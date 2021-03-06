package com.cliff.jms.services;

import com.cliff.jms.domain.User;

import java.util.List;

/**
 * Get a random User
 * User: Cliff
 */
public interface UserService {

    User getRandomUser();

    List<User> getAllUsers();
}
