package com.current.visits_api.controllers;

import com.current.visits_api.models.User;
import com.current.visits_api.models.UserPayload;
import com.current.visits_api.services.UserService;
import com.current.visits_api.utils.ValidationUtil;
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
  private final UserService userService;
  private final ValidationUtil validationUtil;

  @Autowired
  public UsersController(UserService userService, ValidationUtil validationUtil) {
    this.userService = userService;
    this.validationUtil = validationUtil;
  }

  @PostMapping(path = "/user")
  @ResponseBody
  public ResponseEntity<?> createUser(@RequestBody UserPayload payload) {
    try {
      if (!validationUtil.isAlphaNumeric(payload.getName()) || payload.getName().trim().length() == 0)
        return new ResponseEntity<>("User name needs to be non-empty and alphanumeric", HttpStatus.BAD_REQUEST);

      User newUser = new User(payload.getName());
      newUser = userService.saveUser(newUser);
      return new ResponseEntity<>("User created with ID: " + newUser.getId(), HttpStatus.CREATED);
    } catch(Exception e) {
      return new ResponseEntity<>("Cannot process request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
