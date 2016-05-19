package com.appdirect.teleport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.teleport.domain.appdirect.Event;
import com.appdirect.teleport.domain.appdirect.RequestStatus;
import com.appdirect.teleport.service.AppDirectIntegrationService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Exposes endpoints required by the AppDirect API
 */
@RestController
public class AppDirectIntegrationController {
	
	@Autowired
	private AppDirectIntegrationService appDirectIntegrationService;
	
	/**
	 * This method handles an AppDirect event and returns a status in JSON format 
	 * @param model
	 * @param token -> the unique identifier of the request
	 * @return the view 
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/appDirect/event")
	public @ResponseBody RequestStatus handleAppDirectEvent(Model model, @RequestParam("token") String token) throws JsonProcessingException {   
		
		RequestStatus requestStatus = new RequestStatus();
		try {
			
			Event anEvent = appDirectIntegrationService.fetchEventDetails(token);
			
			switch (anEvent.getType()) {
			case SUBSCRIPTION_ORDER:
				requestStatus = appDirectIntegrationService.handleSubscriptionOrder(anEvent);
				break;
			case SUBSCRIPTION_CHANGE:
				requestStatus = appDirectIntegrationService.handleSubscriptionChange(anEvent);
				break;
			case SUBSCRIPTION_CANCEL:
				requestStatus = appDirectIntegrationService.handleSubscriptionCancel(anEvent);
				break;
			case SUBSCRIPTION_NOTICE:
				requestStatus = appDirectIntegrationService.handleSubscriptionNotice(anEvent);
				break;
			default:
				//this should not happen 
				requestStatus.setStatus(false);
				requestStatus.setErrorCode("INVALID_REQUEST_TYPE");
				requestStatus.setMessage("This request type is not supported");
			}
		} catch (Exception e) {
			requestStatus.setStatus(false);
			requestStatus.setErrorCode("UNKNOWN_ERROR");
			requestStatus.setMessage(e.getMessage());
		}
		
		//model.addAttribute("requestStatus", requestStatus);
		return requestStatus;
    }
}
