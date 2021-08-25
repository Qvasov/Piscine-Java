public class Program {
	public static void main(String[] args) {
		boolean devMode = false;

		Menu menu;

		for (String arg : args) {
			if (arg.equals("--profile=dev")) {
				devMode = true;
				break;
			}
		}

		menu = new Menu(devMode);
		System.out.println();
		menu.menuStart();
	}
}
