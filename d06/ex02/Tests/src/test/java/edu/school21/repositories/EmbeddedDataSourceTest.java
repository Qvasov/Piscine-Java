package edu.school21.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedDataSourceTest {
	EmbeddedDatabase database;

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
	}

	@Test
	void testConnection() {
		try {
			assertNotNull(database.getConnection());
			database.shutdown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
