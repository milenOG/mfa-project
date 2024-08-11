package com.example.mfa.model;

import jakarta.validation.constraints.NotEmpty;

/**
 * Request to generate MFA.
 *
 * @param email the email address for the MFA
 */
public record GenerateMfaRequest(@NotEmpty String email) {}
