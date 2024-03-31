package com.orana.appstockexchange.repository;

import com.orana.appstockexchange.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE 1=1 ORDER BY u.username")
    Set<AppUser> findUsers();

    @Query("SELECT u FROM AppUser u WHERE u.username = :username")
    Optional<AppUser> findUserByUsername(String username);
}