public class TransactionsService {
	private UsersList usersList;

	public TransactionsService() {
		this.usersList = new UsersArrayList();
	}

	public void addUser(User user) {
		usersList.addUser(user);
	}

	public int getUserBalance(int userId) {
		return usersList.getUserById(userId).getBalance();
	}

	public void transfer(int fromUserId, int toUserId, int amount) throws IllegalTransactionException {
		User sender = usersList.getUserById(fromUserId);

		User recipient = usersList.getUserById(toUserId);

		Transaction transaction;

		if (sender.getBalance() < amount) {
			throw new IllegalTransactionException();
		}

		transaction = new Transaction(recipient, sender, TransferCategory.OUTCOME, -amount);
		sender.setBalance(sender.getBalance() - amount);
		sender.getTransactionsList().addTransaction(transaction);

		transaction = new Transaction(sender, recipient, TransferCategory.INCOME, amount, transaction.getId());
		recipient.setBalance(recipient.getBalance() + amount);
		recipient.getTransactionsList().addTransaction(transaction);
	}

	public Transaction[] getTransfers(int userId) {
		return usersList.getUserById(userId).getTransactionsList().toArray();
	}

	public void removeTransactionFromUser(int userId, String transactionId) {
		usersList.getUserById(userId).getTransactionsList().removeTransactionById(transactionId);
	}

	public Transaction[] getInvalidTransaction() {
		TransactionsLinkedList invalidTransactions = new TransactionsLinkedList();

		boolean pairExist;

		for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
			for (Transaction tr : usersList.getUserByIndex(i).getTransactionsList().toArray()) {
				pairExist = false;
				for (Transaction trPair : tr.getRecipient().getTransactionsList().toArray()) {
					if (tr.getId().equals(trPair.getId())) {
						pairExist = true;
						break;
					}
				}

				if (!pairExist) {
					invalidTransactions.addTransaction(tr);
				}
			}
		}

		return invalidTransactions.toArray();
	}
}
