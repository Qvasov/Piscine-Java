package edu.school21.services;

import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {
	@Mock
	private UsersRepository usersRepository;
	@InjectMocks
	private UsersServiceImpl usersService;
	private User user;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		user = new User("user", "password", false);
		Mockito.when(usersRepository.findByLogin("incorrectLogin")).thenThrow(EntityNotFoundException.class);
		Mockito.when(usersRepository.findByLogin(user.getLogin())).thenReturn(user);
	}

	@Test
	void authenticateCorrect() {
		usersService.authenticate(user.getLogin(), user.getPassword());
		Mockito.verify(usersRepository).update(Mockito.any());
	}

	@Test
	void authenticateIncorrectLogin() {
		assertThrows(EntityNotFoundException.class,
				() -> usersService.authenticate("incorrectLogin", user.getPassword()));
	}

	@Test
	void authenticateIncorrectPassword() {
		assertFalse(usersService.authenticate(user.getLogin(), "incorrectPassword"));
	}
}