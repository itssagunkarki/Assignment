package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.*;

public class SearchInDatabase {
	private static Logger log = LogManager.getLogger(SearchInDatabase.class);

	/**
	 * Method to get the personId from the database
	 * 
	 * @param personCode
	 * @param conn
	 * @return personId
	 * 
	 *         if personCode is not found, return -1
	 */
	public static int getPersonId(String personCode, Connection conn) {
		try {
			String sql = "SELECT personId FROM Person WHERE personCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, personCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				return rs.getInt("personId");
			}
		} catch (SQLException e) {
			log.error("Error searching for person", e);
			return -1;
		}
	}


    public static int getStateId(String stateCode, String countryCode, Connection conn) {
        try {
            String sql = "SELECT stateId FROM State as s LEFT JOIN Country as c on s.countryId = c.countryId WHERE stateCode = ? AND countryCode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, stateCode);
            ps.setString(2, countryCode);
          
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                return -1;
            } else {
                return rs.getInt("stateId");
            }
        } catch (SQLException e) {
            log.error("Error searching for state", e);
            return -1;
        }
    }

	/**
	 * Method to get the addressId from the database
	 * 
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param conn
	 * @return addressId
	 * 
	 *         if address is not found, return -1
	 */
	public static int getAddressId(String street, String city, String state, String zip, String country,
			Connection conn) {
		try {

			String sql = "select addressId from Address as a\n" + "left join State as s on a.stateId = s.stateId\n"
					+ "left join Country as c on s.countryId = c.countryId\n"
					+ "where a.street = ? and a.city =? and a.zipCode = ? and (s.stateCode = ? or s.stateName=?) and (c.countryCode = ? or c.countryName = ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			ps.setString(4, state);
			ps.setString(5, state);
			ps.setString(6, country);
			ps.setString(7, country);

            ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				return rs.getInt("addressId");
			}
		} catch (SQLException e) {
			log.error("Error searching for address", e);
			return -1;
		}
	}

	/**
	 * Method to get the emailId associated with personCode from the database
	 * 
	 * @param email
	 * @param personCode
	 * @param conn
	 * @return emailId
	 * 
	 *         if email is not found, return -1
	 */
	public static int getEmailId(String email, String personCode, Connection conn) {
		try {
			String sql = "SELECT * FROM Email AS e LEFT JOIN Person AS p ON e.emailId = p.personId where e.email = ? and p.personCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, personCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				return rs.getInt("emailId");
			}
		} catch (SQLException e) {
			log.error("Error searching for email", e);
			return -1;
		}
	}

	/**
	 * Method to get the emailId from the database
	 * 
	 * @param storeCode
	 * @param conn
	 * @return emailId
	 * 
	 * if email is not found, return -1
	 */
	public static int getStoreId(String storeCode, Connection conn) {
		try {
			String sql = "SELECT storeId FROM Store WHERE storeCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, storeCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				return rs.getInt("storeId");
			}
		} catch (SQLException e) {
			log.error("Error searching for store", e);
			return -1;
		}
	}

    /**
     * Method to get the itemId from the database
     * @param itemCode
     * @param conn
     * @return itemId
     * 
     * if itemCode is not found, return -1
     */
    public static int getItemId(String itemCode, Connection conn) {
        try {
            String sql = "SELECT itemId FROM Item WHERE itemCode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, itemCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                return -1;
            } else {
                return rs.getInt("itemId");
            }
        } catch (SQLException e) {
            log.error("Error searching for item", e);
            return -1;
        }
    }

    /**
     * Method to get the invoiceId from the database
     * @param invoiceCode
     * @param itemCode
     * @param conn
     * @return invoiceId
     * 
     * if invoiceCode is not found, return -1
     */
    public static int getInvoiceItemId(String invoiceCode, String itemCode, Connection conn) {
        try {
            String sql = "SELECT invoiceItemId FROM InvoiceItem AS ii\n" +
                    "LEFT JOIN Invoice AS i ON ii.invoiceId = i.invoiceId\n" +
                    "LEFT JOIN Item AS it ON ii.itemId = it.itemId\n" +
                    "WHERE i.invoiceCode = ? AND it.itemCode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, invoiceCode);
            ps.setString(2, itemCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                return -1;
            } else {
                return rs.getInt("invoiceItemId");
            }
        } catch (SQLException e) {
            log.error("Error searching for invoice item", e);
            return -1;
        }
    }


    public static int getInvoiceId(String invoiceCode, Connection conn) {
        try{
			String sql = "SELECT invoiceId FROM Invoice WHERE invoiceCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				return rs.getInt("invoiceId");
			}
		} catch (SQLException e) {
			log.error("Error searching for invoice", e);
			return -1;
		}

    }

	public static Product getProduct(String itemCode, Connection conn) {
		Product product = null;
		try {
			String sql = "SELECT i.itemType, i.itemName, i.unit, i.unitPrice FROM Item as i WHERE i.itemCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			} else {
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemName");
				String unit = rs.getString("unit");
				double unitPrice = rs.getDouble("unitPrice");
				product = new Product(itemCode, itemType, itemName, unit, unitPrice);
			}
		} catch (SQLException e) {
			log.error("Error searching for product", e);
		}
		return product;
	}

	public static Product getInvoiceProduct(String invoiceCode, String invoiceItemCode, Connection conn){
		Product product = getProduct(invoiceItemCode, conn);
		try {
			String sql = "select ii.quantity from InvoiceItem as ii\n" +
					"left join Invoice as i on ii.invoiceId = i.invoiceId\n" +
					"left join Item as it on ii.itemId = it.itemId\n" +
					"where i.invoiceCode = ? and it.itemCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, invoiceCode);
			ps.setString(2, invoiceItemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			} else {
				Double quantity = rs.getDouble("quantity");
				product = new Product(product, quantity);
			}
		} catch (SQLException e) {
			log.error("Error searching for product in invoice", e);
		}
		return product;
	}

	public static Equipment getEquipment(String itemCode, Connection conn){
		Equipment equipment = null;
		try {
			String sql = "SELECT i.itemType, i.itemName, i.model FROM Item as i WHERE i.itemCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			} else {
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemName");
				String model = rs.getString("model");
				equipment = new Equipment(itemCode, itemType, itemName, model);
			}
		} catch (SQLException e) {
			log.error("Error searching for equipment", e);
		}
		return equipment;
	}

	public static Service getService(String itemCode, Connection conn){
		Service service = null;
		try {
			String sql = "SELECT i.itemType, i.itemName, i.hourlyRate FROM Item as i where i.itemCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			} else {
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemName");
				double hourlyRate = rs.getDouble("hourlyRate");
				service = new Service(itemCode, itemType, itemName, hourlyRate);
			}
		} catch (SQLException e) {
			log.error("Error searching for service", e);
		}
		return service;
	}


    public static int getCountryId(String countryCode, Connection conn) {
        try {
			String sql = "SELECT countryId FROM Country WHERE countryCode = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, countryCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				return rs.getInt("countryId");
			}
		} catch (SQLException e) {
			log.error("Error searching for country", e);
			return -1;
		}
    }

	public static int searchInvoiceItem(int invoiceId, String itemCode, Connection conn){
		try {
			String sql = "SELECT invoiceItemId FROM InvoiceItem WHERE itemCode = ? AND invoiceId = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ps.setInt(2, invoiceId);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				return rs.getInt("invoiceItemId");
			}
		} catch (SQLException e) {
			log.error("Error searching for invoice item", e);
			return -1;
		}
	}


    public static int getInvoiceItemId(int invoiceId, String itemCode, Connection conn) {
		int invoiceItemId = -1;
        try {
			String sql = "SELECT invoiceItemId FROM InvoiceItem as ii LEFT JOIN Item as i ON i.itemId = ii.itemId LEFT JOIN Invoice on Invoice.invoiceId = ii.invoiceId WHERE i.itemCode = ? AND ii.invoiceId = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itemCode);
			ps.setInt(2, invoiceId);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return -1;
			} else {
				invoiceItemId = rs.getInt("invoiceItemId");
			}
		} catch (SQLException e) {
			log.error("Error searching for invoice item", e);
		}
		return invoiceItemId;
    }


	public static Item getItem(String itemCode, Connection conn) {
		return null;
	}
}
