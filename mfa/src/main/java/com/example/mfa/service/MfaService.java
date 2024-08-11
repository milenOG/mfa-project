package com.example.mfa.service;

public interface MfaService {

  /**
   * Generates an MFA code and stores it for the given email.
   *
   * @param email the email to generate the MFA code for.
   * @return the generated MFA code
   */
  String generateMfa(String email);

  /**
   * Checks whether the MFA code is valid for the provided email.
   *
   * @param email the email
   * @param code the MFA code
   * @return true if the MFA code is valid
   */
  boolean isMfaValid(String email, String code);
}
