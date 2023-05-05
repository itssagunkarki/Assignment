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
	public static HashMap<String, Invoice> invoices = DataLoader.loadInvoice();
	public static HashMap<String, Store> store = DataLoader.loadStores();

	public static void reportByTotal() {
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
		int totalNumSales = 0;
		HashMap<String, String> storeManager = new HashMap<String, String>();
		for (String storeCode : invoicesByStores.keySet()) {
			List<Invoice> invoiceList = invoicesByStores.get(storeCode);
			String managerName = store.get(storeCode).getManagerCode().getName();
			Double totalSalesStore = 0.0;

			for (Invoice invoice : invoiceList) {
				totalSalesStore += invoice.getTotalInvoicePrice();
			}
			totalSalesAllStores += totalSalesStore;
			// System.out.printf("| %-10s%-30s%-11d$%10.2f |\n", storeCode, managerName, invoiceList.size(),
			// 		totalSalesStore);
			String formattedString = String.format("| %-10s%-30s%-11d$%10.2f |", storeCode, managerName, invoiceList.size(), totalSalesStore);
			storeManager.get(storeManager.put(storeCode, formattedString));


			totalNumSales += invoiceList.size();
		}
		List<String> sortedManager = new ArrayList<>(storeManager.keySet());
		Collections.sort(sortedManager, (i1, i2) -> i2.compareTo(i1));

		for (String s : sortedManager) {
			System.out.println(storeManager.get(s));
		}

		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("                                          %d          $ %.2f    \n\n", totalNumSales,
				totalSalesAllStores);

	}

	/**
	 * fills the invoiceList with invoices from the hashmap of invoices which we get from database
	 * 
	 * @param <T>
	 * @param invoiceList
	 * @return
	 */
	private static <T> LinkedList<Invoice> fillInvoicesList(LinkedList<Invoice> invoiceList){
		HashMap<String, Invoice> invoices = DataLoader.loadInvoice();
		
		// create a linkedlist from the invoices of hashmap
		for (String s : invoices.keySet()) {
			invoiceList.add(invoices.get(s));
		}
		return invoiceList;
	}
	
	/**
	 * prints the sales report
	 * 
	 * @param <T>
	 * @param invoiceList
	 * @param salesBy
	 */
	private static void printSalesReport(LinkedList<Invoice> invoiceList, String salesBy){
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.printf("| %-82s |\n", String.format("Sales by %s", salesBy));

		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.printf(" %-10s %-10s %-20s %-20s %10s \n", "Sale", "Store", "Customer", "Salesperson", "Total");
		
		
		for (Invoice i : invoiceList) {
			// invoiceCode, storeCode, customerCode, salesPersonCode, total
			System.out.printf(" %-10s %-10s %-20s %-25s $%10.2f \n", i.getInvoiceCode(), i.getStore().getStoreCode(), i.getCustomer().getName(), i.getSalesPerson().getName(), i.getTotalInvoicePrice());
		}
		// System.out.println("+------------------------------------------------------------------------------------+");
		
	}
	
	public static void getReportPerInvoice() {
		HashMap<String, Invoice> invoices = DataLoader.loadInvoice();
		for (String s : invoices.keySet()) {
			invoices.get(s).getInvoiceReport();
		}
	}

	public static void getReportByCustomer(){
		LinkedList<Invoice> invoiceList = new LinkedList<Invoice>(Invoice.customerComparator);
		invoiceList = fillInvoicesList(invoiceList);
		printSalesReport(invoiceList, "Customer");
	}	

	public static void getReportByInvoiceTotal(){
		LinkedList<Invoice> invoiceList = new LinkedList<Invoice>(Invoice.invoiceTotalComparator);
		invoiceList = fillInvoicesList(invoiceList);
		printSalesReport(invoiceList, "Total");
	}
	public static void getReportByStore(){
		LinkedList<Invoice> invoiceList = new LinkedList<Invoice>(Invoice.salesByStoreComparator);
		invoiceList = fillInvoicesList(invoiceList);
		printSalesReport(invoiceList, "Store");
	}



	public static void main(String[] args) {

		getReportByCustomer();
		getReportByInvoiceTotal();
		getReportByStore();


	}

}
