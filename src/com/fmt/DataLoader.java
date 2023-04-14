package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataLoader {

	/**
	 * It loads Person.csv converts it into its class and returns a hashmap of
	 * person classes.
	 * 
	 * @return HashMap
	 */

	public static HashMap<String, Person> loadPerson() {
		HashMap<String, Person> result = new HashMap<String, Person>();
		try (Connection conn = ConnectionFactory.getConnection()) {
			String query = "SELECT p.personCode, p.lastName, p.firstName, a.street, a.city, s.stateCode, a.zipCode, c.countryCode, e.email FROM Person AS p \n"
					+ "LEFT JOIN Address AS a ON p.addressId = a.addressId\n"
					+ "LEFT JOIN State AS s ON s.stateId = a.stateId\n"
					+ "LEFT JOIN Country AS c ON c.countryId = s.countryId\n"
					+ "LEFT JOIN Email AS e ON e.personId = p.personId";
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String personCode = rs.getString("personCode");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");

				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("stateCode");
				String zip = rs.getString("zipCode");
				String country = rs.getString("countryCode");

				String email = rs.getString("email");

				// Check if the person is already in the map
				Person person = result.get(personCode);
				if (person == null) {
					List<String> emails = new ArrayList<>();
					emails.add(email);

					Address address = new Address(street, city, state, zip, country);
					person = new Person(personCode, lastName, firstName, address, emails);
					result.put(personCode, person);
				} else {
					person.getEmails().add(email);
				}
			}
			ps.close();
			rs.close();
			conn.close();

		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * It loads Stores.csv converts it into its class and returns a hashmap of Store
	 * classes.
	 * 
	 * @return HashMap
	 */

	public static HashMap<String, Store> loadStores() {
		HashMap<String, Store> result = new HashMap<String, Store>();
		HashMap<String, Person> personMap = loadPerson();

		try (Connection conn = ConnectionFactory.getConnection()) {

			String query = "select s.storeCode, a.street, a.city, st.stateCode, a.zipCode, c.countryCode, p.personCode as managerCode from Store as s\n"
					+ "left join Address as a on s.addressId = a.addressId\n"
					+ "left join State as st on st.stateId = a.stateId\n"
					+ "left join Country as c on c.countryId = st.countryId\n"
					+ "left join Person as p on p.personId = s.storeId";
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				String storeCode = rs.getString("storeCode");
				Person managerCode = personMap.get(rs.getString("managerCode"));

				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("stateCode");
				String zip = rs.getString("zipCode");
				String country = rs.getString("countryCode");

				Address address = new Address(street, city, state, zip, country);
				Store store = new Store(storeCode, managerCode, address);

				result.put(storeCode, store);

			}
			ps.close();
			rs.close();
			conn.close();

		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * It loads Items.csv converts it into its class and returns a hashmap of Item
	 * classes.
	 * 
	 * @return HashMap
	 */

	public static HashMap<String, Item> loadItems() {
		HashMap<String, Item> result = new HashMap<String, Item>();
		Item item = null;
		try (Connection conn = ConnectionFactory.getConnection()) {
			String query = "select itemCode, itemType, itemName, model, unit, unitPrice, hourlyRate from Item as i;";
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				String itemCode = rs.getString("itemCode");
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemName");
				if (itemType.equals("E")) {
					String model = rs.getString("model");
					item = new Equipment(itemCode, itemType, itemName, model);
				} else if (itemType.equals("P")) {
					String unit = rs.getString("unit");
					Double unitPrice = Double.parseDouble(rs.getString("unitPrice"));
					item = new Product(itemCode, itemType, itemName, unit, unitPrice);
				} else if (itemType.equals("S")) {
					Double hourlyRate = Double.parseDouble(rs.getString("hourlyRate"));
					item = new Service(itemCode, itemType, itemName, hourlyRate);
				}
				result.put(itemCode, item);
			}
			ps.close();
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static HashMap<String, Invoice> loadInvoiceItems(HashMap<String, Invoice> invoiceMap) {
		HashMap<String, Item> itemMap = loadItems();
		try (Connection conn = ConnectionFactory.getConnection()) {
			String query = "select i.invoiceCode, Item.itemType, Item.itemCode, ii.leaseOrPurchase, ii.leasePriceMonthly, ii.leaseStartDate, ii.leaseEndDate, ii.purchasePrice, ii.quantity, ii.numHours\n"
					+ "from InvoiceItem as ii\n" + "left join Invoice as i on i.invoiceId = ii.invoiceId\n"
					+ "left join Item on Item.itemId = ii.itemId";
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String invoiceCode = rs.getString("invoiceCode");
				String itemType = rs.getString("itemType");
				String leaseOrPurchase = rs.getString("leaseOrPurchase");
				String itemCode = rs.getString("itemCode");
				if (itemType.equals("E")) {
					if (leaseOrPurchase.equals("L")) {
						Double leasePriceMonthly = Double.parseDouble(rs.getString("leasePriceMonthly"));
						LocalDate leaseStartDate = LocalDate.parse(rs.getString("leaseStartDate"));
						LocalDate leaseEndDate = LocalDate.parse(rs.getString("leaseEndDate"));
						Equipment equipment = new LeaseEquipment((Equipment) itemMap.get(itemCode), leasePriceMonthly,
								leaseStartDate, leaseEndDate);
						invoiceMap.get(invoiceCode).getInvoiceItems().add(equipment);
					} else if (leaseOrPurchase.equals("P")) {
						Double purchasePrice = Double.parseDouble(rs.getString("purchasePrice"));
						Equipment equipment = new PurchaseEquipment((Equipment) itemMap.get(itemCode), purchasePrice);
						invoiceMap.get(invoiceCode).getInvoiceItems().add(equipment);
					}
				} else if (itemType.equals("P")) {
					Double quantity = Double.parseDouble(rs.getString("quantity"));
					Product product = new Product((Product) itemMap.get(itemCode), quantity);
					invoiceMap.get(invoiceCode).getInvoiceItems().add(product);
				} else if (itemType.equals("S")) {
					Double numHours = Double.parseDouble(rs.getString("numHours"));
					Service service = new Service((Service) itemMap.get(itemCode), numHours);
					invoiceMap.get(invoiceCode).getInvoiceItems().add(service);
				}
			}
			ps.close();
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return invoiceMap;
	}

	/**
	 * It loads invoice data and and returns a hashmap of invoices
	 * 
	 * @return
	 */

	public static HashMap<String, Invoice> loadInvoice() {
		HashMap<String, Person> persons = loadPerson();
		HashMap<String, Store> stores = loadStores();

		HashMap<String, Invoice> result = new HashMap<String, Invoice>();
		try (Connection conn = ConnectionFactory.getConnection()) {
			String query = "SELECT DISTINCT i.invoiceCode, s.storeCode, pc.personCode as customerCode, ps.personCode as salesPersonCode, i.InvoiceDate\n"
					+ "FROM Invoice as i \n" + "LEFT JOIN Person as pc ON i.customerId = pc.personId\n"
					+ "LEFT JOIN Person as ps ON i.salesPersonId = ps.personId\n"
					+ "LEFT JOIN Store as s ON s.storeId = i.storeId";

			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String invoiceCode = rs.getString("invoiceCode");
				Store store = stores.get(rs.getString("storeCode"));
				Person customer = persons.get(rs.getString("customerCode"));
				Person salesPerson = persons.get(rs.getString("salesPersonCode"));
				LocalDate invoiceDate = LocalDate.parse(rs.getString("invoiceDate"));
				Invoice invoice = new Invoice(invoiceCode, store, customer, salesPerson, invoiceDate,
						new ArrayList<>());

				result.put(invoiceCode, invoice);
			}
			ps.close();
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loadInvoiceItems(result);

	}
}
