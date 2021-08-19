import java.util.UUID;

public class Transaction {
	private String id;

	private User recipient;

	private User sender;

	private TransferCategory category;

	private int amount;

	public Transaction(User recipient, User sender, TransferCategory category, int amount) {
		setId(UUID.randomUUID().toString());
		setRecipient(recipient);
		setSender(sender);
		setCategory(category);
		setAmount(amount);
	}

	public String getId() {
		return id;
	}

	public User getRecipient() {
		return recipient;
	}

	public User getSender() {
		return sender;
	}

	public TransferCategory getCategory() {
		return category;
	}

	public int getAmount() {
		return amount;
	}

	public String getAmountString() {
		if (amount > 0)
			return "+" + amount;
		else {
			return String.valueOf(amount);
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public void setCategory(TransferCategory category) {
		this.category = category;
	}

	public void setAmount(int amount) {
		if (category == TransferCategory.OUTCOME && amount < 0 ||
				category == TransferCategory.INCOME && amount > 0) {
			this.amount = amount;
		}
	}

	enum TransferCategory {
		INCOME,
		OUTCOME
	}
}
