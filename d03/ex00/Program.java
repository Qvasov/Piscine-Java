public class Program {

	public static void main(String[] args) {
		if (args.length != 1 || !args[0].matches("--count=\\d+")) {
			return;
		}

		int count = Integer.parseInt(args[0].replace("--count=", ""));

		Runnable egg = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < count; i++) {
					System.out.println("Egg");
				}
			}
		};

		Runnable hen = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < count; i++) {
					System.out.println("Hen");
				}
			}
		};

		Thread eggT = new Thread(egg);

		Thread henT = new Thread(hen);

		eggT.start();
		henT.start();

		while (true) {
			if (!(eggT.isAlive() || henT.isAlive())) {
				break;
			}
		}

		for (int i = 0; i < count; i++) {
			System.out.println("Human");
		}
	}
}
