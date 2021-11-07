import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Program {
	private static final String FILES_URLS = "files_urls.txt";
	private static List<String> files;

	public static void main(String[] args) throws IOException {
		if (args.length != 1 || !args[0].matches("--threadsCount=\\d+")) {
			return;
		}

		files = Files.readAllLines(Paths.get(new File(FILES_URLS).getAbsolutePath()));

		int threadCount = Integer.parseInt(args[0].replace("--threadsCount=", ""));

		List<Thread> threads = initThreads(threadCount);

		for (Thread t : threads) {
			t.start();
		}
	}

	private static List<Thread> initThreads(int threadCount) {

		List<Thread> threadList = new ArrayList<>(threadCount);

		for (int i = 0; i < threadCount; i++) {
			threadList.add(new Thread(new Runnable() {
				String[] file;

				@Override
				public void run() {
					int threadNum = Integer.parseInt(Thread.currentThread().getName().replace("Thread-", "")) + 1;

					while (true) {
						synchronized (files) {
							if (files.size() != 0) {
								file = files.remove(0).split(" ");
								System.out.println("Thread-" + threadNum + " start download file number " + file[0]);
							} else {
								break;
							}
						}

						try {
							downloadFile(file[1]);
							System.out.println("Thread-" + threadNum + " finish download file number " + file[0]);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}));
		}

		return threadList;
	}

	private static void downloadFile(String url) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

		String fileName = url.substring(url.lastIndexOf("/") + 1);

		Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
	}
}
