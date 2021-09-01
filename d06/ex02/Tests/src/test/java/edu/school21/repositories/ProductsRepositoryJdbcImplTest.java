package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductsRepositoryJdbcImplTest {
	EmbeddedDatabase database;
	ProductsRepositoryJdbcImpl productsRepository;

	final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(
			Arrays.asList(
					new Product(0L, "milk", 60),
					new Product(1L, "eggs", 50),
					new Product(2L, "bread", 69),
					new Product(3L, "cheese", 300),
					new Product(4L, "meat", 350),
					new Product(5L, "juice", 100)
			)
	);
	final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(0L, "milk", 60);
	final Product EXPECTED_UPDATED_PRODUCT = new Product(5L, "coca-cola", 80);

	@BeforeEach
	void init() {
		database = new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.HSQL)
				.ignoreFailedDrops(true)
				.setScriptEncoding("UTF-8")
				.addScript("schema.sql")
				.addScript("data.sql")
				.build();
		productsRepository = new ProductsRepositoryJdbcImpl(database);
	}

	@AfterEach
	void shutdownDB() {
		database.shutdown();
	}

	@Test
	void findAllTest() {
		List<Product> products = productsRepository.findAll();
		assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
	}

	@Test
	void findByIdTest() {
		Optional<Product> product = productsRepository.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId());
		assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product.get());
	}

	@Test
	void updateTest() {
		productsRepository.update(EXPECTED_UPDATED_PRODUCT);
		assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(EXPECTED_UPDATED_PRODUCT.getId()).get());
	}

	@Test
	void saveTest() {
		Product product = new Product(100L, "butter", 125);
		productsRepository.save(product);
		assertTrue(productsRepository.findAll().contains(product));
	}

	@Test
	void deleteTest() {
		Long id = 1L;
		productsRepository.delete(id);
		assertEquals(Optional.empty(), productsRepository.findById(id));
	}
}