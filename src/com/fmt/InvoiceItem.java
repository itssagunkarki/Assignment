package com.fmt;

import java.time.LocalDate;

public class InvoiceItem  {
	Invoice invoice;
	Item item;
	private Character behavior;
	private Double purchasedPrice;
	private Double monthFee;

	private LocalDate startDate;
	private LocalDate endDate;
	private Double quantity;
	private int numHours;

	/**
	 * @param invoice
	 * @param item
	 * 
	 *                For equipment purchasing
	 */
	public InvoiceItem(Invoice invoice, Item item, Character behavior, Double purchasedPrice) {
		this.invoice = invoice;
		this.item = item;
		this.behavior = behavior;
		this.purchasedPrice = purchasedPrice;
	}

	/**
	 * @param invoice
	 * @param item
	 * 
	 *                For equipment leasing
	 */

	public InvoiceItem(Invoice invoice, Item item, Character behavior, Double monthFee, LocalDate startDate,
			LocalDate endDate) {
		this.invoice = invoice;
		this.item = item;
		this.behavior = behavior;
		this.monthFee = monthFee;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @param invoice
	 * @param item
	 * 
	 *                For Products leasing
	 */

	public InvoiceItem(Invoice invoice, Item item, Double quantity) {
		this.invoice = invoice;
		this.item = item;
		this.quantity = quantity;
	}

	/**
	 * @param invoice
	 * @param item
	 * 
	 *                For Services leasing
	 */

	public InvoiceItem(Invoice invoice, Item item, int numHours) {
		this.invoice = invoice;
		this.item = item;
		this.numHours = numHours;
	}

	public Character getBehavior() {
		return behavior;
	}

	public Double getPurchasedPrice() {
		return purchasedPrice;
	}

	public Double getMonthFee() {
		return monthFee;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public <s extends Service> int getNumHours() {
		return numHours;
	}

}
