package com.appdirect.teleport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appdirect.teleport.service.TeleportationService;

/**
 * 
 * Main controller for the webapp
 * @author anonymous
 *
 */
@Controller
public class TeleportationController {

	@Autowired
	private TeleportationService teleportationService;

	/**
	 * Displaying all available teleporting locations
	 * @return
	 */
	@RequestMapping("/showAvailableDestinations")
    public String showAvailableLocations(Model model) {       		
		model.addAttribute("teleportLocations", teleportationService.getAllAvailableTeleportLocations());
        return "teleportLocations";
    }
}
