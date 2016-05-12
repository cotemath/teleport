package com.appdirect.teleport.service;

import java.util.Collection;

import com.appdirect.teleport.domain.CurrencyEnum;
import com.appdirect.teleport.domain.TeleportablePayload;
import com.appdirect.teleport.entity.TeleportLocation;
import com.appdirect.teleport.entity.User;
import com.appdirect.teleport.error.InsufficientCreditException;

/**
 * An interface hat exposes teleportation functionalities
 *
 */
public interface TeleportationService {

	/**
	 * @return Returns the list of all available locations
	 */
	public Collection<TeleportLocation> getAllAvailableTeleportLocations(); 
	
	/**
	 * 
	 * @param origin -> the origin Point represented as latitude and longitude  
	 * @param destination -> the destination Point represented as latitude and longitude 
	 * @return the distance in Kilometers between the origin and the destination
	 */
	public double getGeographicDistanceInKilometers(TeleportLocation origin, TeleportLocation destination);
	
	/**
	 * @param originId -> the id of the starting point
	 * @param destinationId -> the id of the end point
	 * @param currency -> the target currency
	 * @return the teleportation cost in localized currency
	 */
	public double calculateTeleportationCostInCurrency(Long originId, Long destinationId, CurrencyEnum currency);
	
	/**
	 * @param distanceInKilometers -> the distance in kilometer between two points
	 * @return the teleportation cost in credits
	 */
	public int calculateTeleportationCostInCredits(double distanceInKilometers);
	
	/**
	 * Teleports the selected Payload (Person or Thing) from the origin to the destination
	 * 
	 * @param user -> the user whose account will get billed for teleportation
	 * @param originId -> the primary key of the origin Point represented as latitude and longitude  
	 * @param destination -> the primary key of the destination Point represented as latitude and longitude
	 * @param payload -> The payload to teleport
	 * @return true if teleportation was successful
	 * @throws InsufficientCreditException 
	 */
	public boolean teleport(User user, Long originId, Long destinationId, TeleportablePayload payload) throws InsufficientCreditException;
	
	/**
	 * Allows the user to purchase teleportation credits
	 * @param user -> the user purchasing teleportation credits
	 * @param currency -> the currency used to purchase credits
	 * @param amount -> the amount
	 * @return the number of credits purchased
	 * @throws InsufficientCreditException 
	 */
	public int purchaseTeleportationCredits(User user, CurrencyEnum currency, double amount) throws InsufficientCreditException;
	
}
