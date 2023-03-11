package com.fmt;

public class Service extends Item {
	private double hourlyRate;
	private double numHours;

	/**
	 * @param itemCode
	 * @param itemTypeChar
	 * @param itemName
	 * @param hourlyRate
	 */
	public Service(String itemCode, String itemType, String itemName, double hourlyRate) {
		super(itemCode, itemType, itemName);
		this.hourlyRate = hourlyRate;
	}
	
	public Service(Service service, double numHours) {
	    super(service.getItemCode(), service.getItemType(), service.getItemName());
	    this.hourlyRate = service.getHourlyRate();
	    this.numHours = numHours;
	}


	public double getHourlyRate() {
		return hourlyRate;
	}


	@Override
	public Double getTaxes() {
		return roundToCent(getPrice()*(0.0345));
	}

	@Override
	public Double getPrice() {
		return numHours*hourlyRate;
	}

	public double getNumHours() {
		return numHours;
	}

}
