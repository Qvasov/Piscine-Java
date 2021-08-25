import java.util.UUID;

public class Transaction {
	private String id;

	private User recipient;

	private User sender;

	private TransferCategory category;

	private int amount;

	private TransferStatus status;

	public Transaction(User recipient, User sender, TransferCategory category, int amount) {
		this.id = UUID.randomUUID().toString();
		this.recipient = recipient;
		this.sender = sender;
		this.category = category;
		this.amount = amount;

		if (category == TransferCategory.OUTCOME && amount < 0 && this.recipient.getBalance() >= abs(amount)) {
			this.status = TransferStatus.SUCCESS;
			this.recipient.setBalance(this.recipient.getBalance() + amount);
		} else if (category == TransferCategory.INCOME && amount > 0 && this.recipient.getBalance() >= amount) {
			this.status = TransferStatus.SUCCESS;
			this.sender.setBalance(this.sender.getBalance() + amount);
		} else {
			this.status = TransferStatus.FAIL;
		}
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

	public void setAmount(int amount) {
		if (category == TransferCategory.OUTCOME && amount < 0 ||
				category == TransferCategory.INCOME && amount > 0) {
			this.amount = amount;
		} else {
			this.amount = 0;
		}
	}

	enum TransferCategory {
		INCOME,
		OUTCOME
	}

	enum TransferStatus {
		SUCCESS,
		FAIL
	}

	private int abs(int amount) {
		if (amount < 0) {
			return amount * -1;
		} else {
			return amount;
		}
	}
}
