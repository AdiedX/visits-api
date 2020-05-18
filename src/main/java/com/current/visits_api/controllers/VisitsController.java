package com.current.visits_api.controllers;

import com.current.visits_api.models.User;
import com.current.visits_api.models.Visit;
import com.current.visits_api.models.VisitPayload;
import com.current.visits_api.models.VisitResponse;
import com.current.visits_api.services.UserService;
import com.current.visits_api.services.VisitService;
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
  @Autowired
  private final VisitService visitService;

  @Autowired
  private final UserService userService;

  @Autowired
  public VisitsController(VisitService visitService, UserService userService) {
    this.visitService = visitService;
    this.userService = userService;
  }

  @PostMapping(path = "/visit")
  @ResponseBody
  public ResponseEntity<?> createVisit(@RequestBody VisitPayload payload) {
    // Validate userName and location

    try {
      // Find user
      User user = userService.getUser(payload.getUserId());

      // Create visit with user object and location and save it to DB
      Visit visit = new Visit(user, payload.getLocation());
      visit = visitService.saveVisit(visit);

      // Update user with latest visit object
      userService.updateUser(payload.getUserId(), visit);

      // Return 201, with visitId
      return new ResponseEntity<>("Visit created with ID: " + visit.getId(), HttpStatus.CREATED);
    } catch(Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/visit/{visitId}")
  @ResponseBody
  public ResponseEntity<?> getVisitById(@PathVariable Integer visitId) {
    try {
      Visit visit = visitService.getVisit(visitId);
      if (visit != null) {
        VisitResponse v = new VisitResponse(visit);
        List<VisitResponse> body = new ArrayList<>(Collections.singletonList(v));
        return new ResponseEntity<>(body, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Could not find visit with provided id", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
