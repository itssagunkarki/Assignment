package com.fmt;

import java.util.HashMap;

public class SearchIdCode {
	public static Person searchPerson(String personId) {
		HashMap<String, Person> persons = DataLoader.loadPerson();
		return persons.get(personId);
	}

}
