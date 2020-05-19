package com.current.visits_api.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {
  private final String ALPHA_NUMERIC_REGEX = "^[A-Za-z0-9 ]+$";

  public boolean isAlphaNumeric(String s) {
    return Pattern.compile(ALPHA_NUMERIC_REGEX).matcher(s).matches();
  }
}
