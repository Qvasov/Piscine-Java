public class Program {
	public static void main(String[] args) {
		User user1 = new User("John", 1000);

		User user2 = new User("Mike", -800);

		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user1.getName(), user1.getBalance(), user1.getId());
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user2.getName(), user2.getBalance(), user2.getId());
	}
}
