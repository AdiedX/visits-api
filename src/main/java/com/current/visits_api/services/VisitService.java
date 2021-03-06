package com.current.visits_api.services;

import com.current.visits_api.models.Visit;
import com.current.visits_api.repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class VisitService {
  private final VisitRepository visitRepository;

  @Autowired
  public VisitService(VisitRepository visitRepository) {
    this.visitRepository = visitRepository;
  }

  public Visit saveVisit(Visit visit) {
    return visitRepository.save(visit);
  }

  public Optional<Visit> getVisit(Integer visitId) {
    return visitRepository.findById(visitId);
  }

  public Collection<Visit> searchMatchingEntries(Integer userId, String searchString) {
    return visitRepository.findMatchingEntries(userId, searchString);
  }
}
