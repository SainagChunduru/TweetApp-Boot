package com.tweetapp.dto;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBDocument
public class Reply {

	@DynamoDBAttribute
	private String userId;

	@DynamoDBAttribute
	private String replied;

	@DynamoDBAttribute
	private String dateReplied;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReplied() {
		return replied;
	}

	public void setReplied(String replied) {
		this.replied = replied;
	}

	public String getDateReplied() {
		return dateReplied;
	}

	public void setDateReplied(String dateReplied) {
		this.dateReplied = dateReplied;
	}
	
	public Reply(String replied, String dateReplied, String userId) {
		this.dateReplied = dateReplied;
		this.replied = replied;
		this.userId = userId;
	}
	
}
