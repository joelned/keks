package com.example.demo.Services;

import com.example.demo.DTOs.RideRequestDTO;
import com.example.demo.Models.Ride;
import com.example.demo.Models.Rider;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.RideRequestRepository;
import com.example.demo.Repositories.RiderRepository;
import com.example.demo.Repositories.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StudentService {

    @Autowired
    private RideRequestRepository requestRepository;
    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PreAuthorize("hasAuthority('SCOPE_STUDENT')")
    public void requestRide(HttpSession session, RideRequestDTO requestDTO, String riderId) throws Exception{
        Rider rider = riderRepository.findByRiderId(riderId);

        if(rider == null){
            throw new Exception("Rider not Found");
        }

        String matricNumber = (String) session.getAttribute("userId");
        Student student = studentRepository.findByStudentId(matricNumber);

        if(matricNumber == null){
            throw new Exception("Student Not Found");
        }

        Ride ride = new Ride();
        ride.setPickUpLocation(requestDTO.getPickUpLocation());
        ride.setDestination(requestDTO.getDestination());
        ride.setDate(LocalDate.now());
        ride.setStudent(student);
        ride.setRider(rider);
        ride.setNumberOfPassengers(requestDTO.getNoOfPassengers());
        int price = requestDTO.getNoOfPassengers() * 200;
        ride.setPrice(price);
        ride.setStatus(false);
        requestRepository.save(ride);
    }
}
