package com.fmt;

import java.time.LocalDate;
import java.util.List;

public class Invoice {
	private String invoiceCode;
	public Store store;
	public Person customer;
	public Person salesPerson;
	private LocalDate date;
	private List<Item> invoiceItems;

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
		for (Item i : invoiceItems) {
			totalTax += i.getTaxes();
		}
		return totalTax;
	}
	public Double getInvoicePrice() {
		Double totalPrice = 0.0;
		for (Item i : invoiceItems) {
			totalPrice += i.getPrice();
		}
		return totalPrice;
	}

	public Double getTotalInvoicePrice() {
		Double totalPrice = getTotalInvoiceTaxes()+getInvoicePrice();
		
		return totalPrice;
	}


	public void addInvoiceItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		invoiceItems.add(item);
	}
	private void getInvoiceReportHeader(){
		System.out.println("Invoice #" + getInvoiceCode());
		System.out.println("Store\t#" + getStore().getStoreCode());
		System.out.println("Date\t" + getDate());
		System.out.println("Customer:");
	
		System.out.println(getCustomer().getPersonDetails());
		System.out.println("Sales Person:");
		System.out.println(getSalesPerson().getPersonDetails());
		
	}
	
	private void getInvoiceReportFooter(Double grandTotal, Double tax, Double total){
		System.out.printf(
				"                                                          		-=-=-=-=-=-\n"
						+ "                                           		   Subtotal $   %-70.2f\n"
						+ "                                             			Tax $    %-70.2f\n"
						+ "                                          		Grand Total $   %-70.2f\n\n",
				total, tax, grandTotal);
		
	}

	public void getInvoiceReport() {
		getInvoiceReportHeader();

		System.out.println("Item                                                        		    Total\n"
				+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-               -=-=-=-=-=-");

		List<Item> items = getInvoiceItems();

		if (items.size() > 0) {

			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getItemType().equals("E")) {
					Item equipment = (Item) items.get(i);
					System.out.println(equipment.getItemCode());
					if (equipment instanceof PurchaseEquipment) {
						PurchaseEquipment purchaseEquipment = (PurchaseEquipment) equipment;
						System.out.printf(
								"%s				(Purchase) %s %s				\n"
										+ "								$ %-70.2f\n",
								purchaseEquipment.getItemCode(), purchaseEquipment.getItemName(),
								purchaseEquipment.getModel(), purchaseEquipment.getPrice());
					} else if (equipment instanceof LeaseEquipment) {
						LeaseEquipment leaseEquipment = (LeaseEquipment) equipment;

						System.out.printf(
								"%s				(lease) %s %s				\n \t\t %d days (%s -> %s)   @$%.2f / 30 days\n"
										+ "									$%-70.2f\n",
								leaseEquipment.getItemCode(), leaseEquipment.getItemName(), leaseEquipment.getModel(), leaseEquipment.getLeaseLength(), leaseEquipment.getStartDate().toString(), leaseEquipment.getEndDate().toString(), 
								leaseEquipment.getLeasePrice(),  leaseEquipment.getPrice());

					}
				} else if (items.get(i).getItemType().equals("P")) {
					Product product = (Product) items.get(i);
					System.out.printf(
							"%s				(Product) %s 				\n \t \t \t %.0f @ %.2f/%s\n"
									+ "									$%-70.2f\n",
							product.getItemCode(), product.getItemName(), product.getQuantity(), product.getUnitPrice(),
							product.getUnit(), product.getPrice());
				} else if (items.get(i).getItemType().equals("S")) {
					Service service = (Service) items.get(i);
					System.out.printf(
							"%s				(Service) %s 				\n \t \t \t %.1fhrs @ %.2f/hr\n"
									+ "									$%-70.2f\n",
							service.getItemCode(), service.getItemName(), service.getNumHours(),
							service.getHourlyRate(), service.getPrice());
				}
			}
			getInvoiceReportFooter(getTotalInvoicePrice(), getTotalInvoiceTaxes(), getInvoicePrice());
		} else {
			getInvoiceReportFooter(0.0, 0.0, 0.0);
		}
	}
}
