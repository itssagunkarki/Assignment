package com.fmt;

import java.time.LocalDate;
import java.util.List;

public class Invoice {
	private String invoiceCode;
	public Store store;
	public Person customer;
	public Person salesPerson;
	private LocalDate date;
	List<Item> invoiceItems;

	/**
	 * @param invoiceCode
	 * @param store
	 * @param customer
	 * @param salesPerson
	 * @param date
	 */
	public Invoice(String invoiceCode, Store store, Person customer, Person salesPerson, LocalDate date,
			List<Item> invoiceItems) {
		this.invoiceCode = invoiceCode;
		this.store = store;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.date = date;
		this.invoiceItems = invoiceItems;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public LocalDate getDate() {
		return date;
	}

	public Store getStore() {
		return store;
	}

	public Person getCustomer() {
		return customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public List<Item> getInvoiceItems() {
		return invoiceItems;
	}
	
	public Double getTotalInvoiceTaxes() {
		Double totalTax = 0.0;
		for (Item i: invoiceItems) {
			totalTax += i.getTaxes();
		}
		return totalTax;
	}
	
	public Double getTotalInvoicePrice() {
		Double totalPrice = 0.0;
		for (Item i: invoiceItems) {
			totalPrice += i.getTotalPrice();
		}
		return totalPrice;
	}
	public Double getInvoicePrice() {
		Double totalPrice = 0.0;
		for (Item i: invoiceItems) {
			totalPrice += i.getPrice();
		}
		return totalPrice;
	}
	
	public void addInvoiceItem(Item item) {
		invoiceItems.add(item);
	}

}
