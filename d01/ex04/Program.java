public class Program {
	public static void main(String[] args) {
		UsersArrayList users = new UsersArrayList();
		User user1 = new User("John", 1000);
		User user2 = new User("Mike", 2000);
		User user3 = new User("Ben", 3000);
		users.addUser(user1);
		users.addUser(user2);
		users.addUser(user3);

		System.out.println("1 ---------------");
		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("Name:%5s, StartBalance:%5d, CurBalance:%5d, ID: %-2d\n", users.getUserByIndex(i).getName(),
					users.getUserByIndex(i).getStartBalance(), users.getUserByIndex(i).getBalance(), users.getUserByIndex(i).getId());
		}
		System.out.println();
		TransactionsService transactionsService = new TransactionsService();
		transactionsService.addUser(user1);
		transactionsService.addUser(user2);
		transactionsService.addUser(user3);

		System.out.println("2 ---------------");
		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("User ID: %d, Balance: %d\n", users.getUserByIndex(i).getId(),
					transactionsService.getUserBalance(users.getUserByIndex(i).getId()));
		}
		System.out.println();

		System.out.println("3 ---------------");
		transactionsService.transfer(1, 2, 102);
		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("Name:%5s, CurBalance:%5d, StartBalance:%5d, ID: %-2d\n", users.getUserByIndex(i).getName(),
					users.getUserByIndex(i).getBalance(), users.getUserByIndex(i).getStartBalance(), users.getUserByIndex(i).getId());
			for (Transaction t : transactionsService.getTransfers(users.getUserByIndex(i).getId())) {
				System.out.println(t);
			}
			System.out.println();
		}

		System.out.println("4 ---------------");
		transactionsService.transfer(1, 3, 103);
		transactionsService.transfer(2, 1, 201);
		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("Name:%5s, CurBalance:%5d, StartBalance:%5d, ID: %-2d\n", users.getUserByIndex(i).getName(),
					users.getUserByIndex(i).getBalance(), users.getUserByIndex(i).getStartBalance(), users.getUserByIndex(i).getId());
			for (Transaction t : transactionsService.getTransfers(users.getUserByIndex(i).getId())) {
				System.out.println(t);
			}
			System.out.println();
		}

		System.out.println("5 ---------------");
		transactionsService.transfer(3, 1, 301);
		transactionsService.transfer(2, 3, 203);
		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("Name:%5s, CurBalance:%5d, StartBalance:%5d, ID: %-2d\n", users.getUserByIndex(i).getName(),
					users.getUserByIndex(i).getBalance(), users.getUserByIndex(i).getStartBalance(), users.getUserByIndex(i).getId());
			for (Transaction t : transactionsService.getTransfers(users.getUserByIndex(i).getId())) {
				System.out.println(t);
			}
			System.out.println();
		}

		System.out.println("6 ---------------");
		for (Transaction t : transactionsService.getTransfers(user1.getId())) {
			System.out.println(t);
		}
		System.out.println();
		transactionsService.removeTransactionFromUser(user1.getId(), user1.getTransactionsList().toArray()[1].getId());
		transactionsService.removeTransactionFromUser(user1.getId(), user1.getTransactionsList().toArray()[1].getId());
		for (Transaction t : transactionsService.getTransfers(user1.getId())) {
			System.out.println(t);
		}
		System.out.println();

		System.out.println("7 ---------------");
		for (Transaction t : transactionsService.getInvalidTransaction()) {
			System.out.println(t);
		}
		System.out.println();

		System.out.println("8 ---------------");
		transactionsService.transfer(1,2, 50000);
	}
}
