package com.tweetapp.dynamo.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "users")
public class User {

	@DynamoDBHashKey(attributeName = "loginId")
	@DynamoDBAutoGeneratedKey
	private String loginId;

	@DynamoDBAttribute(attributeName = "firstName")
	private String firstName;
	@DynamoDBAttribute(attributeName = "secondName")
	private String secondName;

	@DynamoDBAttribute(attributeName = "emailId")
	private String emailId;

	@DynamoDBAttribute(attributeName = "contactNumber")
	private String contactNumber;

	@DynamoDBAttribute(attributeName = "password")
	private String password;

	@DynamoDBAttribute(attributeName = "loggedIn")
	private Boolean loggedIn;
}
