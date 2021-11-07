public class Program {
	public static void main(String[] args) {
		UsersArrayList users = new UsersArrayList();

		User user1 = new User("John", 1000);
		User user2 = new User("Mike", 2000);
		User user3 = new User("Ben", 3000);

		users.addUser(user1);
		users.addUser(user2);
		users.addUser(user3);

		TransactionsList transactionsList = new TransactionsLinkedList();

		Transaction toDelete = new Transaction(user1, user3, Transaction.TransferCategory.OUTCOME, -130);

		transactionsList.addTransaction(new Transaction(user1, user2, Transaction.TransferCategory.OUTCOME, -120));
		transactionsList.addTransaction(toDelete);
		transactionsList.addTransaction(new Transaction(user1, user3, Transaction.TransferCategory.INCOME, 130));
		user1.setTransactionsList(transactionsList);

		transactionsList = new TransactionsLinkedList();
		transactionsList.addTransaction(new Transaction(user2, user1, Transaction.TransferCategory.OUTCOME, -210));
		transactionsList.addTransaction(new Transaction(user2, user3, Transaction.TransferCategory.OUTCOME, -230));
		transactionsList.addTransaction(new Transaction(user2, user1, Transaction.TransferCategory.INCOME, 210));
		user2.setTransactionsList(transactionsList);

		transactionsList = new TransactionsLinkedList();
		transactionsList.addTransaction(new Transaction(user3, user1, Transaction.TransferCategory.INCOME, 310));
		transactionsList.addTransaction(new Transaction(user3, user2, Transaction.TransferCategory.INCOME, 320));
		user3.setTransactionsList(transactionsList);

		Transaction[] transactions;

		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", users.getUserByIndex(i).getName(),
					users.getUserByIndex(i).getBalance(), users.getUserByIndex(i).getId());
			transactions = users.getUserByIndex(i).getTransactionsList().toArray();
			for (Transaction t : transactions) {
				System.out.println(t);
			}
			System.out.println();
		}

		System.out.println("--------------DELETE--------------");
		user1.getTransactionsList().removeTransactionById(toDelete.getId());
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user1.getName(),user1.getBalance(), user1.getId());
		for (Transaction t : user1.getTransactionsList().toArray()) {
			System.out.println(t);
		}

		user2.getTransactionsList().removeTransactionById(toDelete.getId());
	}
}
