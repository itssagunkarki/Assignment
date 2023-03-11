package com.fmt;

import java.util.HashMap;
import java.util.List;

public class InvoiceReport {
	private static HashMap<String, Invoice> invoiceWithItems() {
		HashMap<String, Invoice> invoices = DataLoader.loadInvoice();
		HashMap<String, List<Item>> invoiceItems = DataLoader.loadInvoiceItems();

		for (String i : invoiceItems.keySet()) {
			for (Item item : invoiceItems.get(i)) {
				invoices.get(i).addInvoiceItem(item);
			}
		}
		return invoices;

	}

	public static void reportByTotal() {
		HashMap<String, Invoice> invoices = invoiceWithItems();
		int totalItems = 0;
		Double totalTax = 0.0;
		Double totalTotal = 0.0;

		for (String i : invoices.keySet()) {

			totalItems += invoices.get(i).getInvoiceItems().size();
			totalTax += invoices.get(i).getTotalInvoiceTaxes();
			totalTotal += invoices.get(i).getTotalInvoicePrice();
		}

		System.out.println(
				"+-------------------------------------------------------------------------------------------");
		System.out.println(
				"| Summary Report - By Total                                                                |");
		System.out.println(
				"+-------------------------------------------------------------------------------------------+");
		System.out
				.println("Invoice #  Store        Customer                          Num Items       Tax        Total");

		for (String key : invoices.keySet()) {
			Invoice i = invoices.get(key);

//			System.out.printf("%s     %s     %s, %s                  %s          $    %.2f $ $%.2f\n",
//					i.getInvoiceCode(), i.getStore().getStoreCode(), i.getCustomer().getLastName(),
//					i.getCustomer().getFirstName(), i.getInvoiceItems().size(), i.getTotalInvoiceTaxes(),
//					i.getTotalInvoicePrice());

			System.out.printf("| %-10s| %-9s| %-31s| %-10s| $%8.2f| $%10.2f|\n", i.getInvoiceCode(),
					i.getStore().getStoreCode(), i.getCustomer().getLastName() + ", " + i.getCustomer().getFirstName(),
					i.getInvoiceItems().size(), i.getTotalInvoiceTaxes(), i.getTotalInvoicePrice());

		}
		System.out.println(
				"+-------------------------------------------------------------------------------------------+");
		System.out.printf("%60d          $%.2f   $ %.2f", totalItems, totalTax, totalTotal);

	}

	public static void salesReport() {
		HashMap<String, Invoice> invoices = invoiceWithItems();
		
		

	}

	public static void main(String[] args) {
		reportByTotal();

	}

}
