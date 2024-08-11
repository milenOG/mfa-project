package com.example.mfa.model.data;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * The MFA code representation that is stored in the database.
 */
@RedisHash(timeToLive = 600)
public class Mfa {
  @Id
  private String email;

  private String code;

  public Mfa() {
  }

  public Mfa(String email, String code) {
    this.email = email;
    this.code = code;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Mfa mfa = (Mfa) o;
    return Objects.equals(email, mfa.email) && Objects.equals(code, mfa.code);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(email);
    result = 31 * result + Objects.hashCode(code);
    return result;
  }
}
