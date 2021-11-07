import java.util.Scanner;

public class Menu {
	private Scanner scanner = new Scanner(System.in);

	String[] input;

	private boolean devMode;

	private TransactionsService transactionsService = new TransactionsService();

	public Menu(boolean devMode) {
		this.devMode = devMode;
	}

	private static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public void menuStart() {
		int num;

		while (true) {
			mainMenu();

			input = scanner.nextLine().split(" ");

			if (input.length == 1) {
				if (isNumber(input[0])) {
					num = Integer.parseInt(input[0]);

					if (num == 1) {
						addUser();
						System.out.println("---------------------------------------------------------");
					} else if (num == 2) {
						showBalances();
						System.out.println("---------------------------------------------------------");
					} else if (num == 3) {
						performTransfer();
						System.out.println("---------------------------------------------------------");
					} else if (num == 4) {
						showTransactions();
						System.out.println("---------------------------------------------------------");
					} else if (num == 5 && devMode) {
						removeTransfer();
						System.out.println("---------------------------------------------------------");
					} else if (num == 6 && devMode) {
						checkTransfers();
						System.out.println("---------------------------------------------------------");
					} else if (num == 7 && devMode || num == 5 && !devMode) {
						return;
					} else {
						System.out.println("Error. No operation with this number");
					}
				} else {
					System.out.println("Error. The operation number must be an integer number");
				}
			} else {
				System.out.println("Error. You only need to enter the operation number");
			}
		}
	}

	private void mainMenu() {
		System.out.print("1. Add a user\n" +
				"2. View user balances\n" +
				"3. Perform a transfer\n" +
				"4. View all transactions for a specific user\n");

		if (devMode) {
			System.out.print("5. DEV - remove a transfer by ID\n" +
					"6. DEV - check transfer validity\n" +
					"7. Finish execution\n");
		} else {
			System.out.print("5. Finish execution\n");
		}
	}

	private void addUser() {
		String name;

		int balance;

		User user;

		while (true) {
			System.out.println("Enter a user name and a balance");
			input = scanner.nextLine().split(" ");

			if (input.length == 2) {
				if (isNumber(input[1])) {
					name = input[0];
					balance = Integer.parseInt(input[1]);

					if (balance >= 0) {
						user = new User(name, balance);
						transactionsService.addUser(user);
						System.out.println("User with id = " + user.getId() + " is added");
						return;
					} else {
						System.out.println("Error. The balance must be positive");
					}
				} else {
					System.out.println("Error. The balance must be integer number");
				}
			} else {
				System.out.println("Error. You only need to enter name and balance");
			}
		}
	}

	private void showBalances() {
		int userId;

		while (true) {
			System.out.println("Enter a user ID");
			input = scanner.nextLine().split(" ");

			if (input.length == 1) {

				if (isNumber(input[0])) {
					userId = Integer.parseInt(input[0]);

					if (userId >= 0) {
						try {
							System.out.println(transactionsService.getUserName(userId) + " - " +
									transactionsService.getUserBalance(userId));
							return;
						} catch (UserNotFoundException e) {
							System.out.println("Error. User not found");
						}
					} else {
						System.out.println("Error. User ID must be positive");
					}
				} else {
					System.out.println("Error. User ID must be a number");
				}
			} else {
				System.out.println("Error. You only need to enter user ID");
			}
		}
	}

	private void performTransfer() {
		int senderId;

		int recipientId;

		int amount;

		while (true) {
			System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
			input = scanner.nextLine().split(" ");

			if (input.length == 3) {

				if (isNumber(input[0])) {

					if (isNumber(input[1])) {

						if (isNumber(input[2])) {
							senderId = Integer.parseInt(input[0]);
							recipientId = Integer.parseInt(input[1]);
							amount = Integer.parseInt(input[2]);

							if (senderId >= 0 && recipientId >= 0 && amount >= 0) {
								try {
									transactionsService.transfer(senderId, recipientId, amount);
									System.out.println("The transfer is completed");
									return;
								} catch (IllegalTransactionException e) {
									System.out.println("Error. The sender does not have enough balance");
								} catch (UserNotFoundException e) {
									System.out.println("Error. User not found");
								}
							} else {
								System.out.println("Error. Sender ID, recipient ID and Amount must be positive");
							}
						} else {
							System.out.println("Error. Amount must be a number");
						}
					} else {
						System.out.println("Error. Recipient ID must be a number");
					}
				} else {
					System.out.println("Error. Sender ID must be a number");
				}
			} else {
				System.out.println("Error. You only need to enter a sender ID, a recipient ID, and a transfer amount");
			}
		}
	}

	private void showTransactions() {
		int userId;

		while (true) {
			System.out.println("Enter a user ID");
			input = scanner.nextLine().split(" ");

			if (input.length == 1) {

				if (isNumber(input[0])) {
					userId = Integer.parseInt(input[0]);

					if (userId >= 0) {
						try {
							for (Transaction t : transactionsService.getTransfers(userId)) {
								System.out.println(t);
							}
							return;
						} catch (UserNotFoundException e) {
							System.out.println("Error. User not found");
						}
					} else {
						System.out.println("Error. User ID must be positive");
					}
				} else {
					System.out.println("Error. User ID must be a number");
				}
			} else {
				System.out.println("Error. You only need to enter user ID");
			}
		}
	}

	private void removeTransfer() {
		int userId;

		String transferId;

		while (true) {
			System.out.println("Enter a user ID and a transfer ID");
			input = scanner.nextLine().split(" ");

			if (input.length == 2) {

				if (isNumber(input[0])) {
					userId = Integer.parseInt(input[0]);
					transferId = input[1];

					if (userId >= 0) {
						try {
							for (Transaction t : transactionsService.getTransfers(userId)) {
								if (t.getId().equals(transferId)) {
									System.out.printf("Transfer To %s(id = %d) %d removed\n",
											t.getRecipient().getName(), t.getRecipient().getId(),
											(t.getAmount() < 0) ? t.getAmount() * -1 : t.getAmount());
									transactionsService.removeTransactionFromUser(userId, transferId);
									return;
								}
							}
						} catch (UserNotFoundException e) {
							System.out.println("Error. User not found");
						} catch (TransactionNotFoundException e) {
							System.out.println("Error. Transaction not found");
						}
					} else {
						System.out.println("Error. User ID must be positive");
					}
				} else {
					System.out.println("Error. User ID must be a number");
				}
			} else {
				System.out.println("Error. You only need to enter user ID and a transfer ID");
			}
		}
	}

	private void checkTransfers() {
		System.out.println("Check results:");
		for (Transaction t : transactionsService.getInvalidTransaction()) {
			System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s %s %s(id = %d) for %s\n",
					t.getSender().getName(),
					t.getSender().getId(),
					t.getId(),
					(t.getCategory() == Transaction.TransferCategory.OUTCOME) ? "to" : "from",
					t.getRecipient().getName(),
					t.getRecipient().getId(),
					(t.getAmount() < 0) ? t.getAmount() * -1 : t.getAmount());
		}
	}
}
