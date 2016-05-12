package com.appdirect.teleport.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.appdirect.teleport.entity.User;

/**
 * Test class for UserLoginServiceImpl
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserLoginServiceImpl {

	private static final String A_USERNAME = "A username";
	
	@Mock
	private EntityManagerFactory mockedEntityManagerFactory;

	@Mock
	private EntityManager mockedEntityManager;
	
	private UserLoginServiceImpl userLoginServiceImpl;

	@Before
    public void setup() {      
    	when(mockedEntityManagerFactory.createEntityManager()).thenReturn(mockedEntityManager);
    	userLoginServiceImpl = new UserLoginServiceImpl();
    	userLoginServiceImpl.setEntityManagerFactory(mockedEntityManagerFactory);        
    }
	
	@Test
	public void testLoadUserByUsername() {
		userLoginServiceImpl.loadUserByUsername(A_USERNAME);
		verify(mockedEntityManager).find(User.class, A_USERNAME);
	}
}
