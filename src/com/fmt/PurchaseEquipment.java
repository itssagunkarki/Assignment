package com.fmt;

public class PurchaseEquipment extends Equipment {
	private Double purchasePrice;

	/**
	 * @param itemCode
	 * @param itemType
	 * @param itemName
	 * @param model
	 * @param purchasePrice
	 */

	public PurchaseEquipment(Equipment equipment, Double purchasePrice) {
		super(equipment.getItemCode(), equipment.getItemType(), equipment.getItemName(), equipment.getModel());
		this.purchasePrice = purchasePrice;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	@Override
	public Double getTaxes() {
		double tax = 0;
		return tax;
	}

	@Override
	public Double getPrice() {
		return purchasePrice;
	}

}
