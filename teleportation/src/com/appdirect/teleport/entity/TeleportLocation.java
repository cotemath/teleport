package com.appdirect.teleport.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.appdirect.teleport.domain.Location;

/**
 * Entity that defines a Teleportable location
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="TeleportLocation.getAllTeleportLocations",
			query="SELECT t FROM TeleportLocation t")          
})
public class TeleportLocation {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String city;
	
	private String country;
	
	@Embedded
	private Location geographicCoordinates; //represents the location on the map

	TeleportLocation() {
		//default constructor for JPA
	}
	
	public TeleportLocation(Long id, String city, String country, Location geographicCoordinates) {
		super();
		this.id = id;
		this.city = city;
		this.country = country;
		this.geographicCoordinates = geographicCoordinates;
	}

	public Long getId() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public Location getGeographicCoordinates() {
		return geographicCoordinates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((geographicCoordinates == null) ? 0 : geographicCoordinates.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TeleportLocation other = (TeleportLocation) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (geographicCoordinates == null) {
			if (other.geographicCoordinates != null)
				return false;
		} else if (!geographicCoordinates.equals(other.geographicCoordinates))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TeleportLocation [id=" + id + ", city=" + city + ", country=" + country + ", geographicCoordinates="
				+ geographicCoordinates + "]";
	}	
	
}
