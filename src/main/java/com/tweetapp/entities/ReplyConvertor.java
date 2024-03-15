package com.tweetapp.entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.dto.Reply;
import com.tweetapp.service.impl.TweetsServiceImpl;

public class ReplyConvertor implements DynamoDBTypeConverter<List<String>, List<Reply>> {
	private final Logger logger = LoggerFactory.getLogger(TweetsServiceImpl.class);

	@Override
	public List<String> convert(List<Reply> object) {
		List<String> result = new ArrayList<String>();
		if (object != null) {
			object.stream().forEach(e -> result.add(String.format(new Gson().toJson(e))));
		}
		return result;
	}

	@Override
	public List<Reply> unconvert(List<String> object) {
		logger.info("Hiii");
		logger.info(object.toString());
		object.forEach(e -> logger.info(e));
		List<Reply> result = new ArrayList<Reply>();
		if (object != null) {
			
			object.stream().forEach(e -> {
				result.add(new Gson().fromJson(e, Reply.class));
			});
		}
		return result;
}

}
