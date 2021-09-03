package school21.spring.service.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import school21.spring.service.config.TestApplicationConfig;


import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfig.class)
class UsersServiceImplTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UsersService usersService;

	@Test
	void signUpTest() throws SQLException {
		String email = "new_user@mail.ru";
		String tempPassword = usersService.signUp("new_user@mail.ru");
		String password = "";

		PreparedStatement preparedStatement = dataSource.getConnection()
				.prepareStatement("SELECT password FROM users WHERE email = ?;");
		preparedStatement.setString(1, email);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			password = resultSet.getString("password");
		}

		assertEquals(tempPassword, password);
	}
}