public class Program {
	public static void main(String[] args) {
		User user1 = new User(1, "John", 1000);

		User user2 = new User(2, "Mike", -800);

		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user1.getName(), user1.getBalance(), user1.getIdentifier());
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user2.getName(), user2.getBalance(), user2.getIdentifier());
		System.out.println();

		user1.setBalance(-10);
		user2.setBalance(100);
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user1.getName(), user1.getBalance(), user1.getIdentifier());
		System.out.printf("Name:%5s, Balance:%7d, ID: %-2d\n", user2.getName(), user2.getBalance(), user2.getIdentifier());
		System.out.println();

		Transaction t1 = new Transaction(user1, user2, Transaction.TransferCategory.OUTCOME, -500);

		Transaction t2 = new Transaction(user1, user2, Transaction.TransferCategory.INCOME, 500);

		System.out.println(t1);
		System.out.println(t2);
		System.out.println();

		t1.setAmount(100);
		t2.setAmount(-100);
		System.out.println(t1);
		System.out.println(t2);
		System.out.println();

		t1.setAmount(-150);
		t2.setAmount(150);
		System.out.println(t1);
		System.out.println(t2);
	}
}


