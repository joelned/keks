package com.example.demo.Repositories;

import com.example.demo.Models.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RiderRepository extends JpaRepository<Rider, String> {
    Rider findByRiderId(String riderId);

    @Query("SELECT r FROM Rider r WHERE r.status = false")
    List<Rider> findRidersWithInactiveRides();
}
