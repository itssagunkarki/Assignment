package com.fmt;

public class Service extends Item {
	private double hourlyRate;

	/**
	 * @param itemCode
	 * @param itemTypeChar
	 * @param itemName
	 * @param hourlyRate
	 */
	public Service(String itemCode, String itemType, String itemName, double hourlyRate) {
		super(itemCode, itemType, itemName);
		this.hourlyRate = hourlyRate;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

}
