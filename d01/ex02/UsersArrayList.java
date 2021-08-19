public class UsersArrayList implements UsersList {
	private static final int START_NUMBER_OF_USERS = 10;

	private User[] users;

	private int size = 0;

	private int maxUsers = START_NUMBER_OF_USERS;

	public UsersArrayList() {
		this.users = new User[maxUsers];
	}

	public void addUser(User user) {
		if (size == maxUsers) {
			extendArray();
		}

		users[size] = user;
		size++;
	}

	public User getUserById(int id) throws UserNotFoundException {
		for (int i = 0; i < size; i++) {
			if (users[i].getId() == id) {
				return users[i];
			}
		}

		throw new UserNotFoundException();
	}

	public User getUserByIndex(int index) {
		return users[index];
	}

	public int getNumberOfUsers() {
		return size;
	}

	private void extendArray() {
		this.maxUsers = (int) (maxUsers * 1.5);

		User[] extendedArray = new User[maxUsers];

		for (int i = 0; i < size; i++) {
			extendedArray[i] = users[i];
		}

		this.users = extendedArray;
	}
}
