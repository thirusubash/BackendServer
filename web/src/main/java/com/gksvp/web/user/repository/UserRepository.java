package com.gksvp.web.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gksvp.web.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByMobileNo(String mobileNo);

    User findByEmail(String email);

    User findByUserName(String userName);

    // Fetch the user along with its associated KYC info using JOIN FETCH
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.kycInfo WHERE u.id = :userId")
    User findByIdWithKYCInfo(@Param("userId") Long userId);

}
