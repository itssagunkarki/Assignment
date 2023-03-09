package com.fmt;

import java.util.List;

public class Person {
	private String PersonCode;
	private String lastName;
	private String firstName;
	Address address;
	List<String> emails;

	/**
	 * @param personCode
	 * @param lastName
	 * @param firstName
	 * @param address
	 * @param emails
	 */
	public Person(String personCode, String lastName, String firstName, Address address, List<String> emails) {
		super();
		PersonCode = personCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.emails = emails;
	}

	public String getPersonCode() {
		return PersonCode;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Address getAddress() {
		return address;
	}

	public List<String> getEmails() {
		return emails;
	}

}
