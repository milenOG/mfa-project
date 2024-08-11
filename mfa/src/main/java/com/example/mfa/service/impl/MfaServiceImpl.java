package com.example.mfa.service.impl;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mfa.model.data.Mfa;
import com.example.mfa.repository.MfaRepository;
import com.example.mfa.service.MfaService;

@Service
public class MfaServiceImpl implements MfaService {
  private static final int MFA_LENGTH = 6;

  private final MfaRepository mfaRepository;

  public MfaServiceImpl(MfaRepository mfaRepository) {
    this.mfaRepository = mfaRepository;
  }

  @Override
  public String generateMfa(String email) {
    String code = generateCode();
    Mfa mfa = new Mfa(email, code);

    return mfaRepository.save(mfa).getCode();
  }

  @Override
  public boolean isMfaValid(String email, String code) {
    Optional<String> matchedMfa = mfaRepository.findById(email)
        .map(Mfa::getCode)
        .filter(code::equals);

    if (matchedMfa.isPresent()) {
      mfaRepository.deleteById(email); // Delete the MFA code so that it can't be used again.
      return true;
    }

    return false;
  }

  private static String generateCode() {
    StringBuilder code = new StringBuilder();
    SecureRandom random = new SecureRandom();
    for (int count = 1; count <= MFA_LENGTH; count++) {
      code.append(random.nextInt(10));
    }

    return code.toString();
  }
}
