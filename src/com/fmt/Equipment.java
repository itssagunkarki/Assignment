package com.fmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Equipment extends Item {
	private String model;
	private Double purchasePrice;

	private Double leasePrice;
	private LocalDate startDate;
	private LocalDate endDate;
	private String purchaseOrLease;

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

	public Equipment(Equipment equipment, Double purchasePrice, String purchaseOrLease) {
		super(equipment.getItemCode(), equipment.getItemType(), equipment.getItemName());
		this.model = equipment.getModel();
		this.purchasePrice = purchasePrice;
		this.purchaseOrLease = purchaseOrLease;
	}

	public Equipment(Equipment equipment, Double leasePrice, LocalDate startDate, LocalDate endDate,
			String purchaseOrLease) {
		super(equipment.getItemCode(), equipment.getItemType(), equipment.getItemName());
		this.model = equipment.getModel();
		this.leasePrice = leasePrice;
		this.startDate = startDate;
		this.endDate = endDate;
		this.purchaseOrLease = purchaseOrLease;
	}

	public String getModel() {
		return model;
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

	public String getPurchaseOrLease() {
		return purchaseOrLease;
	}

	@Override
	public Double getTaxes() {
		double tax = 0;
		double price = getPrice();
		if (purchaseOrLease.equals("P")) {
			tax += 0;
		} else if (purchaseOrLease.equals("L")) {
			if (price < 10000) {
				tax += 0;
			} else if ((price >= 10000) && (price < 100000)) {
				tax += 500;
			} else {
				tax += 1500;
			}
		}
		return tax;
	}

	@Override
	public Double getPrice() {
		double price = 0.0;
		if (purchaseOrLease.equals("P")) {
			price += purchasePrice;
		} else if (purchaseOrLease.equals("L")) {

			long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
			price = roundToCent((daysBetween + 1) * leasePrice / 30);
		}
		return price;
	}

}
