package com.fmt;

public abstract class Item {
	private String itemCode;
	private String itemType;
	private String itemName;

	/**
	 * @param itemCode
	 * @param itemTypeChar
	 * @param itemName
	 */
	public Item(String itemCode, String itemType, String itemName) {
		this.itemCode = itemCode;
		this.itemType = itemType;
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getItemType() {
		return itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public Double roundToCent(Double num) {
		return Math.round(num * 100) / 100.0;
	}

	public Double getTotalPrice() {
		return roundToCent(getPrice() + getTaxes());
	}

	public abstract Double getTaxes();

	public abstract Double getPrice();
}