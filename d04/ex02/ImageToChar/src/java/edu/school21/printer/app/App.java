package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.printer.logic.Logic;

import java.io.IOException;
import java.net.URL;

@Parameters(separators = "=")
public class App {

	@Parameter(names = "--white", validateWith = ArgsValidator.class)
	private String white;

	@Parameter(names = "--black", validateWith = ArgsValidator.class)
	private String black;

	public static void main(String[] args) {
		App app = new App();

		JCommander.newBuilder().addObject(app).build().parse(args);

		URL url = App.class.getClassLoader().getResource("resources/image.bmp");

		try {
			String[][] array = Logic.imageToCharArray(url, app.white, app.black);

			for (String[] line : array) {
				for (String c : line) {
					System.out.print(c);
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}
