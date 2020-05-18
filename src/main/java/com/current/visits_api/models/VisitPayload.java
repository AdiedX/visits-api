package com.current.visits_api.models;

import lombok.Data;

@Data
public class VisitPayload {
  private Integer userId;
  private String location;
}
