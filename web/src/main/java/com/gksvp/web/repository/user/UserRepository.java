package com.gksvp.web.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gksvp.web.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByMobileNo(String mobileNo);

    User findByEmail(String email);

    User findByUserName(String userName);

    User findByPAN(String PAN);

    User findByAdhaar(String adhaarNo);

}
