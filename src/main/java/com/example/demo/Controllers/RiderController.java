package com.example.demo.Controllers;

import com.example.demo.Models.Ride;
import com.example.demo.Repositories.RideRequestRepository;
import com.example.demo.Repositories.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/api/v1")
public class RiderController {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RideRequestRepository rideRequestRepository;

    @PreAuthorize("hasAuthority('SCOPE_RIDER')")
    @PostMapping("/approve-request")
    public String approveRequest(@RequestParam int rideId) throws Exception {
       Optional<Ride> ride = rideRequestRepository.findById(rideId);
       if(ride.isEmpty()){
           throw new Exception("Ride not found");
       }
       rideRequestRepository.approveRequest(rideId);
       return "redirect:/rider-dashboard";
    }
}
