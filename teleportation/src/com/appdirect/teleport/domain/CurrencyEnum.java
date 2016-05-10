package com.appdirect.teleport.domain;

/**
 * An enum representing currencies and the exchange rate factor from USD 
 *
 */
public enum CurrencyEnum {
	USD(1)
	,CAD(1.27)
	,EUR(1.12);
	
	private double exchangeRate;
	
	private CurrencyEnum(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}	
}
