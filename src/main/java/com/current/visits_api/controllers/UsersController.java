package com.current.visits_api.controllers;

import com.current.visits_api.models.User;
import com.current.visits_api.models.UserPayload;
import com.current.visits_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="api/v1")
public class UsersController {
  @Autowired
  private final UserService userService;

  @Autowired
  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(path = "/user")
  @ResponseBody
  public ResponseEntity<?> createUser(@RequestBody UserPayload payload) {
    try {
      User newUser = new User(payload.getName());
      newUser = userService.saveUser(newUser);
      return new ResponseEntity<>("User created with ID: " + newUser.getId(), HttpStatus.CREATED);
    } catch(Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
