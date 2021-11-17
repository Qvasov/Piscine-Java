package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
	private DataSource database;

	public ProductsRepositoryJdbcImpl(DataSource database) {
		this.database = database;

	}

	@Override
	public List<Product> findAll() {
		List<Product> productList = new ArrayList<>();

		try (Connection connection = database.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM product;");
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Product product = new Product(
						resultSet.getLong("f_id"),
						resultSet.getString("f_name"),
						resultSet.getInt("f_cost")
				);
				productList.add(product);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return productList;
	}

	@Override
	public Optional<Product> findById(Long id) {
		if (id != null) {
			try (Connection connection = database.getConnection()) {
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE f_id = ?;");
				statement.setLong(1, id);
				ResultSet resultSet = statement.executeQuery();

				if (resultSet.next()) {
					Product product = new Product(
							resultSet.getLong("f_id"),
							resultSet.getString("f_name"),
							resultSet.getInt("f_cost")
					);
					return Optional.of(product);
				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return Optional.empty();
	}

	@Override
	public void update(Product product) {
		if (product != null) {
			try (Connection connection = database.getConnection()) {
				PreparedStatement statement = connection
						.prepareStatement("UPDATE product SET f_name = ?, f_cost = ? WHERE f_id = ?;");
				statement.setString(1, product.getName());
				statement.setInt(2, product.getCost());
				statement.setLong(3, product.getId());
				statement.execute();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}

	@Override
	public void save(Product product) {
		if (product != null) {
			try (Connection connection = database.getConnection()) {
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO product (f_id, f_name, f_cost) VALUES (?, ?, ?);");
				statement.setLong(1, product.getId());
				statement.setString(2, product.getName());
				statement.setInt(3, product.getCost());
				statement.execute();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Long id) {
		try (Connection connection = database.getConnection()) {
			Optional<Product> product = findById(id);
			if (product.isPresent()) {
				PreparedStatement statement = connection
						.prepareStatement("DELETE FROM product WHERE f_id = ?;");
				statement.setLong(1, product.get().getId());
				statement.execute();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
}
