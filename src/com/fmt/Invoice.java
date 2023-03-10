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
	public Invoice(String invoiceCode, Store store, Person customer, Person salesPerson, LocalDate date, List<Item> invoiceItems) {
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

}
