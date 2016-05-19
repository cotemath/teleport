package com.appdirect.teleport.domain.appdirect;

/**
 * 
 * Represents an AppDirect account
 *
 */
public class Account {

	private String accountIdentifier;
	
	private String status; //TODO: this should be an enum, what are the possible values?

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String accountStatus) {
		this.status = accountStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountIdentifier == null) ? 0 : accountIdentifier.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Account other = (Account) obj;
		if (accountIdentifier == null) {
			if (other.accountIdentifier != null)
				return false;
		} else if (!accountIdentifier.equals(other.accountIdentifier))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accountIdentifier=" + accountIdentifier + ", status=" + status + "]";
	}
		
}
