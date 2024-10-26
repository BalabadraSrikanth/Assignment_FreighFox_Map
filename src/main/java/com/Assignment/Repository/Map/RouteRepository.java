package com.Assignment.Repository.Map;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Assignment.Entity.Map.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByFromPincodeAndToPincode(String fromPincode, String toPincode);
}
