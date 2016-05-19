package com.appdirect.teleport.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.appdirect.teleport.domain.appdirect.Event;
import com.appdirect.teleport.domain.appdirect.RequestStatus;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Exposes the AppDirect integration API
 *
 */
public interface AppDirectIntegrationService {

	/**
	 * retrieves the event detail from AppDirect
	 * @param eventId
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws OAuthCommunicationException 
	 * @throws OAuthExpectationFailedException 
	 * @throws OAuthMessageSignerException 
	 */
	Event fetchEventDetails(String eventId) throws ClientProtocolException, IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException;

	/**
	 * Handle the Subscription Order event
	 * @param anEvent
	 * @return the Status of the operation
	 */
	RequestStatus handleSubscriptionOrder(Event anEvent);

	/**
	 * Handle the Subscription Change event
	 * @param anEvent
	 * @return the Status of the operation
	 */
	RequestStatus handleSubscriptionChange(Event anEvent);

	/**
	 * Handle the Subscription Cancel event
	 * @param anEvent
	 * @return the Status of the operation
	 */
	RequestStatus handleSubscriptionCancel(Event anEvent);

	/**
	 * Handle the Subscription Notice event
	 * @param anEvent
	 * @return the Status of the operation
	 */
	RequestStatus handleSubscriptionNotice(Event anEvent);
	
}
