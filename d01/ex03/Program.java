public class Program {
	public static void main(String[] args) {
		User user1 = new User("John", 1000);

		User user2 = new User("Mike", 2000);

		User user3 = new User("Ben", 3000);

		UsersArrayList users = new UsersArrayList();

		TransactionsList transactionsList;

		users.addUser(user1);
		users.addUser(user2);
		users.addUser(user3);

		Transaction toDelete = new Transaction(user3, user1, Transaction.TransferCategory.OUTCOME, -310);

		transactionsList = new TransactionsLinkedList();
		transactionsList.addTransaction(new Transaction(user2, user1, Transaction.TransferCategory.OUTCOME, -210));
		transactionsList.addTransaction(toDelete);
		transactionsList.addTransaction(new Transaction(user3, user1, Transaction.TransferCategory.INCOME, 310));
		user1.setTransactionsList(transactionsList);

		transactionsList = new TransactionsLinkedList();
		transactionsList.addTransaction(new Transaction(user1, user2, Transaction.TransferCategory.OUTCOME, -120));
		transactionsList.addTransaction(new Transaction(user3, user2, Transaction.TransferCategory.OUTCOME, -320));
		transactionsList.addTransaction(new Transaction(user1, user2, Transaction.TransferCategory.INCOME, 120));
		user2.setTransactionsList(transactionsList);

		transactionsList = new TransactionsLinkedList();
		transactionsList.addTransaction(new Transaction(user1, user3, Transaction.TransferCategory.INCOME, 130));
		transactionsList.addTransaction(new Transaction(user2, user3, Transaction.TransferCategory.INCOME, 230));
		user3.setTransactionsList(transactionsList);

		Transaction[] transactions;

		for (int i = 0; i < users.getNumberOfUsers(); i++) {
			System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", users.getUserByIndex(i).getName(),
					users.getUserByIndex(i).getBalance(), users.getUserByIndex(i).getId());
			transactions = users.getUserByIndex(i).getTransactionsList().toArray();
			for (Transaction t : transactions) {
				System.out.printf("%4s -> %4s, %s, %s, %s\n", t.getSender().getName(), t.getRecipient().getName(),
						t.getAmountString(), t.getCategory() ,t.getId());
			}
			System.out.println();
		}

		user1.getTransactionsList().removeTransactionById(toDelete.getId());
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user1.getName(),user1.getBalance(), user1.getId());
		for (Transaction t : user1.getTransactionsList().toArray()) {
			System.out.printf("%4s -> %4s, %s, %s, %s\n", t.getSender().getName(), t.getRecipient().getName(),
					t.getAmountString(), t.getCategory() ,t.getId());
		}

		user2.getTransactionsList().removeTransactionById(toDelete.getId());
	}
}
