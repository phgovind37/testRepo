package com.conquer.test.dao;

import java.util.List;

import com.conquer.test.jpa.User;

public interface HomeDao {
	public List<User> getAllUsers();
	public void addUser(User user);
}
