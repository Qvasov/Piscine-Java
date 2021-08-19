public class User {
	private final int id;

	private String name;

	private int balance;

	public User(String name, int balance) {
		this.id = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		setBalance(balance);
	}

	public int getId() {
		return id;
	}

	public int getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public void setBalance(int balance) {
		if (balance < 0) {
			this.balance = 0;
		} else {
			this.balance = balance;
		}
	}

}
