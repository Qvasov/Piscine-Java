public class UserIdsGenerator {
	private int lastId = 0;

	private static UserIdsGenerator instance = new UserIdsGenerator();

	private UserIdsGenerator() {
	}

	public static UserIdsGenerator getInstance() {
		return instance;
	}
	public int generateId() {
		return ++lastId;
	}
}
