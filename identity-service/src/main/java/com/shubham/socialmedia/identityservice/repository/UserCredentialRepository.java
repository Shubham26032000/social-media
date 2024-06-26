package com.shubham.socialmedia.identityservice.repository;

import com.shubham.socialmedia.identityservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByEmail(String email);
}
