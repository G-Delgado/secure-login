package com.login.secureloginbackend.repository;

import com.login.secureloginbackend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Query(value = "SELECT CASE WHEN(COUNT(*) > 0) THEN true ELSE false END FROM UserModel u WHERE u.email = :email")
    boolean findByEmail(String email);

    Optional<UserModel> findUserModelByEmail(String email);

    @Query(value = "SELECT u FROM UserModel u WHERE u.role = 'ADMIN'")
    Optional<UserModel> findUserModelAdmin();

    @Modifying
    @Query(value = "UPDATE UserModel u SET u.password = :password WHERE u.email = :email")
    void updatePassword(String email, String password);




}
