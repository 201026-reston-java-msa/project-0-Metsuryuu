package com.revature.model;

public class Status {
	
	private int statusId;
	private String statusName;
	
	public Status() {}

	public Status(int statusId) {
		super();
		String statusName = "";
		
		this.statusId = statusId;
		
		if(statusId == 1) {
			statusName = "Pending";
		}else if(statusId == 2) {
			statusName = "Open";
		}else if(statusId == 3) {
			statusName = "Closed";
		}
		
		this.statusName = statusName;
	}

	public String getStatus() {
		return statusName;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
		
		String statusName = "";	//statusName is based off the status, so there's no need for it's own setter.
		
		if(statusId == 1) {
			statusName = "Pending";
		}else if(statusId == 2) {
			statusName = "Open";
		}else if(statusId == 3) {
			statusName = "Closed";
		}
		
		this.statusName = statusName;
	}

}
