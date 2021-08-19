public class Program {
	public static void main(String[] args) {
		User user1 = new User("John", 1000);

		User user2 = new User("Mike", 2000);

		User user3 = new User("Ben", 3000);

		User user4 = new User("Bob", 4000);

		User user5 = new User("Bret", 5000);

		User user6 = new User("Sam", 6000);

		User user7 = new User("Alex", 7000);

		User user8 = new User("Max", 8000);

		User user9 = new User("Jack", 9000);

		User user10 = new User("Ann", 10000);

		User user11 = new User("Arnold", 11000);

		UsersArrayList users = new UsersArrayList();

		users.addUser(user1);
		users.addUser(user2);
		users.addUser(user3);
		users.addUser(user4);
		users.addUser(user5);
		users.addUser(user6);
		users.addUser(user7);
		users.addUser(user8);
		users.addUser(user9);
		users.addUser(user10);
		users.addUser(user11);

		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("Name:%7s, Balance:%7d, ID: %-2d\n", users.getUserByIndex(i).getName(),
					users.getUserByIndex(i).getBalance(), users.getUserByIndex(i).getId());
		}
		System.out.println();

		int id = 7;

		System.out.printf("Name:%7s, Balance:%7d, ID: %-2d\n", users.getUserById(id).getName(),
				users.getUserById(id).getBalance(), users.getUserById(id).getId());
		id = 20;
		System.out.printf("Name:%7s, Balance:%7d, ID: %-2d\n", users.getUserById(id).getName(),
				users.getUserById(id).getBalance(), users.getUserById(id).getId());
	}
}
