import java.util.Scanner;

public class Program {

	public static final int MAX_CHARS = 65536;

	public static final int VIEW_CHARS = 10;

	public static void printResult(int[] frequencyResult, char[] charsResult) {
		double max = frequencyResult[0];

		int printed = 0;

		boolean space = false;

		boolean print = false;

		for (int i = 0; i < VIEW_CHARS; i++) {
			if (charsResult[i] != 0) {
				print = true;
				break;
			}
		}

		for (int i = 0; i < VIEW_CHARS + 1; i++) {
			for (int k = 0; k < printed; k++) {
				if (k != 0) {
					System.out.print(" ");
				} else {
					System.out.println();
				}

				System.out.print(" #");
			}

			for (int j = printed; j < VIEW_CHARS; j++) {
				if ((int) ((double) frequencyResult[j] / max * 10) == 10 - i) {
					if (space || j != 0) {
						System.out.print(" ");
					}

					if (frequencyResult[j] > 99) {
						System.out.print(frequencyResult[j]);
					} else if (frequencyResult[j] > 9) {
						System.out.print(frequencyResult[j]);
					} else if (charsResult[j] != 0) {
						System.out.print(" " + frequencyResult[j]);
					}

					space = true;
					printed++;
				} else {
					space = false;
					break;
				}
			}
		}

		if (print) {
			System.out.println();

			for (int i = 0; i < VIEW_CHARS; i++) {
				if (charsResult[i] != 0) {
					if (i != 0) {
						System.out.print(" ");
					}

					System.out.print(" " + charsResult[i]);
				}
			}

			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String str = scanner.nextLine();

		int[] tableOfFrequency = new int[MAX_CHARS];

		char[] charsResult = new char[VIEW_CHARS];

		int[] frequencyResult = new int[VIEW_CHARS];

		int j = 0;

		int k;

		if (str.toCharArray().length == 0) {
			return;
		}

		for (char c : str.toCharArray()) {
			if (tableOfFrequency[c] < 999) {
				tableOfFrequency[c]++;
			}
		}

		for (int i = 0; i < MAX_CHARS; i++) {
			if (tableOfFrequency[i] >= frequencyResult[9] && tableOfFrequency[i] != 0) {
				k = 0;

				while (k < VIEW_CHARS) {
					if (tableOfFrequency[i] >= frequencyResult[k]) {
						j = k;
						break;
					}

					k++;
				}

				if (tableOfFrequency[i] == frequencyResult[j]) {
					k = j;
					while (k < VIEW_CHARS) {
						if (i < charsResult[k]) {
							j = k;
							break;
						} else if (tableOfFrequency[i] == frequencyResult[j]) {
							j++;
						}

						k++;
					}
				}

				k = 9;

				while (k > j) {
					frequencyResult[k] = frequencyResult[k - 1];
					charsResult[k] = charsResult[k - 1];
					k--;
				}

				if (j < VIEW_CHARS) {
					frequencyResult[j] = tableOfFrequency[i];
					charsResult[j] = (char) i;
				}
			}
		}

		printResult(frequencyResult, charsResult);
	}
}
