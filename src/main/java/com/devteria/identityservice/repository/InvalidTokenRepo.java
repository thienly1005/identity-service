package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepo extends JpaRepository<InvalidatedToken, String> {
}
