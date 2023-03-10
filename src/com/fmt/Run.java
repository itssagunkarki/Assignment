package com.fmt;

import java.util.HashMap;
import java.util.List;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, List<Item>> invoices = DataLoader.loadInvoice();
		for (String i: invoices.keySet()) {
			System.out.println(i+"  " + invoices.get(i).size());
//			for (Item j: invoices.get(i)) {
//				System.out.println(j.getItemCode());
//			}
		}

	}

}
