package edu.school21.numbers;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {

	NumberWorker numberWorker = new NumberWorker();

	@ParameterizedTest
	@ValueSource(ints = {2, 3, 5, 7, 11})
	void isPrimeForPrimes(int number) {
		assertTrue(numberWorker.isPrime(number));
	}

	@ParameterizedTest
	@ValueSource(ints = {4, 6, 26, 50, 100})
	void isPrimeForNotPrimes(int number) {
		assertFalse(numberWorker.isPrime(number));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, -26, -50, -100})
	void isPrimeForIncorrectNumbers(int number) {
		assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv")
	void digitsSumForCorrect(int digits, int sum) {
		assertEquals(sum, numberWorker.digitsSum(digits));
	}
}