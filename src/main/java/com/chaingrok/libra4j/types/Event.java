package com.chaingrok.libra4j.types;

public class Event {

	private AccountAddress address;
	private Long sequenceNumber;
	private EventKey eventKey;
	private AccessPath accessPath;
	private EventData data;
	
	public AccountAddress getAddress() {
		return address;
	}

	public void setAddress(AccountAddress address) {
		this.address = address;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public EventKey getEventKey() {
		return eventKey;
	}

	public void setEventKey(EventKey eventKey) {
		this.eventKey = eventKey;
	}

	public AccessPath getAccessPath() {
		return accessPath;
	}

	public void setAccessPath(AccessPath accessPath) {
		this.accessPath = accessPath;
	}

	public EventData getData() {
		return data;
	}

	public void setData(EventData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		String result = "";
		result += "event: sequence number: " + getSequenceNumber() 
			+ " - address: " + getAddress();
		if (getAccessPath() != null) {
			result +=  " - " + getAccessPath().toString();
		}
		if (getData() != null) {
			result +=  " - data (" + getData().getBytes().length 
							+ "): " +  getData().toString();
		}
		return result;
	}

}
