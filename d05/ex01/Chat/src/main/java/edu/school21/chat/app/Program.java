package edu.school21.chat.app;

import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.Scanner;

public class Program {

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "postgres";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String input = scanner.nextLine();

		long message_id;

		try {
			System.out.println("Enter a message ID");
			message_id = Long.parseLong(input);
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		DataSource dataSource;
		new MessagesRepositoryJdbcImpl(dataSource);
	}
}
