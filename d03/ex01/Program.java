public class Program {

	public static void main(String[] args) {
		if (args.length != 2 &&
				!args[0].matches("--arraySize=\\d+") &&
				!args[1].matches("--threadsCount=\\d")) {
			return;
		}

		int arraySize = Integer.parseInt(args[0].replace("--arraySize=", ""));

		int threadCount = Integer.parseInt(args[0].replace("--threadsCount=", ""));

		if (arraySize < 1 || threadCount < 1) {
			return;
		} else if (arraySize > 2_000_000) {
			arraySize = 2_000_000;
		}

		if (threadCount > arraySize) {
			threadCount = arraySize;
		}

		int[] array = new int[arraySize];

		int sum = 0;

		for (int i = 0; i < array.length; i++) {
//			array[i] = (int) Math.round(Math.random() * Integer.MAX_VALUE) % 1000;
			array[i] = 1;
			sum += array[i];
		}

		System.out.println("Sum: " + sum);

		Thread[] threads = new Thread[threadCount];

		int sectorSize = arraySize / threadCount;

		int lastSectorSize = sectorSize + arraySize - (sectorSize * threadCount);

		Runnable sumTask = new Runnable() {
			@Override
			public void run() {

			}
		};

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(sumTask);
		}

		for (int i = 0; i < threadCount; i++) {
			threads[i].start();
		}
	}
}
