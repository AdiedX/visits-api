package com.current.visits_api.models;

import javax.persistence.*;

@Entity
@Table(name="visits")
public class Visit extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "visitId")
  private Integer id;

  @ManyToOne
  @JoinColumn(name="userId", nullable = false)
  private User user;

  @Column
  private String location;

  public Visit() {}

  public Visit(User user, String location) {
    this.user = user;
    this.location = location;
  }

  public Integer getId() {
    return this.id;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
