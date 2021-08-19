import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) throws IOException {
		if (args.length != 1 && !args[0].startsWith("--current-folder=")) {
			return;
		}

		String currentFolder = args[0].replaceFirst("--current-folder=", "");

		Path currentPath = Paths.get(currentFolder);

		Scanner scanner = new Scanner(System.in);

		String console;

		String[] argsCommand;

		if (!Files.isDirectory(currentPath)) {
			return;
		}

		System.out.println(currentPath);

		while (scanner.hasNextLine()) {
			console = scanner.nextLine();

			if (console.equals("exit")) {
				break;
			} else if (console.equals("ls")) {
				for (File file : currentPath.toFile().listFiles()) {
					System.out.println(file.getName() + " " + file.length() / 1024 + " KB");
				}
			} else if (console.startsWith("cd")) {
				argsCommand = console.split(" ");

				if (argsCommand.length == 2) {
					Path target = Paths.get(currentPath.resolve(argsCommand[1]).toString()).normalize();

					if (Files.exists(target)) {
						currentPath = target;
					}

					System.out.println(currentPath);
				}
			} else if (console.startsWith("mv")) {
				argsCommand = console.split(" ");

				if (argsCommand.length == 3) {
					Path source = currentPath.resolve(argsCommand[1]);
					Path target = currentPath.resolve(argsCommand[2]);

					if (Files.exists(source)) {
						if (target.toFile().isDirectory()) {
							target = target.resolve(source.getFileName());
						}

						if (!Files.exists(target)) {
							Files.move(source, target);
						}
					}
				}
			}
		}
	}
}
