package com.appdirect.teleport.domain.appdirect;

/**
 * 
 * Represents a Notice 
 *
 */
public class Notice {
	
	private Type type;
		
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
		

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Notice other = (Notice) obj;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notice [type=" + type + "]";
	}

	public enum Type {
		DEACTIVATED
		,REACTIVATED
		,CLOSED
		,UPCOMING_INVOICE
	}
}
