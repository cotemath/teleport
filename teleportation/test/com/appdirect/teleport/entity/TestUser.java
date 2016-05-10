package com.appdirect.teleport.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.appdirect.teleport.error.InsufficientCreditException;

/**
 * Test class for User
 *
 */
public class TestUser {
	
	User aUser;
	
	@Before
	public void setup() {
		aUser = new User();
	}

	/**
	 * Adding positive and negative amounts
	 * @throws InsufficientCreditException
	 */
	@Test
	public void testAddCreditPositiveAndNegativeAmounts() throws InsufficientCreditException {
		aUser.addCredits(100);
		aUser.addCredits(-50);
		aUser.addCredits(25);
		Assert.assertEquals(75, aUser.getTeleportationCredits());
	}
	
	/**
	 * Removing more than the user has should throw an Exception
	 * @throws InsufficientCreditException
	 */
	@Test(expected=InsufficientCreditException.class)
	public void testAddAndRemoveTooMuch() throws InsufficientCreditException {
		aUser.addCredits(100);
		aUser.addCredits(-150);		
	}
}
