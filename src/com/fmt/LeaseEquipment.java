package com.fmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaseEquipment extends Equipment {

	private Double leasePrice;
	private LocalDate startDate;
	private LocalDate endDate;

	/**
	 * @param itemCode
	 * @param itemType
	 * @param itemName
	 * @param model
	 * @param leasePrice
	 * @param startDate
	 * @param endDate
	 */
	public LeaseEquipment(Equipment equipment, Double leasePrice, LocalDate startDate, LocalDate endDate) {
		super(equipment.getItemCode(), equipment.getItemType(), equipment.getItemName(), equipment.getModel());
		this.leasePrice = leasePrice;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public int getLeaseLength() {
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return (int) (daysBetween + 1);
	}

	@Override
	public Double getPrice() {
		Double price = roundToCent((getLeaseLength() + 1) * leasePrice / 30);
		return price;
	}

	@Override
	public Double getTaxes() {
		double tax = 0;
		double price = getPrice();
		if (price < 10000) {
			tax += 0;
		} else if ((price >= 10000) && (price < 100000)) {
			tax += 500;
		} else {
			tax += 1500;
		}
		return tax;
	}

}
