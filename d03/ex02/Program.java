public class Program {
	private int[] array;
	private int sumOfThreads;

	public static void main(String[] args) throws InterruptedException {
		if (args.length != 2 ||
				!args[0].matches("--arraySize=\\d+") ||
				!args[1].matches("--threadsCount=\\d+")) {
			return;
		}

		int arraySize = Integer.parseInt(args[0].replace("--arraySize=", ""));

		int threadCount = Integer.parseInt(args[1].replace("--threadsCount=", ""));

		if (arraySize < 1 || threadCount < 1) {
			return;
		} else if (arraySize > 2_000_000) {
			arraySize = 2_000_000;
		}

		if (threadCount > arraySize) {
			threadCount = arraySize;
		}

		Program data = new Program();

		data.array = new int[arraySize];

		data.sumOfThreads = 0;

		int sum = 0;

		for (int i = 0; i < data.array.length; i++) {
			data.array[i] = (int) Math.round(Math.random() * 2000) - 1000;
			sum += data.array[i];
		}

		System.out.println("Sum: " + sum);

		int sectorSize = arraySize / threadCount;

		int lastSectorSize = sectorSize + arraySize - (sectorSize * threadCount);

		int start;

		int end;

		for (int i = 0; i < threadCount; i++) {
			start = sectorSize * i;

			if (i == threadCount - 1) {
				end = start + lastSectorSize;
			} else {
				end = start + sectorSize;
			}

			Thread thread = createThread(start, end, i, data);

			thread.start();
			thread.join();
		}

		System.out.println("Sum by threads: " + data.sumOfThreads);
	}

	private static Thread createThread(int start, int end, int threadNumber, Program data) {
		Runnable sumTask = new Runnable() {
			@Override
			public void run() {
				synchronized (data) {
					int sum = 0;

					for (int i = start; i < end; i++) {
						sum += data.array[i];
					}

					data.sumOfThreads += sum;
					System.out.printf("Thread %d: from %d to %d sum is %d\n", threadNumber + 1, start, end, sum);
				}
			}
		};

		return new Thread(sumTask);
	}
}
