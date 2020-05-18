package com.current.visits_api.repositories;

import com.current.visits_api.models.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface VisitRepository extends CrudRepository<Visit, Integer> {
  @Query(
      value = "SELECT * FROM visits WHERE user_id=?1 AND difference(visits.location, ?2) > 2 ORDER BY visits.created_at DESC LIMIT 5",
      nativeQuery = true
  )
  Collection<Visit> findMatchingEntries(Integer userId, String searchString);
}
