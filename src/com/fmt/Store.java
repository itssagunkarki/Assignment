package com.fmt;

import java.util.List;

public class Store {
	private String storeCode;
	private Person managerCode;
	private Address storeAddress;
	private List<Invoice> invoices;

	/**
	 * @param storeCode
	 * @param managerCode
	 * @param storeAddress
	 */
	public Store(String storeCode, Person managerCode, Address storeAddress) {
		this.storeCode = storeCode;
		this.managerCode = managerCode;
		this.storeAddress = storeAddress;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public Person getManager() {
		return managerCode;
	}

	public Address getStoreAddress() {
		return storeAddress;
	}

	public void addInvoices(Invoice invoice) {
		invoices.add(invoice);
	}

}
