import java.util.UUID;

public class Transaction {
	private String id;

	private User sender;

	private User recipient;

	private TransferCategory category;

	private int amount;

	private TransferStatus status;

	public Transaction(User sender, User recipient, TransferCategory category, int amount) {
		this.id = UUID.randomUUID().toString();
		this.sender = sender;
		this.recipient = recipient;
		this.category = category;
		this.amount = amount;

		if (category == TransferCategory.OUTCOME && amount < 0 && this.sender.getBalance() >= abs(amount)) {
			this.status = TransferStatus.SUCCESS;
		} else if (category == TransferCategory.INCOME && amount > 0 && this.recipient.getBalance() >= amount) {
			this.status = TransferStatus.SUCCESS;
		} else {
			this.status = TransferStatus.FAIL;
		}
	}

	public Transaction(User sender, User recipient, TransferCategory category, int amount, String id) {
		this(sender, recipient, category, amount);
		setId(id);
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

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return String.format("%s %s(id = %d) %+d with id = %s",
				(category == Transaction.TransferCategory.OUTCOME) ? "To" : "From",
				recipient.getName(),
				recipient.getId(),
				amount,
				id);
	}
}
