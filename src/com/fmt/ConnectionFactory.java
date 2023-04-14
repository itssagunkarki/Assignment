package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.*;

public class ConnectionFactory {
	private static Logger log = LogManager.getLogger(ConnectionFactory.class);
	/**
	 * Connection parameters that are necessary for CSE's configuration
	 */
	private static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private static final String USERNAME = "skarki";
	private static final String PASSWORD = "OTrkr6Nc";
	private static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

	public static Connection getConnection() {
		try {
			log.info("Attempting to connect to the database");
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		} catch (SQLException e) {
			log.error("Error connecting to the database", e);
			return null;
		}
	}
}
