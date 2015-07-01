package com.conquer.test.service;

import java.util.List;

import com.conquer.test.jpa.User;

public interface HomeService {
public List<User> getAllUsers();
public void addUser(User user);
}
