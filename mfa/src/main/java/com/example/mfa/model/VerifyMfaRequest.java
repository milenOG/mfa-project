package com.example.mfa.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Request to verify MFA code.
 *
 * @param email the email associated with the MFA code
 * @param mfaCode the MFA code
 */
public record VerifyMfaRequest(@NotBlank String email, @NotBlank String mfaCode) {}
