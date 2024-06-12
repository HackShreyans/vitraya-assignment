package com.example.vitraya_assignment.service;

import com.example.vitraya_assignment.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  User createUser(User user);
  User updateUser(User user);
  void deleteUser(Long id);
  Optional<User> getUserById(Long id);
  Optional<User> getUserByEmail(String email);
  Optional<User> login(String email, String password); // Add login method
  List<User> getAllUsers();
}
