package edu.school21.numbers;

public class NumberWorker {

	public boolean isPrime(int number) {
		if (number < 2) {
			throw new edu.school21.numbers.IllegalNumberException();
		}

		int n = 1;

		while (n * n <= number) {
			n++;
		}

		for (int i = 2; i < n; i++) {
			if (number % i == 0) {
				return false;
			}
		}

		return true;
	}

	public int digitsSum(int number) {
		int digitsSum = 0;

		while (number != 0) {
			digitsSum += number % 10;
			number /= 10;
		}

		return digitsSum;
	}
}
