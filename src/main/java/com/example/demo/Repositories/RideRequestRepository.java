package com.example.demo.Repositories;

import com.example.demo.Models.Ride;
import com.example.demo.Models.Rider;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RideRequestRepository extends JpaRepository<Ride, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Ride rd SET rd.status = true WHERE rd.rideId = :rideId")
    void approveRequest(@Param("rideId") int rideId);

    @Query("SELECT r FROM Ride r WHERE r.status = false")
    List<Ride> findByStatus();

    @Query("SELECT r FROM Ride r WHERE r.status = true AND r.date = :date")
    List<Ride>findRidesByStatusAndDate(@Param("date")LocalDate date);

    @Query("SELECT r.price FROM Ride r WHERE r.date = :date")
    List<Integer>findPriceByDate(@Param("date") LocalDate date);


    @Query(value = "SELECT r.rider_id, r.date, SUM(r.price) " +
            "FROM ride r WHERE r.date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 6 DAY) AND CURRENT_DATE " +
            "GROUP BY r.rider_id, r.date ORDER BY r.date DESC",
            nativeQuery = true)
    List<Object[]> getTotalPriceLastWeekPerRider();
    @Query("SELECT r.rider.riderId, SUM(r.price) " +
            "FROM Ride r WHERE FUNCTION('MONTH', r.date) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', r.date) = FUNCTION('YEAR', CURRENT_DATE) " +
            "GROUP BY r.rider.riderId")
    List<Object[]> getTotalPriceForCurrentMonthPerRider();

    @Query("SELECT r.rider.riderId, r.date, SUM(r.price) " +
            "FROM Ride r WHERE r.date = CURRENT_DATE " +
            "GROUP BY r.rider.riderId, r.date ORDER BY r.date DESC")
    List<Object[]> getDailyTotalPricePerRider();

    @Query(value = "SELECT r.date, SUM(r.price) AS total_price " +
            "FROM ride r " +
            "WHERE r.date = CURRENT_DATE " +
            "GROUP BY r.date",
            nativeQuery = true)
    List<Object[]> getTotalPriceForToday();
}
