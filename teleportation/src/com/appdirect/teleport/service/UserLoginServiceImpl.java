package com.appdirect.teleport.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.appdirect.teleport.entity.User;

/**
 * Provides services to authenticate users
 *
 */
public class UserLoginServiceImpl implements UserDetailsService  {

	@PersistenceUnit(unitName="h2") 
	private EntityManagerFactory entityManagerFactory;
	
	/**
	 * Retrieve the user from our User table
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return entityManagerFactory.createEntityManager().find(User.class, userName);
	}

	/*
	 * Setter for testing purposes
	 */
	void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}	
	
}
