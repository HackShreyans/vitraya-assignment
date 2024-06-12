package com.example.vitraya_assignment.controller;

import com.example.vitraya_assignment.models.User;
import com.example.vitraya_assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
    return userService.getUserById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
    try {
      User createdUser = userService.createUser(user);
      return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@RequestBody User user) {
    Optional<User> loggedInUser = userService.login(user.getEmail(), user.getPassword());
    return loggedInUser.map(u -> ResponseEntity.ok("Login successful"))
      .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password"));
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    user.setId(id); // Ensure the ID from the path is set in the user object
    try {
      User updatedUser = userService.updateUser(user);
      return ResponseEntity.ok("User updated successfully");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
    try {
      userService.deleteUser(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
