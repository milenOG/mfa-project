package com.example.mfa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.mfa.model.data.Mfa;
import com.example.mfa.repository.MfaRepository;

@TestConfiguration(proxyBeanMethods = false)
public class TestConfig {
  @Bean
  MfaRepository testMfaRepository() {
    return new TestMfaRepository();
  }

  /**
   * Test implementation of MFA repository. Represents internal storage as a map.
   * To be extended as needed.
   */
  private class TestMfaRepository implements MfaRepository {
    private final Map<String, Mfa> store;

    public TestMfaRepository() {
      store = new HashMap<>();
    }

    @Override
    public <S extends Mfa> S save(S entity) {
      store.put(entity.getEmail(), entity);

      return entity;
    }

    @Override
    public <S extends Mfa> Iterable<S> saveAll(Iterable<S> entities) {
      return null;
    }

    @Override
    public Optional<Mfa> findById(String s) {
      return Optional.ofNullable(store.get(s));
    }

    @Override
    public boolean existsById(String s) {
      return false;
    }

    @Override
    public Iterable<Mfa> findAll() {
      return null;
    }

    @Override
    public Iterable<Mfa> findAllById(Iterable<String> strings) {
      return null;
    }

    @Override
    public long count() {
      return store.size();
    }

    @Override
    public void deleteById(String s) {
      store.remove(s);
    }

    @Override
    public void delete(Mfa entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Mfa> entities) {

    }

    @Override
    public void deleteAll() {

    }
  }
}
