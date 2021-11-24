package edu.school21.classes;

import java.util.StringJoiner;

public class Car {
	private String brand;
	private String model;
	private String color;
	private long mileage;

	public Car() {
		this.brand = "Default brand";
		this.model = "Default model";
		this.color = "Default color";
		this.mileage = 0L;
	}

	public Car(String brand, String model, String color, long mileage) {
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.mileage = mileage;
	}

	public void drive(long mile) {
		this.mileage += mile;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
				.add("brand='" + brand + "'")
				.add("model='" + model + "'")
				.add("color='" + color + "'")
				.add("mileage=" + mileage)
				.toString();
	}
}
