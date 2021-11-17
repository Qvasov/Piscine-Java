package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;

public class NumberWorker {

	public boolean isPrime(int number) {
		if (number < 2) {
			throw new IllegalNumberException();
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
