public class TransactionsLinkedList implements TransactionsList {
	private final Node header;

	private int size = 0;

	public TransactionsLinkedList() {
		this.header = new Node(null, null, null);
		this.header.next = this.header;
		this.header.prev = this.header;
	}

	public void addTransaction(Transaction transaction) {
		Node node = new Node(transaction, header, header.prev);
		node.next.prev = node;
		node.prev.next = node;
		size++;
	}

	public void removeTransactionById(String id) throws TransactionNotFoundException {
		Node iterator = header;

		for (int i = 0; i < size; i++) {
			iterator = iterator.next;
			if (iterator.transaction.getId().equals(id)) {
				iterator.next.prev = iterator.prev;
				iterator.prev.next = iterator.next;
				size--;
				return;
			}
		}

		throw new TransactionNotFoundException();
	}

	public Transaction[] toArray() {
		Transaction[] transactions = new Transaction[size];

		Node iterator = header;

		for (int i = 0; i < size; i++) {
			transactions[i] = iterator.next.transaction;
			iterator = iterator.next;
		}

		return transactions;
	}

	private static class Node {
		private Transaction transaction;
		private Node next;
		private Node prev;

		Node(Transaction transaction, Node next, Node prev) {
			this.transaction = transaction;
			this.next = next;
			this.prev = prev;
		}
	}
}
