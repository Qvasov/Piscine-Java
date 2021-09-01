package edu.school21.models;

public class Product {
	private long id;
	private String name;
	private int cost;

	public Product(long id, String name, int cost) {
		this.id = id;
		this.name = name;
		this.cost = cost;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Product product = (Product) o;

		if (id != product.id) return false;
		if (cost != product.cost) return false;
		return name != null ? name.equals(product.name) : product.name == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + cost;
		return result;
	}
}
