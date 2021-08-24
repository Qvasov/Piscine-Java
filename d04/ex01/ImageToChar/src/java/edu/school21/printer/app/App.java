package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

	public static void main(String[] args) {
		if (args.length != 2 ||
				!args[0].matches("--white=.") ||
				!args[1].matches("--black=.")) {
			System.out.println("Usage: java -classpath target edu.org.school21.printer.app.App " +
					"--white={white char} --black={black char}");
			System.exit(-1);
		}

		char white = args[0].replace("--white=", "").charAt(0);

		char black = args[1].replace("--black=", "").charAt(0);

		URL url = App.class.getClassLoader().getResource("resources/image.bmp");

		try {
			char[][] array = Logic.imageToCharArray(url, white, black);

			for (char[] line : array) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}
