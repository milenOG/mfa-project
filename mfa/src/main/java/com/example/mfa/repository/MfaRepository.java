package com.example.mfa.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.mfa.model.data.Mfa;

/**
 * Provides CRUD methods for MFA codes.
 */
public interface MfaRepository extends CrudRepository<Mfa, String> {

}
