import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {

	private static final String SIGNATURES = "signatures.txt";

	private static final String RESULT = "result.txt";

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);

		FileOutputStream resultFile = new FileOutputStream(RESULT);

		Map<String, String> signatures = readSignatures();

		int maxMagicLength = getMaxLength(signatures);

		String magicBytes;

		String inputFileName;

		boolean processed = false;

		while (scanner.hasNext()) {
			inputFileName = scanner.nextLine();

			if (inputFileName.equals("42")) {
				break;
			}

			magicBytes = getMagicBytes(inputFileName, maxMagicLength);

			String formatName;

			for (String signature : signatures.keySet()) {
				if (magicBytes.contains(signature)) {
					formatName = signatures.get(signature) + System.lineSeparator();
					resultFile.write(formatName.getBytes());
					processed = true;
					break;
				}
			}

			if (processed) {
				System.out.println("PROCESSED");
				processed = false;
			} else {
				System.out.println("UNDEFINED");
			}
		}
		resultFile.close();
	}

	private static String getMagicBytes(String inputFileName, int maxMagicLength) throws IOException {
		byte[] magic = new byte[maxMagicLength];

		FileInputStream inputFile = new FileInputStream(inputFileName);

		inputFile.read(magic, 0, maxMagicLength);

		String result = "";

		for (byte b : magic) {
			result += String.format("%02X", b);
		}

		inputFile.close();
		return result;
	}

	private static Map<String, String> readSignatures() throws IOException {
		FileInputStream signatureStream = new FileInputStream(SIGNATURES);

		HashMap<String, String> signatures = new HashMap<>();

		byte[] bytes = new byte[signatureStream.available()];

		signatureStream.read(bytes);

		String temp = new String(bytes);

		String[] lines = temp.split("\r\n|\n");

		String[] splitLine;

		for (String line : lines) {
			splitLine = line.split(",");
			signatures.put(splitLine[1].replaceAll(" ", ""), splitLine[0]);
		}

		return signatures;
	}

	private static int getMaxLength(Map<String, String> signatures) {
		int maxLength = 0;

		for (String value : signatures.keySet()) {
			if (value.length() > maxLength) {
				maxLength = value.length();
			}
		}

		return maxLength / 2;
	}
}
