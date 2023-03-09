package com.fmt;

import java.time.LocalDate;

public class Invoice {
	private String invoiceCode;
	public Store store;
	public Person customer;
	public Person salesPerson;
	private LocalDate date;

	/**
	 * @param invoiceCode
	 * @param store
	 * @param customer
	 * @param salesPerson
	 * @param date
	 */
	public Invoice(String invoiceCode, Store store, Person customer, Person salesPerson, LocalDate date) {
		this.invoiceCode = invoiceCode;
		this.store = store;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.date = date;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public LocalDate getDate() {
		return date;
	}


}
