import java.util.Scanner;

public class Program {

	public static final int MAX_STUDENTS = 10;

	public static final int MAX_LESSONS = 10;

	public static final String[] DAY_OF_WEEK = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};

	public static final int DAYS_IN_MONTHS = 30;

	public static int parseTime(String str) {
		if (str.equals("1")) {
			return 1;
		} else if (str.equals("2")) {
			return 2;
		} else if (str.equals("3")) {
			return 3;
		} else if (str.equals("4")) {
			return 4;
		} else if (str.equals("5")) {
			return 5;
		} else if (str.equals("6")) {
			return 6;
		}
		return 0;
	}

	public static int parseDay(String str) {
		if (str.equals("MO")) {
			return 1;
		} else if (str.equals("TU")) {
			return 2;
		} else if (str.equals("WE")) {
			return 3;
		} else if (str.equals("TH")) {
			return 4;
		} else if (str.equals("FR")) {
			return 5;
		} else if (str.equals("SA")) {
			return 6;
		} else if (str.equals("SU")) {
			return 7;
		}
		return 0;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String str;

		int i = 0;

		String[] students = new String[10];

		int numberOfStudents = 0;

		while (i < MAX_STUDENTS) {
			str = scanner.nextLine();

			if (str.equals(".")) {
				break;
			} else {
				students[numberOfStudents++] = str;
			}
			i++;
		}

		if (i == MAX_STUDENTS) {
			str = scanner.nextLine();
		}

		i = 0;

		int[][] timetable = new int[6][7];

		while (i < MAX_LESSONS) {
			str = scanner.next();

			if (str.equals(".")) {
				break;
			}

			timetable[parseTime(str) - 1][parseDay(scanner.next()) - 1] = 1;
			i++;
		}

		if (i == MAX_LESSONS) {
			str = scanner.nextLine();
		}

		int[][][] attendance = new int[numberOfStudents][6][DAYS_IN_MONTHS];

		int name = 0;

		int time;

		int day;

		int dayOfWeek;

		int presence;

		while (true) {
			str = scanner.next();

			if (str.equals(".")) {
				break;
			}

			i = 0;

			while (i < numberOfStudents) {
				if (students[i].equals(str)) {
					name = i;
					break;
				}
				i++;
			}

			time = scanner.nextInt();
			day = scanner.nextInt();
			dayOfWeek = day % 7;

			str = scanner.next();

			if (str.equals("HERE")) {
				presence = 1;
			} else if (str.equals("NOT_HERE")) {
				presence = -1;
			} else {
				presence = 0;
			}

			if (timetable[time - 1][dayOfWeek] == 1) {
				attendance[name][time - 1][day - 1] = presence;
			}
		}


		System.out.printf("%10c", ' ');
		for (int j = 0; j < DAYS_IN_MONTHS; j++) {
			for (int k = 0; k < 6; k++) {
				if (timetable[k][(j + 1) % 7] == 1) {
					System.out.printf("%1d:00%3s%3d|", k + 1, DAY_OF_WEEK[(j + 1) % 7], j + 1);
				}
			}
		}

		System.out.println();

		for (int j = 0; j < numberOfStudents; j++) {
			System.out.printf("%10s", students[j]);
			for (int k = 0; k < DAYS_IN_MONTHS; k++) {
				for (int l = 0; l < 6; l++) {
					if (timetable[l][(k + 1) % 7] == 1) {
						if (attendance[j][l][k] == 1) {
							System.out.printf("%10d|", attendance[j][l][k]);
						} else if (attendance[j][l][k] == -1) {
							System.out.printf("%10d|", attendance[j][l][k]);
						} else {
							System.out.printf("%10c|", ' ');
						}
					}
				}
			}
			System.out.println();
		}
	}
}
