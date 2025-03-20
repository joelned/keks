package com.example.demo.Controllers;

import com.example.demo.DTOs.RideRequestDTO;
import com.example.demo.Models.Ride;
import com.example.demo.Models.Rider;
import com.example.demo.Repositories.RideRequestRepository;
import com.example.demo.Repositories.RiderRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Services.StudentService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RideRequestRepository rideRequestRepository;

    @Autowired
    private StudentService studentService;

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/request-ride")
    public String requestRide(HttpSession session, @ModelAttribute RideRequestDTO requestDTO, RedirectAttributes model,
                              @RequestParam String riderId) throws Exception {
        try {
            studentService.requestRide(session, requestDTO, riderId);

            model.addFlashAttribute("success", "Ride Requested");
            return "redirect:/student-dashboard";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            logger.error(ex.getMessage());
            return "redirect:/student-dashboard";
        }
    }

}
