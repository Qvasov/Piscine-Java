public class User {
	private int identifier;

	private String name;

	private int balance;

	public User(int identifier, String name, int balance) {
		setIdentifier(identifier);
		setName(name);
		setBalance(balance);
	}

	public int getIdentifier() {
		return identifier;
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

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public void setName(String name) {
		this.name = name;
	}
}
