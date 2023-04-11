package com.fmt;

import java.util.HashMap;
import java.util.Map;

public class Store {
	private String storeCode;
	private Person managerCode;
	private Address storeAddress;
	private Map<String, Invoice> invoices = new HashMap<>();

	/**
	 * @param storeCode
	 * @param managerCode
	 * @param storeAddress
	 */
	public Store(String storeCode, Person managerCode, Address storeAddress) {
		if (storeCode == null || managerCode == null || storeAddress == null) {
			throw new IllegalArgumentException("storeCode, managerCode, and storeAddress cannot be null");
		}

		this.storeCode = storeCode;
		this.managerCode = managerCode;
		this.storeAddress = storeAddress;
		this.invoices = new HashMap<>(invoices);
	}

	public String getStoreCode() {
		return storeCode;
	}

	public Person getManagerCode() {
		return managerCode;
	}

	public Address getStoreAddress() {
		return storeAddress;
	}

	public Map<String, Invoice> getInvoices() {
		return invoices;
	}

	public void addInvoice(Invoice invoice) {
		if (invoice == null) {
			throw new IllegalArgumentException("invoice cannot be null");
		}
		invoices.put(invoice.getInvoiceCode(), invoice);
	}
}
