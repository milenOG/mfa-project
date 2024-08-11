package com.example.mfa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.mfa.service.MailService;

@Service
public class MailServiceImpl implements MailService {
  private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

  private final JavaMailSender mailSender;

  public MailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendMail(String email, String mfaCode) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject("MFA Code");
    message.setText(String.format("Your MFA code is %s. The code expires after 5 minutes.", mfaCode));

    mailSender.send(message);
    logger.info("MFA Email sent to {}", email);
  }
}
