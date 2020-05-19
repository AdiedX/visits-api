package com.current.visits_api.repositories;

import com.current.visits_api.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {}
