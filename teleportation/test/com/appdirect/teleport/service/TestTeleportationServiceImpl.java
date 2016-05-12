package com.appdirect.teleport.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.teleport.domain.CurrencyEnum;
import com.appdirect.teleport.domain.Location;
import com.appdirect.teleport.domain.TeleportablePayload;
import com.appdirect.teleport.entity.TeleportLocation;
import com.appdirect.teleport.entity.User;
import com.appdirect.teleport.error.InsufficientCreditException;

/**
 * Test class for TeleportationServiceImpl
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestTeleportationServiceImpl {

	private static final long AN_ID=1;
	
	private static final String A_CITY="a city";
	
	private static final String A_COUNTRY="a country";
	
	private static final long ANOTHER_ID=2;
	
	private static final String ANOTHER_CITY="another city";
	
	private static final String ANOTHER_COUNTRY="another country";
	
	private static final int A_LATITUDE = 2;
	
	private static final int ANOTHER_LATITUDE = 10;
	
	private static final int A_LONGITUDE = 45;
	
	private static final int ANOTHER_LONGITUDE = 50;
	
	private final static Location A_LOCATION = new Location(20,20);
	
	private final static Location ANOTHER_LOCATION_CLOSE_BY = new Location(45,160);
	
	private final static Location ANOTHER_LOCATION_FAR_AWAY = new Location(45,160);
	
	@Mock
	private EntityManagerFactory mockedEntityManagerFactory;

	@Mock
	private EntityManager mockedEntityManager;
	
	@Mock
	private TypedQuery<TeleportLocation> mockedTypedQuery;
	
	@Mock
	private User mockedUser;
	
	@Mock
	private TeleportLocation mockedOrigin;
	
	@Mock
	private TeleportLocation mockedDestination;
	
	@Mock
	private TeleportablePayload mockedPayload;
	
    private TeleportationServiceImpl teleportationService;

    @SuppressWarnings("unchecked")
	@Before
    public void setup() {      
    	when(mockedEntityManagerFactory.createEntityManager()).thenReturn(mockedEntityManager);
    	when(mockedEntityManager.createNamedQuery(anyString(),any(Class.class))).thenReturn(mockedTypedQuery);
        teleportationService = new TeleportationServiceImpl();
        teleportationService.setEntityManagerFactory(mockedEntityManagerFactory);        
    }
	
    /**
     * Origin is null, method should throw IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
	public void testGetGeographicDistanceInKilometersOriginIsNull() {
    	TeleportLocation destination = new TeleportLocation(ANOTHER_ID, ANOTHER_CITY, ANOTHER_COUNTRY, new Location(ANOTHER_LATITUDE, ANOTHER_LONGITUDE));
		teleportationService.getGeographicDistanceInKilometers(null, destination);
	}
    
    /**
     * Destination is null, method should throw IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
	public void testGetGeographicDistanceInKilometersDestinationIsNull() {
    	TeleportLocation origin = new TeleportLocation(AN_ID, A_CITY, A_COUNTRY, new Location(A_LATITUDE, A_LONGITUDE));
		teleportationService.getGeographicDistanceInKilometers(origin, null);
	}
    
    /**
     * origin equals destination, distance should be 0
     */
    @Test
	public void testGetGeographicDistanceInKilometersOriginIsDestinationSuccess() {
		TeleportLocation origin = new TeleportLocation(AN_ID, A_CITY, A_COUNTRY, new Location(A_LATITUDE, A_LONGITUDE));
		Assert.assertTrue(teleportationService.getGeographicDistanceInKilometers(origin, origin)==0);
	}
    
    /**
     * origin is different then the destination
     */
	@Test
	public void testGetGeographicDistanceInKilometersSuccess() {
		TeleportLocation origin = new TeleportLocation(AN_ID, A_CITY, A_COUNTRY, new Location(A_LATITUDE, A_LONGITUDE));
		TeleportLocation destination = new TeleportLocation(ANOTHER_ID, ANOTHER_CITY, ANOTHER_COUNTRY, new Location(ANOTHER_LATITUDE, ANOTHER_LONGITUDE));
		Assert.assertTrue(teleportationService.getGeographicDistanceInKilometers(origin, destination)>1);
	}
	
	/**
     * Currency is null, method should throw IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
	public void testCalculateTeleportationCostInCurrencyIsNull() {
		teleportationService.calculateTeleportationCostInCurrency(1L,2L, null);
	}
    
    /**
     * originId is null, method should throw IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
	public void testCalculateTeleportationOriginIsNull() {
		teleportationService.calculateTeleportationCostInCurrency(null,2L, CurrencyEnum.USD);
	}
    
    /**
     * destinationId is null, method should throw IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
	public void testCalculateTeleportationDestinationIsNull() {
		teleportationService.calculateTeleportationCostInCurrency(1L,null, CurrencyEnum.USD);
	}
    
    /**
     * Both params are valid, currency is USD
     */
    @Test
	public void testCalculateTeleportationCostInCurrencyUSDSuccess() {
    	when(mockedOrigin.getGeographicCoordinates()).thenReturn(A_LOCATION);
		when(mockedDestination.getGeographicCoordinates()).thenReturn(ANOTHER_LOCATION_FAR_AWAY);
		when(mockedOrigin.getId()).thenReturn(1L);
		when(mockedDestination.getId()).thenReturn(2L);
		when(mockedEntityManager.find(TeleportLocation.class, 1L)).thenReturn(mockedOrigin);
		when(mockedEntityManager.find(TeleportLocation.class, 2L)).thenReturn(mockedDestination);
		Assert.assertEquals(23461,teleportationService.calculateTeleportationCostInCurrency(1L,2L, CurrencyEnum.USD),0);
	}
    
    /**
     * Both params are valid, currency is CAD
     */
    @Test
	public void testCalculateTeleportationCostInCurrencyCADSuccess() {
    	when(mockedOrigin.getGeographicCoordinates()).thenReturn(A_LOCATION);
		when(mockedDestination.getGeographicCoordinates()).thenReturn(ANOTHER_LOCATION_FAR_AWAY);
		when(mockedOrigin.getId()).thenReturn(1L);
		when(mockedDestination.getId()).thenReturn(2L);
		when(mockedEntityManager.find(TeleportLocation.class, 1L)).thenReturn(mockedOrigin);
		when(mockedEntityManager.find(TeleportLocation.class, 2L)).thenReturn(mockedDestination);
		Assert.assertEquals(29795.47,teleportationService.calculateTeleportationCostInCurrency(1L,2L, CurrencyEnum.CAD),0);
	}
    
    /**
     * Distance is negative, method should throw IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
	public void testCalculateTeleportationCostInCreditDistanceIsNegative() {
		teleportationService.calculateTeleportationCostInCredits(-1);
	}
    
    /**
     * distance is valid, currency is USD
     */
    @Test
	public void testCalculateTeleportationCostInCreditSuccess() {
		Assert.assertTrue(teleportationService.calculateTeleportationCostInCredits(6)==12);
	}
    
    /**
     * query returns null
     */
	@Test
	public void testGetAllAvailableTeleportLocationsNull() {
    	when(mockedTypedQuery.getResultList()).thenReturn(null);
		Assert.assertNull(teleportationService.getAllAvailableTeleportLocations());
	}
    
    /**
     * query returns a Collection
     */
	@Test
	public void testGetAllAvailableTeleportLocationsIsNotNull() {
    	List<TeleportLocation> teleportLocations = new ArrayList<>();
    	when(mockedTypedQuery.getResultList()).thenReturn(teleportLocations);
		Assert.assertEquals(teleportLocations, teleportationService.getAllAvailableTeleportLocations());
	}
	
	/**
     * buying some credit 
     */
	@Test
	public void testPurchaseTeleportationCreditsSuccess() throws InsufficientCreditException {
		when(mockedUser.getTeleportationCredits()).thenReturn(10);
		Assert.assertEquals(5000,teleportationService.purchaseTeleportationCredits(mockedUser, CurrencyEnum.USD, 1000));
		verify(mockedEntityManagerFactory).createEntityManager();
		verify(mockedEntityManager).persist(mockedUser);
		verify(mockedUser).addCredits(5000);
	}
    
	/**
     * Trying to teleport with insufficient credits
     */
	@Test(expected=InsufficientCreditException.class)
	public void testTeleportNotEnoughCredit() throws InsufficientCreditException {
		doCallRealMethod().when(mockedUser).addCredits(anyInt());				
		mockedUser.setTeleportationCredits(10);
		when(mockedOrigin.getGeographicCoordinates()).thenReturn(A_LOCATION);
		when(mockedDestination.getGeographicCoordinates()).thenReturn(ANOTHER_LOCATION_FAR_AWAY);
		when(mockedOrigin.getId()).thenReturn(1L);
		when(mockedDestination.getId()).thenReturn(2L);
		when(mockedEntityManager.find(TeleportLocation.class, 1L)).thenReturn(mockedOrigin);
		when(mockedEntityManager.find(TeleportLocation.class, 2L)).thenReturn(mockedDestination);
		teleportationService.teleport(mockedUser, mockedOrigin.getId(), mockedDestination.getId(), mockedPayload);
		verify(mockedEntityManager).find(TeleportLocation.class, 1L);
		verify(mockedEntityManager).find(TeleportLocation.class, 2L);
		verify(mockedEntityManagerFactory,never()).createEntityManager();
		verify(mockedEntityManager,never()).persist(mockedUser);
		verify(mockedUser,never()).addCredits(5000);
	}
	
	/**
     * Trying to teleport with sufficent credits, should be successful
     */
	@Test
	public void testTeleportWithEnoughCredit() throws InsufficientCreditException {
		doCallRealMethod().when(mockedUser).addCredits(anyInt());				
		mockedUser.addCredits(100000);
		verify(mockedUser).addCredits(100000);
		when(mockedOrigin.getGeographicCoordinates()).thenReturn(A_LOCATION);
		when(mockedDestination.getGeographicCoordinates()).thenReturn(ANOTHER_LOCATION_CLOSE_BY);
		when(mockedOrigin.getId()).thenReturn(1L);
		when(mockedDestination.getId()).thenReturn(2L);
		when(mockedEntityManager.find(TeleportLocation.class, 1L)).thenReturn(mockedOrigin);
		when(mockedEntityManager.find(TeleportLocation.class, 2L)).thenReturn(mockedDestination);
		Assert.assertTrue(teleportationService.teleport(mockedUser, mockedOrigin.getId(), mockedDestination.getId(), mockedPayload));
		verify(mockedEntityManager).find(TeleportLocation.class, 1L);
		verify(mockedEntityManager).find(TeleportLocation.class, 2L);
		verify(mockedEntityManagerFactory).createEntityManager();
		verify(mockedEntityManager).persist(mockedUser);
		verify(mockedUser).addCredits(-23461);
	}
}

