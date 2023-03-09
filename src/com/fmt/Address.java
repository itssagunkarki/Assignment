package com.fmt;

public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;

	/**
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public Address(String street, String city, String state, String zip, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}

}
