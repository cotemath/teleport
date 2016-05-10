package com.appdirect.teleport.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.appdirect.teleport.domain.CurrencyEnum;
import com.appdirect.teleport.domain.TeleportablePayload;
import com.appdirect.teleport.entity.TeleportLocation;
import com.appdirect.teleport.entity.User;
import com.appdirect.teleport.error.InsufficientCreditException;
import com.appdirect.teleport.util.GeographicDistanceCalculator;

/**
 * An implementation of the TeleportationService API 
 *
 */
public class TeleportationServiceImpl implements TeleportationService {

	private static final int CREDITS_PER_USD = 5; //we arbitrarily define the cost of a credit to .20 USD
	
	private static final int CREDITS_PER_KILOMETER = 2; //2 Credits = 1 Kilometer
	
	@PersistenceUnit(unitName="h2") 
	private EntityManagerFactory entityManagerFactory;
	
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getGeographicDistanceInKilometers(TeleportLocation origin, TeleportLocation destination) {
		Assert.notNull(origin,"Origin not provided");
		Assert.notNull(destination, "Destination not provided");
		return GeographicDistanceCalculator.distanceInKilometer(origin.getGeographicCoordinates(), destination.getGeographicCoordinates());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculateTeleportationCostInCurrency(double distanceInKilometers, CurrencyEnum currency) {
		Assert.isTrue(distanceInKilometers>=0);
		Assert.notNull(currency);
		return (this.calculateTeleportationCostInCredits(distanceInKilometers) * currency.getExchangeRate());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int calculateTeleportationCostInCredits(double distanceInKilometers) {	
		Assert.isTrue(distanceInKilometers>=0);
		return (int)Math.ceil(distanceInKilometers * CREDITS_PER_KILOMETER);
	}

	/**
	 * {@inheritDoc}
	 * @throws InsufficientCreditException 
	 */
	@Transactional
	@Override
	public boolean teleport(User user, TeleportLocation origin, TeleportLocation destination, TeleportablePayload payload) throws InsufficientCreditException {
		double distance = this.getGeographicDistanceInKilometers(origin, destination);
		int credits = this.calculateTeleportationCostInCredits(distance);
		EntityManager entityManager = entityManagerFactory.createEntityManager();		
		user.addCredits(-1 * credits);
		entityManager.persist(user);
		return true;		
	}

	/**
	 * {@inheritDoc}
	 * @throws InsufficientCreditException 
	 */
	@Transactional
	@Override
	public int purchaseTeleportationCredits(User user, CurrencyEnum currency, double amount) throws InsufficientCreditException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		int creditsToAdd = (int)Math.floor(CREDITS_PER_USD * amount / currency.getExchangeRate());
		user.addCredits(creditsToAdd);
		entityManager.persist(user);
		return creditsToAdd;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<TeleportLocation> getAllAvailableTeleportLocations() {
		return entityManagerFactory.createEntityManager().createNamedQuery("TeleportLocation.getAllTeleportLocations", TeleportLocation.class).getResultList();		
	}

}
