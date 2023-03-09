package com.fmt;

public class InvoiceEquipment<T extends Equipment> {
	private String behavior;
	private double price;

	/**
	 * @param behavior
	 * @param price
	 */
	public InvoiceEquipment(String behavior, double price) {
		super();
		this.behavior = behavior;
		this.price = price;
	}

	public String getBehavior() {
		return behavior;
	}

	public double getPrice() {
		return price;
	}

}
