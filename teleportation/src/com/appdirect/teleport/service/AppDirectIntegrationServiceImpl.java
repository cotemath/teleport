package com.appdirect.teleport.service;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.appdirect.teleport.domain.appdirect.Event;
import com.appdirect.teleport.domain.appdirect.RequestStatus;
import com.appdirect.teleport.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * 
 * Implementation of the AppDirectIntegrationService API
 * 
 * TODO: add test class for this
 *
 */
public class AppDirectIntegrationServiceImpl implements AppDirectIntegrationService {
	
	private static final String APP_DIRECT_EVENT_URL = "https://www.appdirect.com/api/events/"; //TODO: change this
	
	private static final String CONSUMER_KEY = "Dummy"; //TODO: change this

	private static final String CONSUMER_SECRET= "secret"; //TODO: change this
	
	private ObjectMapper jsonObjectMapper;
	
	private OAuthConsumer oAuthConsumer;
	
	@PersistenceUnit(unitName="h2") 
	private EntityManagerFactory entityManagerFactory;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event fetchEventDetails(String eventId) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		
		//perform OAuth 
		oAuthConsumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		URL url = new URL(APP_DIRECT_EVENT_URL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		oAuthConsumer.sign(request);
		request.connect();
		
		//Fetch and Unmarshall the event
		return jsonObjectMapper.readValue(request.getInputStream(), Event.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public RequestStatus handleSubscriptionOrder(Event anEvent) {
		RequestStatus requestStatus = new RequestStatus();
		String firstname = anEvent.getCreator().getFirstName();
		String lastname = anEvent.getCreator().getLastName();
		String email = anEvent.getCreator().getEmail();
		String openId = anEvent.getCreator().getOpenId();
		String uuid = anEvent.getCreator().getUuid();
		
		int amount = 0;
		if (anEvent.getPayload().getOrder()!=null && anEvent.getPayload().getOrder().getItem()!=null) {
			amount = (int) anEvent.getPayload().getOrder().getItem().getQuantity();
		}
		
		// use the email as username for now, switch to openId when it works
		User newUser = new User(email, firstname, lastname, email, "", uuid, openId, amount);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		User checkExistingUser = entityManager.find(User.class, email);
		if (checkExistingUser!=null) {
			requestStatus.setStatus(false);
			requestStatus.setErrorCode("USER_ALREADY_EXISTS");
			requestStatus.setMessage("User already exists");
		} else {
			entityManager.persist(newUser);
			requestStatus.setStatus(true);
			requestStatus.setAccountIdentifier(email); //todo, use something else than email here
			requestStatus.setMessage("User successfully added");
		}
		
		return requestStatus;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestStatus handleSubscriptionChange(Event anEvent) {
		// TODO Implement this
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestStatus handleSubscriptionCancel(Event anEvent) {
		// TODO Implement this
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RequestStatus handleSubscriptionNotice(Event anEvent) {
		// TODO Implement this
		throw new UnsupportedOperationException();
	}	
}
