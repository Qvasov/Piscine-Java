package edu.school21.managers;

import edu.school21.main.Program;

import javax.sql.DataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class OrmManager {
	private static final String TABLE_PATTERN = "DROP TABLE IF EXISTS ?; CREATE TABLE ?";
	private Map<String, String> tables;
	private DataSource dataSource;

	public OrmManager(DataSource dataSource) {
		this.dataSource = dataSource;
		this.tables = new HashMap<>();
		Set<Class<?>> classes = findClassesAnnotatedWith(OrmEntity.class);
		StringJoiner result = new StringJoiner(";\n", "", ";");
		for (Class<?> clazz : classes) {
			tables.put(clazz.getSimpleName(), clazz.getAnnotation(OrmEntity.class).table());
			String sql = TABLE_PATTERN;
			sql = sql.replaceAll("\\?", clazz.getAnnotation(OrmEntity.class).table());
			sql += getColumns(clazz.getDeclaredFields());
			result.add(sql);
		}
		System.out.println(result);

		try (Connection connection = dataSource.getConnection()) {
			connection.prepareStatement(result.toString()).execute();
		} catch (SQLException sqlException) {
			System.err.println(sqlException.getMessage());
			System.exit(-1);
		}
	}

	private Set<Class<?>> findClassesAnnotatedWith(Class<? extends Annotation> annotation) {
		String packageName = Program.class.getPackage().getName().replaceAll("\\..*", "");
		String url = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(packageName)).getPath();
		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		Set<File> files = findFiles(url);
		Set<Class<?>> classes = new HashSet<>();

		for (File file : files) {
			String classStr = file.toString();
			if (classStr.endsWith(".class")) {
				classStr = file.toString()
						.replaceFirst("^.*" + packageName, packageName)
						.replaceAll("\\.class", "")
						.replaceAll(Matcher.quoteReplacement(File.separator), "\\.");
				try {
					Class<?> clazz = Class.forName(classStr);
					if (Objects.nonNull(clazz.getAnnotation(annotation))) {
						classes.add(clazz);
					}
				} catch (ClassNotFoundException ignored) {
				}
			}
		}
		return classes;
	}

	private Set<File> findFiles(String path) {
		Set<File> files = new HashSet<>();
		File file = new File(path);
		if (file.isFile()) {
			files.add(file);
		} else {
			if (file.isDirectory()) {
				for (File f : Optional.ofNullable(file.listFiles()).orElse(new File[]{})) {
					files.addAll(findFiles(f.getPath()));
				}
			}
		}
		return files;
	}

	private String getColumns(Field[] fields) {
		StringJoiner columns = new StringJoiner(", ", "(", ")");
		for (Field field : fields) {
			if (!getColumn(field).isEmpty()) {
				columns.add(getColumn(field));
			}
		}
		return columns.toString();
	}

	private String getColumn(Field field) {
		String result = "";
		if (field.isAnnotationPresent(OrmColumnId.class) && field.isAnnotationPresent(OrmColumn.class)) {
			System.err.printf("Field \"%s\" is ignored. It have two annotations %s, %s. You must leave only one!\n",
					field.getName(), OrmColumnId.class.getSimpleName(), OrmColumn.class.getSimpleName());
		} else if (field.isAnnotationPresent(OrmColumnId.class)) {
			OrmColumnId annotation = field.getAnnotation(OrmColumnId.class);
			String name = annotation.name();
			String type = mapType(field.getType());
			type = type.equals("BIGINT") ? "BIGSERIAL" : type;
			result = name + " " + type + " PRIMARY KEY";
		} else if (field.isAnnotationPresent(OrmColumn.class)) {
			OrmColumn annotation = field.getAnnotation(OrmColumn.class);
			String name = annotation.name();
			String type = mapType(field.getType());
			type = type.equals("VARCHAR") ? type + "(" + annotation.length() + ")" : type;
			result = name + " " + type;
		}
		return result;
	}

	private String mapType(Class<?> type) {
		switch (type.getSimpleName()) {
			case "String":
				return "VARCHAR";
			case "Integer":
				return "INTEGER";
			case "Double":
				return "DOUBLE PRECISION";
			case "Boolean":
				return "BOOLEAN";
			case "Long":
				return "BIGINT";
			default:
				return "";
		}
	}

	public void save(Object entity) {
		String sql = "INSERT INTO " + tables.get(entity.getClass().getSimpleName());
		StringJoiner columns = new StringJoiner(",", "(", ")");
		StringJoiner values = new StringJoiner(",", "VALUES(", ");");

		for (Field field : entity.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(OrmColumnId.class) && field.isAnnotationPresent(OrmColumn.class)) {
				System.err.printf("Field \"%s\" is ignored. It have two annotations %s, %s. You must leave only one!\n",
						field.getName(), OrmColumnId.class.getSimpleName(), OrmColumn.class.getSimpleName());
			} else if (field.isAnnotationPresent(OrmColumn.class)) {
				OrmColumn annotation = field.getAnnotation(OrmColumn.class);
				columns.add(annotation.name());
				try {
					field.setAccessible(true);
					Object value = field.get(entity);
					if (value != null) {
						values.add("'" + value + "'");
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}

		sql += columns + " " + values;
		System.out.println(sql);

		try (Connection connection = dataSource.getConnection()) {
			connection.prepareStatement(sql).execute();
		} catch (SQLException sqlException) {
			System.err.println(sqlException.getMessage());
			System.exit(-1);
		}
	}

	public void update(Object entity) {
		String sql = "UPDATE " + tables.get(entity.getClass().getSimpleName());
		String idColumn = null;
		Object id = null;
		StringJoiner values = new StringJoiner(",", "SET ", "");

		try {
			for (Field field : entity.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(OrmColumnId.class)) {
					idColumn = field.getAnnotation(OrmColumnId.class).name();
					id = field.get(entity);
				} else if (field.isAnnotationPresent(OrmColumn.class)) {
					values.add(field.getAnnotation(OrmColumn.class).name() + " = " + getValue(field.get(entity)));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		sql += " " + values + " WHERE " + idColumn + " = " + id;
		System.out.println(sql);

		try (Connection connection = dataSource.getConnection()) {
			connection.prepareStatement(sql).execute();
		} catch (SQLException sqlException) {
			System.err.println(sqlException.getMessage());
			System.exit(-1);
		}
	}

	private Object getValue(Object value) {
		if (value instanceof String) {
			return "'" + value + "'";
		} else {
			return value;
		}
	}

	public <T> T findById(Long id, Class<T> aClass) {
		String sql = "SELECT * FROM " + tables.get(aClass.getSimpleName()) + " WHERE ";
		String column = Arrays.stream(aClass.getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(OrmColumnId.class))
				.map(f -> f.getAnnotation(OrmColumnId.class).name())
				.collect(Collectors.toList())
				.get(0);
		sql += column + " = '" + id + "';";
		System.out.println(sql);

		try (Connection connection = dataSource.getConnection()) {
			ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
			if (resultSet.next()) {
				return createInstance(resultSet, aClass);
			}
		} catch (SQLException sqlException) {
			System.err.println(sqlException.getMessage());
			System.exit(-1);
		}
		return null;
	}

	private <T> T createInstance(ResultSet resultSet, Class<T> aClass) throws SQLException {
		Constructor<?> constructor = Arrays.stream(aClass.getDeclaredConstructors())
				.filter(c -> c.getParameterTypes().length == 0)
				.collect(Collectors.toList())
				.get(0);
		try {
			constructor.setAccessible(true);
			T instance = (T) constructor.newInstance();
			for (Field field : aClass.getDeclaredFields()) {
				Object value = null;
				if (field.isAnnotationPresent(OrmColumnId.class)) {
					value = getValueFromTable(resultSet, field.getAnnotation(OrmColumnId.class).name(), field.getType());
				} else if (field.isAnnotationPresent(OrmColumn.class)) {
					value = getValueFromTable(resultSet, field.getAnnotation(OrmColumn.class).name(), field.getType());
				}
				field.setAccessible(true);
				field.set(instance, value);
			}
			return instance;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}

	private Object getValueFromTable(ResultSet resultSet, String columnName, Class<?> type) throws SQLException {
		if (type.equals(String.class)) {
			return resultSet.getString(columnName);
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			return resultSet.getInt(columnName);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			return resultSet.getDouble(columnName);
		} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return resultSet.getBoolean(columnName);
		} else if (type.equals(long.class) || type.equals(Long.class)) {
			return resultSet.getLong(columnName);
		}
		return null;
	}
}
