package com.example.mfa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.mfa.model.GenerateMfaRequest;
import com.example.mfa.model.VerifyMfaRequest;
import com.example.mfa.model.data.Mfa;
import com.example.mfa.repository.MfaRepository;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MfaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {MailSenderAutoConfiguration.class, RedisAutoConfiguration.class})
public class MfaIntegrationTest {

  @LocalServerPort
  private int port;
  @MockBean
  private JavaMailSender javaMailSender;
  @MockBean
  private RedisTemplate<?, ?> redisTemplate;
  @Autowired
  private MfaRepository mfaRepository;

  private final TestRestTemplate restTemplate = new TestRestTemplate();

  @Test
  @DisplayName("Should generate MFA code and verify that it is correct.")
  void generateMfaAndVerifyMfa() {
    String email = "email@example.com";

    // Generate MFA code
    ResponseEntity<String> generateResponse = generateMfa(email);
    assertThat(generateResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

    // Check that MFA code is stored internally for the given email
    Optional<Mfa> mfa = mfaRepository.findById(email);
    assertThat(mfa).isPresent();

    // Verify that the MFA code is valid.
    assertThat(verifyMfa(email, mfa.get().getCode()).getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

    // A second attempt with the same code should fail.
    assertThat(verifyMfa(email, mfa.get().getCode()).getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
  }

  @Test
  @DisplayName("Should fail the MFA check if the code or email is invalid")
  void verifyMfaCodeInvalid() {
    String email = "email@example.com";
    generateMfa(email);
    String code = mfaRepository.findById(email).get().getCode();

    assertThat(verifyMfa(email, "INVALID_CODE").getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
    assertThat(verifyMfa("INVALID EMAIL", code).getStatusCode()).isEqualTo(HttpStatusCode.valueOf(401));
  }

  private ResponseEntity<String> verifyMfa(String email, String mfaCode) {
    return restTemplate.postForEntity(
        createUri("/mfa/verify"),
        new VerifyMfaRequest(email, mfaCode),
        String.class);
  }

  private ResponseEntity<String> generateMfa(String email) {
    return restTemplate.postForEntity(createUri("/mfa/generate"), new GenerateMfaRequest(email), String.class);
  }

  private String createUri(String uri) {
    return String.format("http://localhost:%s%s", port, uri);
  }
}
