package com.shortenUrl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	
	String timestamp;
	int status;
	String message;
	String path;
	
	public ErrorResponse(String timestamp,int status,String message,String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.path = path;
	}

}
