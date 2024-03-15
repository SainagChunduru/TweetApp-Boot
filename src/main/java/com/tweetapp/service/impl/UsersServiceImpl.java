package com.tweetapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tweetapp.dto.UsersDto;
import com.tweetapp.entities.UsersEntity;
import com.tweetapp.repo.UsersRepo;
import com.tweetapp.request.UserRequest;
import com.tweetapp.response.UserResponse;
import com.tweetapp.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepo usersRepo;
	
	private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);


	@Override
	public UserResponse getAllUsers() {
		// TODO Auto-generated method stub
		UserResponse response = new UserResponse();
		try {
			Iterable<UsersEntity> users = usersRepo.findAll();
			List<UsersDto> usersDto = new ArrayList<>();
			users.forEach(entity -> {
				UsersDto dto = new UsersDto();
				dto.setContactNumber(entity.getContactNumber());
				dto.setEmailId(entity.getEmailId());
				dto.setFirstName(entity.getFirstName());
				dto.setLastName(entity.getLastName());
				// dto.setLoggedIn(entity.getLoggedIn());
				dto.setLoginId(entity.getLoginId());
				dto.setPassword(entity.getPassword());
				usersDto.add(dto);
			});
			response.setUsersDto(usersDto);
			response.setStatusMessage("SUCCESS");
		} catch (Exception e) {
			// TODO: handle exception
			response.setStatusMessage("FAILURE");
		}

		return response;
	}

	@Override
	public UserResponse register(UserRequest request) {
		// TODO Auto-generated method stub
		UserResponse response = new UserResponse();
		UsersEntity entity = new UsersEntity();
		UsersDto userDto = request.getUserDto();
		logger.info("Users Controller" + "in register() call" + userDto.getFirstName() + userDto.getLoginId());
		try {
			Optional<UsersEntity> user = usersRepo.findById(userDto.getLoginId());
			logger.info("Users Entity" + "in register() call" + user.toString());
			List<String> roles = new ArrayList<>();
			roles.add("user");
			if (!user.isPresent()) {
				entity.setContactNumber(userDto.getContactNumber());
				entity.setEmailId(userDto.getEmailId());
				entity.setFirstName(userDto.getFirstName());
				entity.setLastName(userDto.getLastName());
				// entity.setLoggedIn(false);
				entity.setLoginId(userDto.getLoginId());
				entity.setPassword(passwordEncoder().encode(userDto.getPassword()));
				entity.setRoles(roles);
				usersRepo.save(entity);
				response.setStatusMessage("SUCCESS");
			} else {
				response.setStatusMessage("User Already Exists");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatusMessage("FAILURE");
		}

		return response;
	}

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserResponse forgetPassword(UserRequest request) {
		// TODO Auto-generated method stub
		UserResponse response = new UserResponse();
		UsersDto userDto = request.getUserDto();
		try {
			UsersEntity user = usersRepo.findByLoginId(userDto.getLoginId());
			if (user != null) {
				user.setPassword(passwordEncoder().encode(userDto.getPassword()));
				usersRepo.save(user);
				response.setStatusMessage("SUCCESS");
			} else {
				response.setStatusMessage("USER NOT FOUND");
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.setStatusMessage("FAILURE");
		}
		return response;
	}

	@Override
	public UserResponse searchUserById(String username) {
		// TODO Auto-generated method stub
		UserResponse response = new UserResponse();
		List<UsersDto> users = new ArrayList<UsersDto>();
		try {
			Optional<UsersEntity> entity = usersRepo.findById(username);
			if(entity.isPresent()) {
				UsersDto dto = new UsersDto();
				dto.setLoginId(entity.get().getLoginId());
				dto.setFirstName(entity.get().getFirstName());
				dto.setLastName(entity.get().getLastName());
				users.add(dto);
			}
			response.setStatusMessage("SUCCESS");
			response.setUsersDto(users);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatusMessage("FAILURE");
		}
		return response;
	}

}
