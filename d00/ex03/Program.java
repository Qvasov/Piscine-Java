import java.util.Scanner;

public class Program {

	public static void printError() {
		System.err.println("IllegalArgument");
		System.exit(-1);
	}

	public static int parseNumber(String scan) {
		if (scan.equals("1")) {
			return 1;
		} else if (scan.equals("2")) {
			return 2;
		} else if (scan.equals("3")) {
			return 3;
		} else if (scan.equals("4")) {
			return 4;
		} else if (scan.equals("5")) {
			return 5;
		} else if (scan.equals("6")) {
			return 6;
		} else if (scan.equals("7")) {
			return 7;
		} else if (scan.equals("8")) {
			return 8;
		} else if (scan.equals("9")) {
			return 9;
		} else if (scan.equals("10")) {
			return 10;
		} else if (scan.equals("11")) {
			return 11;
		} else if (scan.equals("12")) {
			return 12;
		} else if (scan.equals("13")) {
			return 13;
		} else if (scan.equals("14")) {
			return 14;
		} else if (scan.equals("15")) {
			return 15;
		} else if (scan.equals("16")) {
			return 16;
		} else if (scan.equals("17")) {
			return 17;
		} else if (scan.equals("18")) {
			return 18;
		} else {
			return 0;
		}
	}

	public static long storageGrade(int grade, int weekNumber) {
		long positionInStorage = 10;

		if (weekNumber > 0 && weekNumber < 19) {
			for (int i = 1; i < weekNumber; i++) {
				positionInStorage *= 10;
			}

			return grade * positionInStorage / 10;
		}

		return 0;
	}

	public static void printStatistics(long storage, int NumberOfWeeks) {
		int weekNumber = 1;

		long checkWeek = storage;

		while (NumberOfWeeks > 0) {
			if (checkWeek % 10 == 0) {
				printError();
			}

			checkWeek /= 10;
			NumberOfWeeks--;
		}

		while (storage != 0) {
			System.out.print("Week " + weekNumber + " ");

			for (int i = 0; i < storage % 10; i++) {
				System.out.print("=");
			}

			System.out.println(">");
			storage /= 10;
			weekNumber++;
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String scan;

		long storage = 0;

		int lastWeekNumber = 0;

		int weekNumber = 0;

		int grade;

		int minGrade = 10;

		int NumberOfGrade = 19;

		while (!(scan = scanner.next()).equals("42")) {
			if (scan.equals("Week")) {
				scan = scanner.next();
				weekNumber = parseNumber(scan);

				if (weekNumber <= lastWeekNumber || NumberOfGrade < 5) {
					printError();
				}

				storage += storageGrade(minGrade, lastWeekNumber);
				minGrade = 10;
				lastWeekNumber = weekNumber;
				NumberOfGrade = 0;
			} else {
				grade = parseNumber(scan);

				if (grade == 0) {
					printError();
				} else if (grade < minGrade) {
					minGrade = grade;
				}

				NumberOfGrade++;

				if (NumberOfGrade > 9) {
					printError();
				}
			}
		}

		storage += storageGrade(minGrade, lastWeekNumber);
		printStatistics(storage, lastWeekNumber);
	}
}
