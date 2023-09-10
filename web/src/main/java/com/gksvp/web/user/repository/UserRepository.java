package com.gksvp.web.user.repository;

import com.gksvp.web.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByMobileNo(String mobileNo);

    User findByEmail(String email);

    User findByUserName(String userName);

    // Fetch the user along with its associated KYC info using JOIN FETCH
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.kycInfoList WHERE u.id = :userId")
    User findByIdWithKYCInfo(@Param("userId") Long userId);

    boolean existsByEmail(String email);

    boolean existsByMobileNo(String mobile);

    boolean existsByUserName(String username);

}
