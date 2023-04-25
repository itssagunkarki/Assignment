package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.*;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {
	private static Logger log = LogManager.getLogger(InvoiceData.class);

	/**
	 * Removes all records from all tables in the database.
	 */
	public static void clearDatabase() {
// 		drop table if exists InvoiceItem;
// drop table if exists Invoice;
// drop table if exists Item;
// drop table if exists Store;
// drop table if exists Email;
// drop table if exists Person;
// drop table if exists Address;
// drop table if exists State;
// drop table if exists Country;
		try (Connection conn = ConnectionFactory.getConnection()) {
			Statement stmt = conn.createStatement();
			// statements arranged according to foreign key dependencies
			stmt.executeUpdate("DELETE FROM InvoiceItem");
			stmt.executeUpdate("DELETE FROM Invoice");
			stmt.executeUpdate("DELETE FROM Item");
			stmt.executeUpdate("DELETE FROM Store");
			stmt.executeUpdate("DELETE FROM Email");
			stmt.executeUpdate("DELETE FROM Person");
			stmt.executeUpdate("DELETE FROM Address");
			stmt.executeUpdate("DELETE FROM State");
			stmt.executeUpdate("DELETE FROM Country");
			stmt.close();
		} catch (SQLException e) {
			log.error("Error clearing database", e);
		}


	}
	public static int addCountry(String countryCode, Connection conn){
		int countryId = -1;
		try {
			String sql = "INSERT INTO Country (countryCode) VALUES (?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, countryCode);
			ps.executeUpdate();
			// get the generated keys:
			ResultSet keys = ps.getGeneratedKeys();

			// if we only expect one:
			keys.next();
			countryId = keys.getInt(1);
		} catch (SQLException e) {
			log.error("Error searching for country", e);
		}
		return countryId;
	}

	public static int addState(String stateCode, String countryCode, Connection conn){
		int stateId = -1;
		int countryId = -1;
		try {
			String sql = "INSERT INTO State (stateCode, countryId) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, stateCode);

			countryId = SearchInDatabase.getCountryId(countryCode, conn);
			if (countryId == -1) {
				countryId = addCountry(countryCode, conn);
			}
			ps.setInt(2, countryId);

			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("Error adding state", e);
		}
		return stateId;
	}

	public static int addAddress (String street, String city, String state, String zip, String country, Connection conn) {
		try {
			String sql = "INSERT INTO Address (street, city, zipCode, stateId) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			int stateId = SearchInDatabase.getStateId(state, country, conn);
			if (stateId == -1) {
				stateId = addState(state, country, conn);
			}
			ps.setInt(4, SearchInDatabase.getStateId(state, country, conn));
			ps.executeUpdate();
			// get the generated keys:
			ResultSet keys = ps.getGeneratedKeys();

			// if we only expect one:
			keys.next();
			int addressId = keys.getInt(1);

			ps.close();

			return addressId;
		} catch (Exception e) {
			log.error("Error adding address", e);
			return -1;
		}
	}

	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			int personId = SearchInDatabase.getPersonId(personCode, conn);
			if (personId == -1) {
				int addressId = SearchInDatabase.getAddressId(street, city, state, zip, country, conn);
				if (addressId == -1) {
					addressId = addAddress(street, city, state, zip, country, conn);
				}
				String sql = "INSERT INTO Person (personCode, firstName, lastName, addressId) VALUES (?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, personCode);
				ps.setString(2, firstName);
				ps.setString(3, lastName);
				ps.setInt(4, addressId);
				ps.executeUpdate();
			} else {
				log.error("Person already exists");
				return;
			}
		} catch (SQLException e) {
			log.error("Error adding person", e);
		}

	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int personId = SearchInDatabase.getPersonId(personCode, conn);
			if (personId == -1) {
				log.error("Person does not exist");
				return;
			}
			String sql = "INSERT INTO Email (personId, email) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, personId);
			ps.setString(2, email);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("Error adding email", e);
		}

	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int storeId = SearchInDatabase.getStoreId(storeCode, conn);
			if (storeId == -1){
				int addressId = SearchInDatabase.getAddressId(street, city, state, zip, country, conn);
				if (addressId == -1) {
					addressId = addAddress(street, city, state, zip, country, conn);
				}
				int managerId = SearchInDatabase.getPersonId(managerCode, conn);
				if (managerId == -1) {
					log.error("Manager details not available");
					return;
				}
				String sql = "INSERT INTO Store (storeCode, managerId, addressId) VALUES (?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, storeCode);
				ps.setInt(2, managerId);
				ps.setInt(3, addressId);
				ps.executeUpdate();
			} else {
				log.error("Store already exists");
				return;
			}
		} catch (SQLException e) {
			log.error("Error adding store", e);
		}
	}

	/**
	 * Adds a product record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int itemId = SearchInDatabase.getItemId(code, conn);
			if (itemId == -1) {
				String sql = "INSERT INTO Item (itemCode, itemType, itemName, unit, unitPrice) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, code);
				ps.setString(2, "P");
				ps.setString(3, name);
				ps.setString(4, unit);
				ps.setDouble(5, pricePerUnit);
				ps.executeUpdate();
			} else {
				log.error("Product already exists");
				return;
			}
		} catch (SQLException e) {
			log.error("Error adding product", e);
		}

	}

	/**
	 * Adds an equipment record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>modelNumber</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addEquipment(String code, String name, String modelNumber) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int itemId = SearchInDatabase.getItemId(code, conn);
			if (itemId == -1) {
				String sql = "INSERT INTO Item (itemCode, itemType, itemName, model) VALUES (?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, code);
				ps.setString(2, "E");
				ps.setString(3, name);
				ps.setString(4, modelNumber);
				ps.executeUpdate();
			} else {
				log.error("Equipment already exists");
				return;
			}
		} catch (SQLException e) {
			log.error("Error adding equipment", e);
		}

	}

	/**
	 * Adds a service record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>costPerHour</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addService(String code, String name, double costPerHour) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int itemId = SearchInDatabase.getItemId(code, conn);
			if (itemId == -1) {
				String sql = "INSERT INTO Item (itemCode, itemType, itemName, HourlyRate) VALUES (?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, code);
				ps.setString(2, "S");
				ps.setString(3, name);
				ps.setDouble(4, costPerHour);
				ps.executeUpdate();
			} else {
				log.error("Service already exists");
				return;
			}
		} catch (SQLException e) {
			log.error("Error adding service", e);
		}

	}

	/**
	 * Adds an invoice record to the database with the given data.
	 *
	 * @param invoiceCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 * @param invoiceDate
	 */
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode,
			String invoiceDate) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int invoiceId = SearchInDatabase.getInvoiceId(invoiceCode, conn);
			if (invoiceId == -1) {
				int storeId = SearchInDatabase.getStoreId(storeCode, conn);
				if (storeId == -1) {
					log.error("Store details not available");
					return;
				}
				int customerId = SearchInDatabase.getPersonId(customerCode, conn);
				if (customerId == -1) {
					log.error("Customer details not available");
					return;
				}
				int salesPersonId = SearchInDatabase.getPersonId(salesPersonCode, conn);
				if (salesPersonId == -1) {
					log.error("Salesperson details not available");
					return;
				}
				String sql = "INSERT INTO Invoice (invoiceCode, storeId, customerId, salesPersonId, invoiceDate) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, invoiceCode);
				ps.setInt(2, storeId);
				ps.setInt(3, customerId);
				ps.setInt(4, salesPersonId);
				ps.setString(5, invoiceDate);
				ps.executeUpdate();
			} else {
				log.error("Invoice already exists");
				return;
			}
		} catch (SQLException e) {
			log.error("Error adding invoice", e);
		}

	}

	/**
	 * Adds a particular product (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {
		Product product = null;
		try (Connection conn = ConnectionFactory.getConnection()) {
			int invoiceId = SearchInDatabase.getInvoiceId(invoiceCode, conn);
			if (invoiceId == -1) {
				log.error("Invoice details not available");
				return;
			}
			int invoiceItemId = SearchInDatabase.getInvoiceItemId(invoiceId, itemCode, conn);
			if (invoiceItemId != -1) {
				log.error("Product already exists in invoice");
			} else {
				int itemId = SearchInDatabase.getItemId(itemCode, conn);
				if (itemId == -1) {
					log.error("Product details not available");
					return;
				}
				product = SearchInDatabase.getProduct(itemCode, conn);
				product.setQuantity(quantity/1.0);
	
				Double itemPrice = product.getPrice();
				Double itemTaxes = product.getTaxes();
	
				String sql = "INSERT INTO InvoiceItem (invoiceId, itemId, quantity, itemPrice, itemTaxes) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setInt(3, quantity);
				ps.setDouble(4, itemPrice);
				ps.setDouble(5, itemTaxes);
				ps.executeUpdate();
				
			}
		} catch (SQLException e) {
			log.error("Error adding product to invoice", e);
		}

	}

	/**
	 * Adds a particular equipment <i>purchase</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int invoiceId = SearchInDatabase.getInvoiceId(invoiceCode, conn);
			if (invoiceId == -1) {
				log.error("Invoice details not available");
				return;
			}
			int invoiceItemId = SearchInDatabase.getInvoiceItemId(invoiceId, itemCode, conn);
			if (invoiceItemId != -1) {
				log.error("Equipment already exists in invoice");
				return;
			} else {
				int itemId = SearchInDatabase.getItemId(itemCode, conn);
				if (itemId == -1) {
					log.error("Equipment details not available");
					return;
				}
				Equipment equipment = SearchInDatabase.getEquipment(itemCode, conn);
				Double itemPrice = equipment.getPrice();
				Double itemTaxes = equipment.getTaxes();
	
				String sql = "INSERT INTO InvoiceItem (invoiceId, itemId, itemPrice, itemTaxes, leaseOrPurchase, purchasePrice) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setDouble(3, itemPrice);
				ps.setDouble(4, itemTaxes);
				ps.setString(5, "P");
				ps.setDouble(6, purchasePrice);

				ps.executeUpdate();
			}
		} catch (SQLException e) {
			log.error("Error adding equipment to invoice", e);
		}
		
	}
	
	/**
	 * Adds a particular equipment <i>lease</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) with the given 30-day <code>periodFee</code> and
	 * <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate,
	String endDate) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int invoiceId = SearchInDatabase.getInvoiceId(invoiceCode, conn);
			if (invoiceId == -1) {
				log.error("Invoice details not available");
				return;
			}
			int invoiceItemId = SearchInDatabase.getInvoiceItemId(invoiceId, itemCode, conn);
			if (invoiceItemId != -1) {
				log.error("Equipment already exists in invoice");
				return;
			} else {
			
				int itemId = SearchInDatabase.getItemId(itemCode, conn);
				if (itemId == -1) {
					log.error("Equipment details not available");
					return;
				}
				Equipment equipment = SearchInDatabase.getEquipment(itemCode, conn);
				Double itemPrice = equipment.getPrice();
				Double itemTaxes = equipment.getTaxes();

				String sql = "INSERT INTO InvoiceItem (invoiceId, itemId, itemPrice, itemTaxes, leaseOrPurchase, leasePriceMonthly, leaseStartDate, leaseEndDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setDouble(3, itemPrice);
				ps.setDouble(4, itemTaxes);
				ps.setString(5, "L");
				ps.setDouble(6, periodFee);
				ps.setString(7, beginDate);
				ps.setString(8, endDate);


				ps.executeUpdate();
			}
		} catch (SQLException e) {
			log.error("Error adding equipment to invoice", e);
		}

	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified number of hours.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param billedHours
	 */
	public static void addServiceToInvoice(String invoiceCode, String itemCode, double billedHours) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			int invoiceId = SearchInDatabase.getInvoiceId(invoiceCode, conn);
			if (invoiceId == -1) {
				log.error("Invoice details not available");
				return;
			}
			int invoiceItemId = SearchInDatabase.getInvoiceItemId(invoiceId, itemCode, conn);
			if (invoiceItemId != -1) {
				log.error("Product already exists in invoice");
				return;
			} else {
				int itemId = SearchInDatabase.getItemId(itemCode, conn);
				if (itemId == -1) {
					log.error("Service details not available");
					return;
				}
				Service service = SearchInDatabase.getService(itemCode, conn);
				service.setNumHours(billedHours);
				Double itemPrice = service.getPrice();
				Double itemTaxes = service.getTaxes();

				String sql = "INSERT INTO InvoiceItem (invoiceId, itemId, itemPrice, itemTaxes, numHours) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, invoiceId);
				ps.setInt(2, itemId);
				ps.setDouble(3, itemPrice);
				ps.setDouble(4, itemTaxes);
				ps.setDouble(5, billedHours);


				ps.executeUpdate();
			}
		} catch (SQLException e) {
			log.error("Error adding service to invoice", e);
		}

	}

}