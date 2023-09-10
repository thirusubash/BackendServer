package com.gksvp.web.user.repository;
import com.gksvp.web.user.entity.GeoLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {

}
