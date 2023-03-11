package com.fmt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class DataConverter {

	/**
	 * It takes a list of objects and exports it into json format.
	 * 
	 * @param HashMap<String, T>, String
	 * @return
	 */
	private static <T> void saveJSON(HashMap<String, T> data, String filepath) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (FileWriter writer = new FileWriter(filepath)) {
			for (String key : data.keySet()) {
				gson.toJson(data.get(key), writer);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static <T> void saveXML(HashMap<String, T> data, String filepath) {
		XStream xstream = new XStream(new StaxDriver());
		try {
			FileWriter fw = new FileWriter(filepath);
			for (String key : data.keySet()) {
				String xml = xstream.toXML(data.get(key));
				fw.write(xml);
			}

			fw.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public static void savePersonsJSON() {
		HashMap<String, Person> persons = DataLoader.loadPerson();
		saveJSON(persons, "data/Persons.json");
	}

	public static void savePersonsXML() {
		HashMap<String, Person> persons = DataLoader.loadPerson();
		saveXML(persons, "data/Persons.xml");
	}

	public static void saveStoresJSON() {
		HashMap<String, Store> stores = DataLoader.loadStores();
		saveJSON(stores, "data/Stores.json");
	}

	public static void saveStoresXML() {
		HashMap<String, Store> stores = DataLoader.loadStores();
		saveXML(stores, "data/Stores.xml");
	}

	public static void saveItemsJSON() {
		HashMap<String, Item> items = DataLoader.loadItems();
		saveJSON(items, "data/Items.json");
	}

	public static void saveItemsXML() {
		HashMap<String, Item> items = DataLoader.loadItems();
		saveXML(items, "data/Items.xml");
	}

	public static void main(String[] args) {
		savePersonsXML();
		savePersonsJSON();
		saveStoresXML();
		saveStoresJSON();
		saveItemsXML();
//		saveItemsJSON();
	}
}
