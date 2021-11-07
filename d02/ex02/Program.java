import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) throws IOException {
		if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
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
				try {
					for (File file : currentPath.toFile().listFiles()) {
						int i = 0;
						long size = countFileSize(file);

						while (size >= 1024) {
							size /= 1024;
							i++;
						}

						System.out.println(file.getName() + " " + size + " " + getSizeChar(i));
					}
				} catch (SecurityException e) {
					e.printStackTrace();
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

	private static long countFileSize(File file) {
		long result = 0L;

		try {
			if (Files.isDirectory(file.toPath())) {
				for (File f : file.listFiles()) {
					result += countFileSize(f);
				}
			} else {
				result = file.length();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String getSizeChar(int i) {
		switch (i) {
			case 1:
				return "KB";
			case 2:
				return "MB";
			case 3:
				return "GB";
			case 4:
				return "TB";
			case 5:
				return "PB";
		}
		return "B";
	}
}
