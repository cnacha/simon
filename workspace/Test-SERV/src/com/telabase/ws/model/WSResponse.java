package com.telabase.ws.model;

public class WSResponse {
	
	public static int STATUS_SUCCESS = 1;
	public static int STATUS_FAIL = -1;

	private String key;
	private int status;
	private String message;

	
	public WSResponse(String key, int status) {
		super();
		this.key = key;
		this.status = status;
	}
	public WSResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		if(this.status == STATUS_SUCCESS)
			return "Success:" + this.message;
		else if(this.status == STATUS_FAIL)
			return "Fail:" + this.message;
		else
			return "Unknown:" + this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
