package com.tweetapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.tweetapp.repo.TweetRepo")
public class AppConfig {}