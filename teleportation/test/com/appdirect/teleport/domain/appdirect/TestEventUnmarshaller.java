package com.appdirect.teleport.domain.appdirect;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Test cases to ensure that we can Unmarshall sample JSON
 *
 */
public class TestEventUnmarshaller {
	
	ObjectMapper jsonObjectMapper;
	
	@Before
	public void setup() {
		jsonObjectMapper = new ObjectMapper();
	}
	
	@Test
	public void testUnmarshallSubscriptionOrder() throws JsonParseException, JsonMappingException, IOException {
		InputStream in = TestEventUnmarshaller.class.getClassLoader().getResourceAsStream("com/appdirect/teleport/domain/appdirect/subscription_order.json");
		Event event = jsonObjectMapper.readValue(in, Event.class);
		Assert.assertEquals(Type.SUBSCRIPTION_ORDER, event.getType());
		Assert.assertEquals("bd58b532-323b-4627-a828-57729489b27b", event.getPayload().getCompany().getUuid());
		Assert.assertEquals("https://www.acme.com", event.getMarketplace().getBaseUrl());
		Assert.assertEquals("MONTHLY", event.getPayload().getOrder().getPricingDuration());
		//TODO: add more assertions
	}
	
	@Test
	public void testUnmarshallSubscriptionChange() throws JsonParseException, JsonMappingException, IOException {
		InputStream in = TestEventUnmarshaller.class.getClassLoader().getResourceAsStream("com/appdirect/teleport/domain/appdirect/subscription_change.json");
		Event event = jsonObjectMapper.readValue(in, Event.class);
		Assert.assertEquals(Type.SUBSCRIPTION_CHANGE, event.getType());
		Assert.assertEquals("GIGABYTE", event.getPayload().getOrder().getItem().getUnit());
		Assert.assertEquals("https://www.acme.com", event.getMarketplace().getBaseUrl());
		Assert.assertEquals("Test User", event.getCreator().getAddress().getFullName());		
		Assert.assertEquals("DAILY", event.getPayload().getOrder().getPricingDuration());
		//TODO: add more assertions
	}
	
	@Test
	public void testUnmarshallSubscriptionCancel() throws JsonParseException, JsonMappingException, IOException {
		InputStream in = TestEventUnmarshaller.class.getClassLoader().getResourceAsStream("com/appdirect/teleport/domain/appdirect/subscription_cancel.json");
		Event event = jsonObjectMapper.readValue(in, Event.class);
		Assert.assertEquals(Type.SUBSCRIPTION_CANCEL, event.getType());
		Assert.assertEquals("9d6fca98-aa94-462b-85fa-118804ad3fe3", event.getPayload().getAccount().getAccountIdentifier());
		Assert.assertEquals("https://www.acme.com", event.getMarketplace().getBaseUrl());
		Assert.assertEquals("Test User", event.getCreator().getAddress().getFullName());		
		//TODO: add more assertions
	}
	
	@Test
	public void testUnmarshallSubscriptionNotice() throws JsonParseException, JsonMappingException, IOException {
		InputStream in = TestEventUnmarshaller.class.getClassLoader().getResourceAsStream("com/appdirect/teleport/domain/appdirect/subscription_notice.json");
		Event event = jsonObjectMapper.readValue(in, Event.class);
		Assert.assertEquals(Type.SUBSCRIPTION_NOTICE, event.getType());
		Assert.assertEquals("a3f72246-5377-4d92-8bdc-b1b6b450c55c", event.getPayload().getAccount().getAccountIdentifier());
		Assert.assertEquals("https://www.acme.com", event.getMarketplace().getBaseUrl());	
		Assert.assertEquals(Notice.Type.UPCOMING_INVOICE, event.getPayload().getNotice().getType());	
		//TODO: add more assertions
	}
	
}
