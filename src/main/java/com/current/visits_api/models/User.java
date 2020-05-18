package com.current.visits_api.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
public class User extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "userId")
  private Integer id;

  @Column
  private String name;

  @OneToMany(mappedBy = "user")
  private Set<Visit> visits;

  public User() {}

  public User(String name) {
    this.name = name;
    this.visits = new HashSet<>();
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Visit> getVisits() {
    return visits;
  }

  public void setVisits(Set<Visit> visits) {
    this.visits = visits;
  }
}
