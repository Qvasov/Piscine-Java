public class User {
	private final int id;

	private String name;

	private int balance;

	private int startBalance;

	private TransactionsList transactionsList;

	public User(String name, int balance) {
		this.id = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		setBalance(balance);
		this.startBalance = balance;
		this.transactionsList = new TransactionsLinkedList();
	}

	public int getId() {
		return id;
	}

	public int getBalance() {
		return balance;
	}

	public int getStartBalance() {
		return startBalance;
	}

	public String getName() {
		return name;
	}

	public TransactionsList getTransactionsList() {
		return transactionsList;
	}

	public void setBalance(int balance) {
		if (balance < 0) {
			this.balance = 0;
		} else {
			this.balance = balance;
		}
	}
}
