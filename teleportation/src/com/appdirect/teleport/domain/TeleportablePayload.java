package com.appdirect.teleport.domain;

/**
 * Represents a teleportable payload
 *
 */
public abstract class TeleportablePayload {
	
	private float weightInKilos;

	public TeleportablePayload(float weightInKilos) {
		super();
		this.weightInKilos = weightInKilos;
	}

	public float getWeightInKilos() {
		return weightInKilos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(weightInKilos);
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
		TeleportablePayload other = (TeleportablePayload) obj;
		if (Float.floatToIntBits(weightInKilos) != Float.floatToIntBits(other.weightInKilos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payload [weightInKilos=" + weightInKilos + "]";
	}
	
	
}
