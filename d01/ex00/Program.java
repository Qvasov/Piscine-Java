public class Program {
	public static void main(String[] args) {
		User user1 = new User(1, "John", 1000);

		User user2 = new User(2, "Mike", -800);

		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user1.getName(), user1.getBalance(), user1.getIdentifier());
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user2.getName(), user2.getBalance(), user2.getIdentifier());
		user1.setBalance(-10);
		System.out.println();
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user1.getName(), user1.getBalance(), user1.getIdentifier());
		user2.setBalance(100);
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user2.getName(), user2.getBalance(), user2.getIdentifier());
		System.out.println();

		Transaction t1 = new Transaction(user1, user2, Transaction.TransferCategory.OUTCOME, -500);

		Transaction t2 = new Transaction(user1, user2, Transaction.TransferCategory.INCOME, 500);

		System.out.printf("%s -> %s, %s, %s, %s\n", t1.getSender().getName(), t1.getRecipient().getName(),
				t1.getAmountString(), t1.getCategory(), t1.getId());
		System.out.printf("%s -> %s, %s, %s, %s\n", t2.getSender().getName(), t2.getRecipient().getName(),
				t2.getAmountString(), t2.getCategory(), t2.getId());
		System.out.println();

		t1.setAmount(100);
		System.out.printf("%s -> %s, %s, %s, %s\n", t1.getSender().getName(), t1.getRecipient().getName(),
				t1.getAmountString(), t1.getCategory(), t1.getId());
		t2.setAmount(-100);
		System.out.printf("%s -> %s, %s, %s, %s\n", t2.getSender().getName(), t2.getRecipient().getName(),
				t2.getAmountString(), t2.getCategory(), t2.getId());
		System.out.println();

		t1.setAmount(-150);
		System.out.printf("%s -> %s, %s, %s, %s\n", t1.getSender().getName(), t1.getRecipient().getName(),
				t1.getAmountString(), t1.getCategory(), t1.getId());
		t2.setAmount(150);
		System.out.printf("%s -> %s, %s, %s, %s\n", t2.getSender().getName(), t2.getRecipient().getName(),
				t2.getAmountString(), t2.getCategory(), t2.getId());
	}
}


