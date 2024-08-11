package com.example.mfa.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mfa.model.GenerateMfaRequest;
import com.example.mfa.model.VerifyMfaRequest;
import com.example.mfa.service.MailService;
import com.example.mfa.service.MfaService;

/**
 * Controller that manages generation and verification of MFA codes.
 */
@RestController
@RequestMapping("/mfa")
public class MfaController {
  private final MfaService mfaService;
  private final MailService mailService;

  public MfaController(MfaService mfaService, MailService mailService) {
    this.mfaService = mfaService;
    this.mailService = mailService;
  }

  /**
   * Generates an MFA code, persists it and sends an email with the code.
   *
   * @param request the request to generate MFA
   * @return A 200 OK response if the MFA was generated.
   */
  @PostMapping("/generate")
  ResponseEntity<String> generateMfa(@RequestBody GenerateMfaRequest request) {
    String mfaCode = mfaService.generateMfa(request.email());
    mailService.sendMail(request.email(), mfaCode);

    return ResponseEntity.ok("Sending email with generated MFA. Please wait for 2 minutes before trying again.");
  }

  /**
   * Verifies the MFA code.
   *
   * @param request the request to verify the MFA
   * @return A 200 OK response if the MFA is valid or 401 otherwise
   */
  @PostMapping("/verify")
  ResponseEntity<String> verifyMfa(@RequestBody VerifyMfaRequest request) {
    if (mfaService.isMfaValid(request.email(), request.mfaCode())) {
      return ResponseEntity.ok("MFA is valid");
    } else {
      return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("MFA is not valid");
    }
  }

}
