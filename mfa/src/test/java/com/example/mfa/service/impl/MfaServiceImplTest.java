package com.example.mfa.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.mfa.model.data.Mfa;
import com.example.mfa.repository.MfaRepository;

@ExtendWith(MockitoExtension.class)
class MfaServiceImplTest {

  @Mock
  private MfaRepository mfaRepository;
  @InjectMocks
  private MfaServiceImpl mfaService;

  @Test
  void testGenerateMfa() {
    String email = "test@example.com";
    when(mfaRepository.save(any(Mfa.class))).thenReturn(new Mfa("test@example.com", "123456"));

    String code = mfaService.generateMfa(email);

    verify(mfaRepository, times(1)).save(any());
    assertThat(code).isEqualTo("123456");
  }

  @Test
  void isMfaValid_missingEmail() {
    assertThat(mfaService.isMfaValid("notExistent", "123")).isFalse();
  }

  @Test
  void isMfaValid_wrongCode() {
    when(mfaRepository.findById("email")).thenReturn(Optional.of(new Mfa("email", "321")));
    assertThat(mfaService.isMfaValid("email", "123")).isFalse();
    verify(mfaRepository, times(0)).deleteById("email");
  }

  @Test
  void isMfaValid_correctCode() {
    when(mfaRepository.findById("email")).thenReturn(Optional.of(new Mfa("email", "123")));
    assertThat(mfaService.isMfaValid("email", "123")).isTrue();
    verify(mfaRepository).deleteById("email");
  }
}