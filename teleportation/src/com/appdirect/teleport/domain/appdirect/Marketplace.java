package com.appdirect.teleport.domain.appdirect;

/**
 * Represents an AppDirect Marketplace
 *
 */
public class Marketplace {

	private String baseUrl;
	
	private String partner;
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseUrl == null) ? 0 : baseUrl.hashCode());
		result = prime * result + ((partner == null) ? 0 : partner.hashCode());
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
		Marketplace other = (Marketplace) obj;
		if (baseUrl == null) {
			if (other.baseUrl != null)
				return false;
		} else if (!baseUrl.equals(other.baseUrl))
			return false;
		if (partner == null) {
			if (other.partner != null)
				return false;
		} else if (!partner.equals(other.partner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Marketplace [baseUrl=" + baseUrl + ", partner=" + partner + "]";
	}
	
}
