public class Program {

	public static void main(String[] args) {
		if (args.length != 1 || !args[0].matches("--count=\\d+")) {
			return;
		}

		int count = Integer.parseInt(args[0].replace("--count=", ""));

		Object lock = new Object();

		Runnable egg = new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					for (int i = 0; i < count; i++) {
						System.out.println("Egg");
						lock.notify();
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		Runnable hen = new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					for (int i = 0; i < count; i++) {
						System.out.println("Hen");
						lock.notify();
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		Thread eggT = new Thread(egg);

		Thread henT = new Thread(hen);

		eggT.start();
		henT.start();
	}
}
