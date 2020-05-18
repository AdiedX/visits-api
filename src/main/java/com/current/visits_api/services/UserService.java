package com.current.visits_api.services;

import com.current.visits_api.models.User;
import com.current.visits_api.models.Visit;
import com.current.visits_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public User getUser(Integer userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public User updateUser(Integer userId, Visit newVisit) {
    User user = userRepository.findById(userId).orElse(null);

    if (user != null) {
      Set<Visit> visits = user.getVisits();
      visits.add(newVisit);
      user.setVisits(visits);
      return userRepository.save(user);
    }

    return null;
  }
}
