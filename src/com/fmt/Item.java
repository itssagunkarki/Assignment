package com.fmt;

public class Item {
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

	public String getItemTypeChar() {
		return itemType;
	}

	public String getItemName() {
		return itemName;
	}
}