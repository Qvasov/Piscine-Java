import java.util.Scanner;

public class Program {

	public static int sqrt(int number) {
		int n = 1;

		while (n * n <= number) {
			n++;
		}

		return n;
	}

	public static int sumOfDigit(int number) {
		int sumOfDigit = 0;

		while (number != 0) {
			sumOfDigit += number % 10;
			number /= 10;
		}

		return sumOfDigit;
	}

	public static boolean isPrime(int number) {

		for (int i = 2; i < sqrt(number); i++) {
			if (number % i == 0) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int number;

		int result = 0;

		int sum;

		while ((number = scanner.nextInt()) != 42) {
			sum = sumOfDigit(number);

			if (sum > 1) {
				if (isPrime(sum)) {
					result++;
				}
			}
		}

		System.out.println("Count of coffee-request - " + result);
	}
}
