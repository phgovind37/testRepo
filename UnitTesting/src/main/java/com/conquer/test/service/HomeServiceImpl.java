package com.conquer.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conquer.test.dao.HomeDao;
import com.conquer.test.jpa.User;
@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private HomeDao homeDao;
	
	@Override
	public List<User> getAllUsers() {
		return homeDao.getAllUsers();
	}

	@Override
	public void addUser(User user) {
		homeDao.addUser(user);
	}

}
