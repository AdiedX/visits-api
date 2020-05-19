package com.current.visits_api.controllers;

import com.current.visits_api.models.User;
import com.current.visits_api.models.Visit;
import com.current.visits_api.models.VisitPayload;
import com.current.visits_api.models.VisitResponse;
import com.current.visits_api.services.UserService;
import com.current.visits_api.services.VisitService;
import com.current.visits_api.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="api/v1")
public class VisitsController {
  private final VisitService visitService;
  private final UserService userService;
  private final ValidationUtil validationUtil;

  @Autowired
  public VisitsController(VisitService visitService, UserService userService, ValidationUtil validationUtil) {
    this.visitService = visitService;
    this.userService = userService;
    this.validationUtil = validationUtil;
  }

  @PostMapping(path = "/visit")
  @ResponseBody
  public ResponseEntity<?> createVisit(@RequestBody VisitPayload payload) {
    if (!validationUtil.isAlphaNumeric(payload.getLocation()) || payload.getLocation().trim().length() == 0)
      return new ResponseEntity<>("Location needs to be non-empty and alphanumeric", HttpStatus.BAD_REQUEST);

    try {
      Optional<User> user = userService.getUser(payload.getUserId());
      if (user.isPresent()) {
        Visit visit = visitService.saveVisit(new Visit(user.get(), payload.getLocation()));
        userService.updateUser(payload.getUserId(), visit);
        return new ResponseEntity<>("Visit created with ID: " + visit.getId(), HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>("Could not find user with ID: " + payload.getUserId(), HttpStatus.NOT_FOUND);
      }
    } catch(Exception e) {
      return new ResponseEntity<>("Could not process request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/visit/{visitId}")
  @ResponseBody
  public ResponseEntity<?> getVisitById(@PathVariable Integer visitId) {
    try {
      Optional<VisitResponse> visitResponse = visitService.getVisit(visitId).map(VisitResponse::new);

      if (visitResponse.isPresent()) {
        List<VisitResponse> body = new ArrayList<>(Collections.singletonList(visitResponse.get()));
        return new ResponseEntity<>(body, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Could not find visit with provided ID:" + visitId, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>("Could not process request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/visit")
  public ResponseEntity<?> getVisitByUserAndSearch(@RequestParam(required = true) Integer userId, @RequestParam(required = true) String searchString) {
    // Fuzzy search on the visits data associated with the user object
    try {
      List<VisitResponse> result = visitService
          .searchMatchingEntries(userId, searchString)
          .stream().map(VisitResponse::new).collect(Collectors.toList());

      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Error executing search", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
