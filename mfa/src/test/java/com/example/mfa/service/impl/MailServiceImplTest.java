package com.example.mfa.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {
  @Mock
  private JavaMailSender javaMailSender;
  @InjectMocks
  private MailServiceImpl mailService;

  @Test
  void testSendMail() {
    SimpleMailMessage expected = new SimpleMailMessage();
    expected.setTo("test@example.com");
    expected.setSubject("MFA Code");
    expected.setText("Your MFA code is 123456. The code expires after 5 minutes.");

    mailService.sendMail("test@example.com", "123456");

    verify(javaMailSender, times(1)).send(expected);
  }
}