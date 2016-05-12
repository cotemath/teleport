package com.appdirect.teleport.entity;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.appdirect.teleport.error.InsufficientCreditException;

/**
 * Represents a User of the application
 *
 */
@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = -4057473596714403693L;

	@Id
	private String username;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private String password;
	
	private int teleportationCredits;	
	
	User() {
		//default constructor for JPA
	}
	
	public User(String username, String firstname, String lastname, String email, String password,
			int teleportationCredits) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.teleportationCredits = teleportationCredits;
	}


	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}
	
	/**
	 * Adds credits to this user's account
	 * @param creditsToAdd, a negative value will deduct from the current amount
	 * @throws InsufficientCreditException if trying to remove more credit than the current balance
	 */
	public void addCredits(int creditsToAdd) throws InsufficientCreditException {
 
		if (this.teleportationCredits + creditsToAdd < 0) {
			throw new InsufficientCreditException(MessageFormat.format("Insufficient credits, current credits: {0}, amount to add/remove: {1}",this.teleportationCredits,creditsToAdd));
		}
		this.teleportationCredits += creditsToAdd;
	}

	public int getTeleportationCredits() {
		return teleportationCredits;
	}

	public void setTeleportationCredits(int teleportationCredits) {
		this.teleportationCredits = teleportationCredits;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return auths;
	}

	@Override
	public String getPassword() {	
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + teleportationCredits;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (teleportationCredits != other.teleportationCredits)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", teleportationCredits=" + teleportationCredits + "]";
	}
	
}
