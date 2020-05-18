package com.current.visits_api.models;

import lombok.Data;

@Data
public class VisitResponse {
  private Integer visitId;
  private Integer userId;
  private String location;

  public VisitResponse() {}

  public VisitResponse(Visit visit) {
    this.visitId = visit.getId();
    this.userId = visit.getUser().getId();
    this.location = visit.getLocation();
  }
}
