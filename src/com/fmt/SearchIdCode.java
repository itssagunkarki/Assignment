package com.fmt;

import java.util.HashMap;

public class SearchIdCode {
	public static Person searchPerson(String personId) {
		HashMap<String, Person> persons = DataLoader.loadPerson();
		return persons.get(personId);
	}
	public static Store searchStore(String storeId) {
		HashMap<String, Store> persons = DataLoader.loadStores();
		return persons.get(storeId);
	}
	public static Item searchItem(String itemId) {
		HashMap<String, Item> persons = DataLoader.loadItems();
		return persons.get(itemId);
	}

}
