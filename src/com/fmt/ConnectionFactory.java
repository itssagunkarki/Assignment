package com.fmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionFactory {

	/**
	 * Connection parameters that are necessary for CSE's configuration
	 */
	private static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private static final String USERNAME = "skarki";
	private static final String PASSWORD = "OTrkr6Nc";
	private static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

}
