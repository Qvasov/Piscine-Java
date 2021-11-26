package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {

	@Autowired
	@Qualifier("usersRepository")
	UsersRepository usersRepository;

	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	BCryptPasswordEncoder encoder;

	@Override
	public boolean signUp(String username, String password) {
		if (usersRepository.findByUsername(username).isPresent()) {
			return false;
		} else {
			User newUser = new User(username, encoder.encode(password));
			usersRepository.save(newUser);
			return true;
		}
	}

	@Override
	public boolean signIn(String username, String password) {
		Optional<User> user = usersRepository.findByUsername(username);
		return user.filter(value -> encoder.matches(password, value.getPassword())).isPresent();
	}
}
