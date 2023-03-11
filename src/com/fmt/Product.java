package com.fmt;

public class Product extends Item {
	private String unit;
	private Double unitPrice;
	private Double quantity;

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
	
	public Product(Product product, Double quantity) {
	    super(product.getItemCode(), product.getItemType(), product.getItemName());
	    this.unit = product.getUnit();
	    this.unitPrice = product.getUnitPrice();
	    this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}


	@Override
	public Double getTaxes() {
		return roundToCent(getPrice() * (7.15/100));
	}

	@Override
	public Double getPrice() {
		return unitPrice * quantity;
	}

	public Double getQuantity() {
		return quantity;
	}

}
