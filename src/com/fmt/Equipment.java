package com.fmt;

public class Equipment extends Item {
	private String model;

	/**
	 * @param itemCode
	 * @param itemTypeChar
	 * @param itemName
	 * @param model
	 */
	public Equipment(String itemCode, String itemType, String itemName, String model) {
		super(itemCode, itemType, itemName);
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	@Override
	public Double getTaxes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getPrice() {
		// TODO Auto-generated method stub
		return null;
	}

}
