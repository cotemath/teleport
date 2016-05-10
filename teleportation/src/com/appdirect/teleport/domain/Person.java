package com.appdirect.teleport.domain;

import org.springframework.util.Assert;

/**
 * Represents a Person
 * When teleporting a Person, there is always a risk of death
 *
 */
public class Person extends TeleportablePayload {

	private float riskOfDeath; //a value between 0 and 1 that represents the risk of dying during teleportation
	
	public Person(float weightInKilos, float riskOfDeath) {
		super(weightInKilos);
		Assert.isTrue(riskOfDeath>=0 && riskOfDeath<=1);
		this.riskOfDeath = riskOfDeath;
	}

	// TODO: Implement this at some point
	public float getRiskOfDeath() {
		return riskOfDeath;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(riskOfDeath);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (Float.floatToIntBits(riskOfDeath) != Float.floatToIntBits(other.riskOfDeath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [riskOfDeath=" + riskOfDeath + "]";
	}
	
}
