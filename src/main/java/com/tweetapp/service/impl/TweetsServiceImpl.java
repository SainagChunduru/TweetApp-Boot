package com.tweetapp.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.dto.Reply;
import com.tweetapp.dto.TweetsDto;
import com.tweetapp.entities.TweetsEntity;
import com.tweetapp.repo.TweetsRepo;
import com.tweetapp.request.TweetRequest;
import com.tweetapp.response.TweetResponse;
import com.tweetapp.service.TweetsService;

@Service
public class TweetsServiceImpl implements TweetsService {
	
	
	private final Logger logger = LoggerFactory.getLogger(TweetsServiceImpl.class);

	@Autowired
	TweetsRepo tweetsRepo;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
	SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat dateTimeFormat =new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");  
	@Override
	public TweetResponse getAllTweets() {
		// TODO Auto-generated method stub
		TweetResponse response = new TweetResponse();
		try {
			Iterable<TweetsEntity> tweets = tweetsRepo.findAll();
			List<TweetsDto> tweetsDto = new ArrayList<>();
			tweets.forEach(entity -> {
				TweetsDto tweet = new TweetsDto();
				tweet.setTweet(entity.getTweet());
				tweet.setTweetId(entity.getTweetId());
				tweet.setUserTweetId(entity.getUserTweetId());
				tweet.setLike(entity.getLike());
				tweet.setReply(entity.getReply());
				tweet.setDateOfPost(entity.getDateOfPost());
				tweet.setTimeOfPost(entity.getDateOfPost());
				tweetsDto.add(tweet);
			});
			response.setTweetsDto(tweetsDto);
			response.setStatusMessage("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Get all tweets" + e.getMessage());
			response.setStatusMessage("FAILURE");
		}

		return response;
	}

	@Override
	public TweetResponse getAllTweetsByUserName(String userName) {
		// TODO Auto-generated method stub
		TweetResponse response = new TweetResponse();
		logger.info(userName);
		List<TweetsDto> tweetsDto = new ArrayList<>();
		try {
			List<TweetsEntity> tweets = tweetsRepo.findByUserTweetId(userName);
			logger.info(tweets.toString());
			tweets.forEach(entities -> {
				TweetsDto dto = new TweetsDto();
				dto.setTweet(entities.getTweet());
				dto.setLike(entities.getLike());
				dto.setReply(entities.getReply());
				dto.setTweetId(entities.getTweetId());
				dto.setUserTweetId(entities.getUserTweetId());
				try {
					dto.setDateOfPost(simpleDateFormat.format(dateTimeFormat.parse(entities.getDateOfPost())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					dto.setTimeOfPost(localDateFormat.format(dateTimeFormat.parse(entities.getDateOfPost())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tweetsDto.add(dto);
			});
			response.setStatusMessage("SUCCESS");
			response.setTweetsDto(tweetsDto);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			e.printStackTrace();
			response.setStatusMessage("FAILURE");
		}

		return response;
	}

	@Override
	public TweetResponse addTweet(TweetRequest request, String userName) {
		// TODO Auto-generated method stub
		TweetResponse response = new TweetResponse();
		TweetsDto tweet = request.getTweet();
		TweetsEntity entity = new TweetsEntity();
		List<Reply> reply = new ArrayList<>();
		try {
//			Optional<TweetsEntity> maxEntity = tweetsRepo.findTopByOrderByIdDesc();
//			if (maxEntity == null) {
//				entity.setTweetId(1l);
//			} else {
//				entity.setTweetId(maxEntity.get().getTweetId() + 1);
//			}
			entity.setTweet(tweet.getTweet());
			entity.setUserTweetId(userName);
			entity.setLike(0l);
			entity.setReply(reply);
			entity.setDateOfPost(dateTimeFormat.format(new Date()));
			tweetsRepo.save(entity);
			response.setStatusMessage("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatusMessage("FAILURE");
		}
		return response;
	}

	@Override
	public TweetResponse deleteTweet(String userName, String tweetId) {
		// TODO Auto-generated method stub
		TweetResponse response = new TweetResponse();
		try {
			tweetsRepo.deleteById(tweetId);
			response.setStatusMessage("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatusMessage("FAILURE");
		}
		return response;
	}

	@Override
	public TweetResponse replyToTweet(TweetRequest request) {
		// TODO Auto-generated method stub
		TweetResponse response = new TweetResponse();
		TweetsDto dto = request.getTweet();
		List<Reply> replies = new ArrayList<>();
		try {
			Optional<TweetsEntity> entity = tweetsRepo.findById(dto.getTweetId());
			if (entity.isPresent()) {
				replies.addAll(entity.get().getReply());
			} else {
				throw new NullPointerException();
			}
			List<Reply> newReplies = new ArrayList<>();
			if (dto.getReply() != null) {
				newReplies = dto.getReply();
			} else {
				throw new NullPointerException();
			}
			newReplies.forEach(reply -> {
			});
			replies.addAll(newReplies);
			entity.get().setReply(replies);
			tweetsRepo.save(entity.get());
			response.setStatusMessage("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatusMessage("FAILED");
		}
		return response;
	}

	@Override
	public TweetResponse likeATweet(TweetRequest request) {
		// TODO Auto-generated method stub
		TweetResponse response = new TweetResponse();
		TweetsDto dto = request.getTweet();
		try {
			Optional<TweetsEntity> entity = tweetsRepo.findById(dto.getTweetId());
			if (entity.isPresent() && entity.get().getLike() != null) {
				entity.get().setLike(entity.get().getLike() + 1);
			}
			tweetsRepo.save(entity.get());
			response.setStatusMessage("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatusMessage("FAILURE");
		}
		return response;
	}

	@Override
	public TweetResponse updateTweet(TweetRequest request) {
		// TODO Auto-generated method stub
		TweetResponse response = new TweetResponse();
		TweetsDto dto = request.getTweet();
		try {
			Optional<TweetsEntity> entity = tweetsRepo.findById(dto.getTweetId());
			entity.get().setTweet(dto.getTweet());
			tweetsRepo.save(entity.get());
			response.setStatusMessage("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatusMessage("FAILURE");
		}
		return response;
	}

}
