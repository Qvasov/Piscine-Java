package edu.school21.forms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Program {
	private static final String PACKAGE_NAME = "edu.school21.classes";

	public static void main(String[] args) throws Throwable {
		Scanner scanner = new Scanner(System.in);
		Map<String, Class> allClasses = findClasses();

		System.out.println("Classes:");
		allClasses.keySet().forEach(System.out::println);
		System.out.println("---------------------");

		System.out.println("Enter class name:");
		String classname = scanner.nextLine().trim();

		if (!allClasses.containsKey(classname)) {
			throw new ClassNotFoundException("Class not found");
		}

		Class choosedClass = allClasses.get(classname);
		Object instance;
		printFieldsAndMethods(choosedClass);
		instance = createObject(scanner, choosedClass);
		updateObject(scanner, choosedClass, instance);
		methodCall(scanner, choosedClass, instance);
	}

	private static Map<String, Class> findClasses() {
		InputStream stream = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(PACKAGE_NAME.replaceAll("\\.", "/"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.lines().map((Function<String, Optional<Class>>) s -> {
			if (s.endsWith(".class")) {
				try {
					return Optional.of(Class.forName(PACKAGE_NAME + "." + s.substring(0, s.lastIndexOf('.'))));
				} catch (ClassNotFoundException e) {
					return Optional.empty();
				}
			}
			return Optional.empty();
		})
				.filter(Optional::isPresent).collect(Collectors.toMap(o -> o.get().getSimpleName(), Optional::get));
	}

	private static void printFieldsAndMethods(Class choosedClass) {
		System.out.println("---------------------");
		StringBuilder fieldsAndMethods = new StringBuilder();
		fieldsAndMethods.append("fields:\n");
		Arrays.stream(choosedClass.getDeclaredFields())
				.forEach(field -> fieldsAndMethods
						.append('\t')
						.append(field.getType().getSimpleName())
						.append(' ')
						.append(field.getName())
						.append('\n'));

		fieldsAndMethods.append("methods:\n");

		Arrays.stream(choosedClass.getDeclaredMethods())
				.filter(method -> {
					try {
						Object.class.getMethod(method.getName(), method.getParameterTypes());
						return false;
					} catch (NoSuchMethodException e) {
						return true;
					}
				})
				.forEach(method -> fieldsAndMethods
						.append('\t')
						.append(method.getReturnType().getSimpleName())
						.append(" ")
						.append(method.getName())
						.append("(")
						.append(Arrays.stream(method.getParameterTypes())
								.map(Class::getSimpleName).collect(Collectors.joining(", ")))
						.append(")\n"));
		System.out.println(fieldsAndMethods);
	}

	private static Object createObject(Scanner scanner, Class choosedClass)
			throws InvocationTargetException, InstantiationException, IllegalAccessException {
		Object instance = null;
		for (Constructor c : choosedClass.getConstructors()) {
			if (c.getParameterTypes().length == 0) {
				instance = c.newInstance();
				break;
			}
		}
		System.out.println("---------------------");
		System.out.println("Let's create an object.");
		for (Field field : choosedClass.getDeclaredFields()) {
			System.out.println(field.getName() + ":");
			String input = scanner.nextLine().trim();
			field.setAccessible(true);
			field.set(instance, getValue(input, field.getType()));
		}
		System.out.println("Object created: " + instance.toString());
		return instance;
	}

	private static void updateObject(Scanner scanner, Class choosedClass, Object instance) throws IllegalAccessException {
		System.out.println("---------------------");
		System.out.println("Enter name of the field for changing:");
		String fieldName = scanner.nextLine().trim();
		for (Field field : choosedClass.getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				System.out.println("Enter " + field.getType().getSimpleName() + " value:");
				fieldName = scanner.nextLine().trim();
				field.setAccessible(true);
				field.set(instance, getValue(fieldName, field.getType()));
				System.out.println("Object updated: " + instance.toString());
				return;
			}
		}
		throw new IllegalAccessException("Field " + fieldName + " not found");
	}

	private static void methodCall(Scanner scanner, Class choosedClass, Object instance) throws InvocationTargetException, IllegalAccessException {
		System.out.println("---------------------");
		System.out.println("Enter name of the method for call:");
		String methodName = scanner.nextLine().trim();
		for (Method method : choosedClass.getDeclaredMethods()) {
			StringBuilder declaredMethod = new StringBuilder();
			declaredMethod.append(method.getName())
					.append("(")
					.append(Arrays.stream(method.getParameterTypes())
							.map(Class::getSimpleName)
							.collect(Collectors.joining(", ")))
					.append(")");

			if (declaredMethod.toString().equals(methodName)) {
				method.setAccessible(true);
				Object result = method.invoke(instance, Arrays.stream(method.getParameterTypes())
						.map(type -> {
							System.out.println("Enter " + type.getSimpleName() + " value:");
							return getValue(scanner.nextLine().trim(), type);
						})
						.toArray());

				if (result != null) {
					System.out.println("Method returned:");
					System.out.println(result);
				}

				return;
			}
		}
		throw new IllegalAccessException("Method " + methodName + " not found");
	}

	private static Object getValue(String input, Class type) {
		if (type.equals(String.class)) {
			return input;
		} else if (type.equals(int.class) || type.equals(Integer.class)) {
			return Integer.parseInt(input);
		} else if (type.equals(double.class) || type.equals(Double.class)) {
			return Double.parseDouble(input);
		} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return Boolean.parseBoolean(input);
		} else if (type.equals(long.class) || type.equals(Long.class)) {
			return Long.parseLong(input);
		}
		return Object.class;
	}
}
