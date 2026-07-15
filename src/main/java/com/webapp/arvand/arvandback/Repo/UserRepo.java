package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String> {
    UserEntity findByUserName(String userName);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE u.userName = :username AND r.group = :group")
    UserEntity existsUserWithGroup(@Param("username") String username, @Param("group") String group);


    @Query("select u from UserEntity u join fetch u.roles where u.userName = :username")
    UserEntity findByUserNameWithRoles(String username);

    Optional<UserEntity> findByPhoneNumberEqualsAndActiveTrue(String phone);}
