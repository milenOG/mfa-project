package com.example.mfa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.example.mfa.model.GenerateMfaRequest;
import com.example.mfa.model.VerifyMfaRequest;
import com.example.mfa.service.MailService;
import com.example.mfa.service.MfaService;

@ExtendWith(MockitoExtension.class)
class MfaControllerTest {
  @Mock
  private MfaService mfaService;
  @Mock
  private MailService mailService;
  @InjectMocks
  private MfaController mfaController;

  @Test
  void testGenerateMfa() {
    String email = "test@example.com";
    String mfa = "123456";
    when(mfaService.generateMfa(email)).thenReturn(mfa);

    ResponseEntity<String> response = mfaController.generateMfa(new GenerateMfaRequest(email));

    verify(mfaService, times(1)).generateMfa(email);
    verify(mailService, times(1)).sendMail(email, mfa);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
  }

  @Test
  void testVerifyMfa_invalid() {
    String email = "test@example.com";
    String mfa = "123456";
    when(mfaService.isMfaValid(email, mfa)).thenReturn(false);

    ResponseEntity<String> response = mfaController.verifyMfa(new VerifyMfaRequest(email, mfa));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
  }

  @Test
  void testVerifyMfa_valid() {
    String email = "test@example.com";
    String mfa = "123456";
    when(mfaService.isMfaValid(email, mfa)).thenReturn(true);

    ResponseEntity<String> response = mfaController.verifyMfa(new VerifyMfaRequest(email, mfa));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
  }
}