package com.example.demo.Controllers;

import com.example.demo.DTOs.RideRequestDTO;
import com.example.demo.Models.Ride;
import com.example.demo.Models.Rider;
import com.example.demo.Models.Student;
import com.example.demo.Repositories.RideRequestRepository;
import com.example.demo.Repositories.RiderRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Services.AdminService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class PageController implements ErrorController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RideRequestRepository requestRepository;

    @Autowired
    private RiderRepository riderRepository;

    private final Logger logger = LoggerFactory.getLogger(PageController.class);

    @GetMapping("/")
    public String landingPage(){
       return "Landing";
    }
    @GetMapping("/rider/register")
    public String riderRegister(Model model, HttpSession httpSession){
        Random random = new Random();

        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String kekeId = "Keks" + randomNumber;
        httpSession.setAttribute("kekeId", kekeId);
        model.addAttribute("kekeId", kekeId);
        return "RiderReg";
    }

    @GetMapping("/student/register")
    public String studentRegister(){
        return "Registerationn";
    }

    @GetMapping("/rider-dashboard")
    public String riderDashboard(Model model, HttpSession session){
       String kekeId = (String) session.getAttribute("kekeId");
        List<Ride> rideRequests = requestRepository.findByStatus();

        if(rideRequests.isEmpty()){
            logger.info("No Rides Found");
        }
       model.addAttribute("rideRequests", rideRequests);
       model.addAttribute("kekeId", kekeId);

       return "RiderBoard";
    }

    @GetMapping("/student-dashboard")
    public String studentDashBoard(HttpSession session, Model model) throws RuntimeException {
       String matricNumber = (String)session.getAttribute("userId");
       Student student = studentRepository.findByStudentId(matricNumber);
       if(student == null){
           throw new RuntimeException("User not found");
       }
        model.addAttribute("student", student);
        return "StudentDashboard";
    }


    @GetMapping("/access-denied")
    public String accessDenied(){
        return "accessdenied";
    }

    @GetMapping("/login")
    public String login(){
        return "Loginn";
    }

    @GetMapping("/schedule")
    public String schedule(){
        return "Schedule";
    }

    @PreAuthorize("hasAuthority('SCOPE_STUDENT')")
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) throws Exception {
       String matricNumber = (String) session.getAttribute("userId");
       Student student = studentRepository.findByStudentId(matricNumber);
       if(student == null){
           throw new Exception("Student Not Found");
       }
       model.addAttribute("student", student);
        return "Profilee";
    }

    @GetMapping("/find-ride")
    public String findRide(RedirectAttributes model, @ModelAttribute RideRequestDTO rideRequestDTO){
        List<Rider>freeRiders = riderRepository.findRidersWithInactiveRides();


        logger.info(freeRiders.toString());
        model.addFlashAttribute("riders", freeRiders);
        model.addFlashAttribute("pickUpLocation", rideRequestDTO.getPickUpLocation());
        model.addFlashAttribute("destination", rideRequestDTO.getDestination());
        model.addFlashAttribute("numberOfPassengers", rideRequestDTO.getNoOfPassengers());
        return "redirect:/student-dashboard";
    }

    @GetMapping("/error")
    public String error(){
        return "redirect:/login";
    }


    @GetMapping("/admin-dashboard")
    public String dashboard(Model model, RedirectAttributes model2) throws Exception {
        int numberOfRiders = adminService.numberOfRiders();
        int numberOfStudents = adminService.numberOfCustomers();
        List<Rider>riders = riderRepository.findAll();
        List<Ride> rides = requestRepository.findAll();

        int totalDailyRides = adminService.dailyRides();
        Map<String, Integer> pricePerRider= rides.stream().collect(Collectors.groupingBy(
                ride -> ride.getRider().getRiderId(), Collectors.summingInt(Ride::getPrice)
        ));

       List<Student> student = studentRepository.findAll();

        model.addAttribute("numberOfRiders", numberOfRiders);
        model.addAttribute("numberOfStudents", numberOfStudents);
        model.addAttribute("riders", riders);
        model.addAttribute("totalDailyRides", totalDailyRides);
        model.addAttribute("pricePerRider", pricePerRider);
        model.addAttribute("students", student);
        return "KeksAdmin";
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/profile/{name}")
    public String handleProfileAdminLookUp(@PathVariable String name, Model model) throws Exception{
       Student student = studentRepository.findByName(name);

       if(student == null){
          throw new Exception("Student cannot be found");
       }

       model.addAttribute("student", student);
       return "Profilee";

    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/admin-customers")
    public String customers(Model model){
        List<Ride>rides = requestRepository.findAll();

        List<Student> students = studentRepository.findAll();
        Map<String, Integer> pricePerStudent = rides.stream().collect(Collectors.groupingBy(
                ride -> ride.getStudent().getStudentId(),Collectors.summingInt(Ride::getPrice)
        ));
        Map<String, Long> numberOfRidesPerStudent= rides.stream().collect(Collectors.groupingBy(
                ride -> ride.getStudent().getStudentId(),Collectors.counting()
        ));

        model.addAttribute("pricePerStudent", pricePerStudent);
        model.addAttribute("ridesPerStudent", numberOfRidesPerStudent);
        model.addAttribute("students", students);
        return "Customers";
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/admin-riders")
    public String riders(Model model) throws Exception{
        List<Rider>riders = riderRepository.findAll();
        List<Ride> rides = requestRepository.findAll();
        if(riders.isEmpty()){
           throw new Exception("Rider not found");
        }

        Map<String, Long> ridesPerRider= rides.stream().collect(Collectors.groupingBy(
                ride -> ride.getRider().getRiderId(),Collectors.counting()
        ));

        model.addAttribute("riders", riders);
        model.addAttribute("ridesPerRider", ridesPerRider);
        return "Riders";
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/earnings")
    public String earnings(Model model){

        List<Rider>riders = riderRepository.findAll();

        List<Ride> rides = requestRepository.findAll();
        List<Object[]> perDay = requestRepository.getTotalPriceForToday();
        List<Object[]> perMonth = requestRepository.getTotalPriceForCurrentMonthPerRider();
        List<Object[]> perWeek= requestRepository.getTotalPriceLastWeekPerRider();

        Map<String, Long> ridesPerRider= rides.stream().collect(Collectors.groupingBy(
                ride -> ride.getRider().getRiderId(),Collectors.counting()
        ));
        model.addAttribute("ridesPerRider", ridesPerRider);
        model.addAttribute("riders", riders);
        model.addAttribute("earningsPerDay", perDay);
        model.addAttribute("earningsPerWeek", perWeek);
        model.addAttribute("earningsPerMonth", perMonth);



        return "KeksEarnings";
    }

}
