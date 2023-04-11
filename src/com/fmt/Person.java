package com.fmt;

import java.util.List;
import java.util.stream.Collectors;

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

	public String getName() {
		return this.lastName + ", " + this.firstName;
	}

	public String getPersonDetails() {
		String emailList = getEmails().stream().collect(Collectors.joining(", "));

		return String.format("%s (%s: [%s])\n%s\n", getName(), getPersonCode(), emailList,
				getAddress().getFormattedAddress());
	}

}
