package edu.school21.chat.app;

import java.util.Scanner;

public class Program {

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



	}
}
