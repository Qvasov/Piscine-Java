import java.util.LinkedList;
import java.util.List;

public class Program {

	public static void main(String[] args) {
		if (args.length != 1 || !args[0].matches("--count=\\d+")) {
			return;
		}

		int count = Integer.parseInt(args[0].replace("--count=", ""));

		List<Object> buffer = new LinkedList<>();

		Runnable egg = new Runnable() {
			@Override
			public void run() {
				synchronized (buffer) {
					for (int i = 0; i < count; i++) {
						System.out.println(Thread.currentThread().getName());
						buffer.add(new Object());
						buffer.notify();
						try {
							buffer.wait();
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
				synchronized (buffer) {
					for (int i = 0; i < count; i++) {
						if (buffer.isEmpty()) {
							try {
								buffer.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						buffer.remove(0);
						System.out.println(Thread.currentThread().getName());
						buffer.notify();
					}
				}
			}
		};

		Thread eggT = new Thread(egg);
		Thread henT = new Thread(hen);

		eggT.setName("Egg");
		henT.setName("Hen");

		eggT.start();
		henT.start();
	}
}
