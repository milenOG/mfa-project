package com.example.mfa.service;

import org.springframework.scheduling.annotation.Async;

public interface MailService {

  /**
   * Sends an email with the MFA code.
   *
   * @param email the email of the recipient
   * @param mfaCode the MFA code
   */
  @Async
  void sendMail(String email, String mfaCode);

}
