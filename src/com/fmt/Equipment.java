package com.fmt;

import java.time.LocalDate;

public class Equipment extends Item {
	private String model;
	private Double purchasePrice;

	private Double leasePrice;
	private LocalDate startDate;
	private LocalDate endDate;

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

	public Equipment(Equipment equipment, Double purchasePrice) {
		super(equipment.getItemCode(), equipment.getItemType(), equipment.getItemName());
		this.model = equipment.getModel();
		this.purchasePrice = purchasePrice;
	}

	public Equipment(Equipment equipment, Double leasePrice, LocalDate startDate, LocalDate endDate) {
		super(equipment.getItemCode(), equipment.getItemType(), equipment.getItemName());
		this.model = equipment.getModel();
		this.leasePrice = leasePrice;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getModel() {
		return model;
	}

	public Double getLeaseOrPurchase() {
		return purchasePrice;
	}

	public Double getLeasePrice() {
		return leasePrice;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	@Override
	public Double getPrice() {
		return 0.0;
	}

	@Override
	public Double getTotalPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getTaxes() {
		// TODO Auto-generated method stub
		return null;
	}


}
