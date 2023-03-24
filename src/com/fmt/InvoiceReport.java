package com.fmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author sagunkarki
 *
 */

public class InvoiceReport {
	
	

	public static void reportByTotal() {
		HashMap<String, Invoice> invoices = DataLoader.loadInvoice();
		int totalItems = 0;
		Double totalTax = 0.0;
		Double totalTotal = 0.0;

		List<Invoice> sortedInvoices = new ArrayList<>(invoices.values());
		Collections.sort(sortedInvoices, (i1, i2) -> i2.getTotalInvoicePrice().compareTo(i1.getTotalInvoicePrice()));

		System.out.println(
				"+-------------------------------------------------------------------------------------------");
		System.out.println(
				"| Summary Report - By Total                                                                |");
		System.out.println(
				"+-------------------------------------------------------------------------------------------+");
		System.out
				.println("Invoice #  Store        Customer                          Num Items       Tax        Total");

		for (Invoice i : sortedInvoices) {
			totalItems += i.getInvoiceItems().size();
			totalTax += i.getTotalInvoiceTaxes();
			totalTotal += i.getTotalInvoicePrice();

			System.out.printf("| %-10s| %-9s| %-31s| %-10s| $%8.2f| $%10.2f|\n", i.getInvoiceCode(),
					i.getStore().getStoreCode(), i.getCustomer().getLastName() + ", " + i.getCustomer().getFirstName(),
					i.getInvoiceItems().size(), i.getTotalInvoiceTaxes(), i.getTotalInvoicePrice());

		}
		System.out.println(
				"+-------------------------------------------------------------------------------------------+");
		System.out.printf("%60d          $%.2f   $ %.2f\n\n", totalItems, totalTax, totalTotal);
	}

	private static HashMap<String, List<Invoice>> groupInvoicesByStore() {
		HashMap<String, List<Invoice>> result = new HashMap<String, List<Invoice>>();

		HashMap<String, Store> store = DataLoader.loadStores();
		HashMap<String, Invoice> invoices = DataLoader.loadInvoice();

		for (String i : store.keySet()) {

			String storeCode = store.get(i).getStoreCode();

			for (String j : invoices.keySet()) {
				if (result.get(storeCode) == null) {
					result.put(storeCode, new ArrayList<Invoice>());
				}
				if (invoices.get(j).getStore().getStoreCode().equals(storeCode)) {

					result.get(storeCode).add(invoices.get(j));
				}

			}

		}
		return result;
	}

	public static void salesReport() {
		HashMap<String, List<Invoice>> invoicesByStores = groupInvoicesByStore();

		System.out.println("+----------------------------------------------------------------+");
		System.out.println("| Store Sales Summary Report                                     |");
		System.out.println("+----------------------------------------------------------------+");
		System.out.println("| Store      Manager                        # Sales    Grand Total    ");

		Double totalSalesAllStores = 0.0;
		int totalSales = 0;
		for (String storeCode : invoicesByStores.keySet()) {
			List<Invoice> invoiceList = invoicesByStores.get(storeCode);
			String managerName = SearchIdCode.searchStore(storeCode).getManager().getName();
			Double totalSalesStore = 0.0;

			for (Invoice invoice : invoiceList) {
				totalSalesStore += invoice.getTotalInvoicePrice();
			}
			totalSalesAllStores += totalSalesStore;
			System.out.printf("| %-10s%-30s%-11d$%10.2f |\n", storeCode, managerName, invoiceList.size(),
					totalSalesStore);
			totalSales += invoiceList.size();
		}

		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("                                          %d          $ %.2f    \n\n", totalSales,
				totalSalesAllStores);

	}

	public static void getReportPerInvoice() {
		HashMap<String, Invoice> invoices = DataLoader.loadInvoice();
		for (String s : invoices.keySet()) {
			System.out.println("Invoice #" + s);
			System.out.println("Store\t#" + invoices.get(s).getStore().getStoreCode());
			System.out.println("Date\t" + invoices.get(s).getDate());
			System.out.println("Customer:");

			System.out.println(invoices.get(s).getCustomer().getPersonDetails());
			System.out.println("Sales Person:");
			System.out.println(invoices.get(s).getSalesPerson().getPersonDetails());

			System.out.println("Item                                                        		    Total\n"
					+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-               -=-=-=-=-=-");

			List<Item> items = invoices.get(s).getInvoiceItems();

			if (items.size() > 0) {

				for (int i = 0; i < items.size(); i++) {
					if (items.get(i).getItemType().equals("E")) {
						Equipment equipment = (Equipment) items.get(i);
						System.out.println(equipment.getItemCode());
						if (equipment.getPurchaseOrLease().equals("P")) {
							System.out.printf(
									"%s				(Purchase) %s %s				\n"
											+ "								$ %-70.2f\n",
									equipment.getItemCode(), equipment.getItemName(), equipment.getModel(),
									equipment.getPrice());
						} else {
							System.out.printf(
									"%s				(lease) %s %s				\n \t\t 365(%s -> %s) @$%.2f / 30 days\n"
											+ "									$%-70.2f\n",
									equipment.getItemCode(), equipment.getItemName(), equipment.getModel(),
									equipment.getStartDate().toString(), equipment.getEndDate().toString(),
									equipment.getLeasePrice(), equipment.getPrice());

						}
					} else if (items.get(i).getItemType().equals("P")) {
						Product product = (Product) items.get(i);
						System.out.printf(
								"%s				(Product) %s 				\n \t \t \t %.0f @ %.2f/%s\n"
										+ "									$%-70.2f\n",
								product.getItemCode(), product.getItemName(), product.getQuantity(),
								product.getUnitPrice(), product.getUnit(), product.getPrice());
					} else if (items.get(i).getItemType().equals("S")) {
						Service service = (Service) items.get(i);
						System.out.printf(
								"%s				(Service) %s 				\n \t \t \t %.1fhrs @ %.2f/hr\n"
										+ "									$%-70.2f\n",
								service.getItemCode(), service.getItemName(), service.getNumHours(),
								service.getHourlyRate(), service.getPrice());
					}
				}
				System.out.printf(
						"                                                          		-=-=-=-=-=-\n"
								+ "                                           		   Subtotal $   %-70.2f\n"
								+ "                                             			Tax $    %-70.2f\n"
								+ "                                          		Grand Total $   %-70.2f\n\n",
						invoices.get(s).getInvoicePrice(), invoices.get(s).getTotalInvoiceTaxes(),
						invoices.get(s).getTotalInvoicePrice());
			} else {
				System.out.printf("                                                          		-=-=-=-=-=-\n"
						+ "                                           		      Subtotal $  0\n"
						+ "                                             		   	   Tax $  0\n"
						+ "                                          		   Grand Total $  0\n\n");
			}
		}

	}

	public static void main(String[] args) {

		reportByTotal();
		salesReport();
		getReportPerInvoice();

	}

}
