import java.util.Scanner;

public class Program {

	public static int sqrt(int number) {

		int n = 1;

		while (n * n <= number) {
			n++;
		}

		return n - 1;
	}

	public static void main(String[] args) {

		int number = new Scanner(System.in).nextInt();

		int divider = 2;

		int countIteration = 1;

		boolean prime = true;

		if (number < 2) {
			System.err.println("IllegalArgument");
			System.exit(-1);
		}

		while (divider <= sqrt(number)) {
			if (number % divider == 0) {
				prime = false;
				break;
			}
			countIteration++;
			divider++;
		}

		System.out.print(prime + " " + countIteration);
	}
}
