package com.appdirect.teleport.domain.appdirect;

/**
 * Represents an AppDirect event
 *
 */
public class Event {

	private Type type;
	
	private Marketplace marketplace;
	
	private Creator creator;
	
	private Payload payload;

	public Type getType() {
		return type;
	}

	public void setType(Type eventType) {
		this.type = eventType;
	}

	public Marketplace getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(Marketplace marketplace) {
		this.marketplace = marketplace;
	}

	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((marketplace == null) ? 0 : marketplace.hashCode());
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
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
		Event other = (Event) obj;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (type != other.type)
			return false;
		if (marketplace == null) {
			if (other.marketplace != null)
				return false;
		} else if (!marketplace.equals(other.marketplace))
			return false;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [type=" + type + ", marketplace=" + marketplace + ", creator=" + creator + ", payload="
				+ payload + "]";
	}
	
}
