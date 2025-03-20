package com.example.demo.Services;

import com.example.demo.Models.Ride;
import com.example.demo.Models.Rider;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.RideRequestRepository;
import com.example.demo.Repositories.RiderRepository;
import com.example.demo.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private RideRequestRepository rideRequestRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public int dailyRides(){
        List<Ride> ride = rideRequestRepository.findRidesByStatusAndDate(LocalDate.now());
        if (ride.isEmpty()) {
            return 0;
        }
        int dailyNumberOfRides = 0;

        for (int i = 0; i < ride.size(); i++) {
            dailyNumberOfRides += 1;
        }
        return dailyNumberOfRides;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public int dailyEarnings() throws Exception {
        List<Integer> ride = rideRequestRepository.findPriceByDate(LocalDate.now());

        if (ride.isEmpty()) {
            throw new Exception("No rides");
        }

        int dailyEarning = 0;
        for (Integer price : ride) {
            dailyEarning += price;
        }
        return dailyEarning;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public int numberOfRiders() throws Exception {
        return (int) riderRepository.count();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public int numberOfCustomers() throws Exception {
        return (int) studentRepository.count();
    }
}
