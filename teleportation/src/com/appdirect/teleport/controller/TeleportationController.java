package com.appdirect.teleport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.appdirect.teleport.domain.CurrencyEnum;
import com.appdirect.teleport.entity.User;
import com.appdirect.teleport.error.InsufficientCreditException;
import com.appdirect.teleport.security.AuthenticationFacade;
import com.appdirect.teleport.service.TeleportationService;

/**
 * 
 * Main controller for the webapp
 * @author anonymous
 *
 */
@Controller
@Scope("session")
public class TeleportationController {

	@Autowired
	private TeleportationService teleportationService;
	
	@Autowired
    private AuthenticationFacade authenticationFacade;

	/**
	 * Estimates teleportation costs
	 * @return 
	 */
	@RequestMapping("/estimateCost")
    public String estimateCost(Model model, @RequestParam("originId") Long originId, @RequestParam("destinationId") Long destinationId, @RequestParam("currency") String currency) {   
		double costInCurrency = teleportationService.calculateTeleportationCostInCurrency(originId, destinationId, CurrencyEnum.valueOf(currency));
		model.addAttribute("cost",costInCurrency);
		model.addAttribute("currency",currency);
		return "costEstimation";			
    }
	
	/**
	 * Displaying all available teleporting locations
	 * @return
	 */
	@RequestMapping("/buyCredits")
    public String buyCredits(Model model, @RequestParam("currency") String currency, @RequestParam("amount") double amount) {   
		try {
			int credits = teleportationService.purchaseTeleportationCredits((User)authenticationFacade.getAuthentication().getPrincipal(), CurrencyEnum.valueOf(currency), amount);
			model.addAttribute("credits",credits);
			return "purchaseSuccessful";
		} catch (InsufficientCreditException e) {
			return "purchaseFailed";
		}					
    }
	
	/**
	 * Method that teleport from the origin to the destination 
	 * @param model
	 * @param originId
	 * @param destinationId
	 * @return
	 */
	@RequestMapping("/teleport")
    public String teleport(Model model, @RequestParam("originId") Long originId, @RequestParam("destinationId") Long destinationId) {   
		
		try {
			// TODO: Add Payload information, using null for now as it makes no difference
			teleportationService.teleport((User)authenticationFacade.getAuthentication().getPrincipal(), originId, destinationId, null);
		} catch (InsufficientCreditException e) {
			return "teleportFailed";
		}
		return "teleportSuccessful";		
    }
	
	/**
	 * Displaying the welcome page to the user
	 * @param model
	 * @return
	 */
	@RequestMapping("/welcome")
    public String showWelcomePage(Model model) {       		
		model.addAttribute("teleportLocations", teleportationService.getAllAvailableTeleportLocations());
		model.addAttribute("authentication",authenticationFacade.getAuthentication());
        return "welcome";
    }
}
