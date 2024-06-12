package com.example.vitraya_assignment.service;

import com.example.vitraya_assignment.models.User;
import com.example.vitraya_assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User createUser(User user) {
    // Check if email already exists
    Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      throw new RuntimeException("Email already exists");
    }
    return userRepository.save(user);
  }

  @Override
  public User updateUser(User user) {
    Optional<User> existingUser = userRepository.findById(user.getId());
    if (existingUser.isPresent()) {
      return userRepository.save(user);
    } else {
      throw new RuntimeException("User not found with id " + user.getId());
    }
  }

  @Override
  public void deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new RuntimeException("User not found with id " + id);
    }
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> login(String email, String password) {
    return userRepository.findByEmailAndPassword(email, password);
  }
}
