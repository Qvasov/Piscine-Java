import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

	public static int sqrt(int number) {
		int n = 0;

		while (n * n <= number) {
			n++;
		}

		return n - 1;
	}

	public static void main(String[] args) {
		int number;

		int divider = 2;

		int countIteration = 1;

		boolean prime = true;

		try {
			number = new Scanner(System.in).nextInt();
		} catch (InputMismatchException e) {
			return;
		}

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
