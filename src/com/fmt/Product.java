package com.fmt;

public class Product extends Item {
	private String unit;
	private Double unitPrice;

	/**
	 * @param itemCode
	 * @param itemTypeChar
	 * @param itemName
	 * @param unit
	 * @param unitPrice
	 */
	public Product(String itemCode, String itemType, String itemName, String unit, Double unitPrice) {
		super(itemCode, itemType, itemName);
		this.unit = unit;
		this.unitPrice = unitPrice;
	}

	public String getUnit() {
		return unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

}
