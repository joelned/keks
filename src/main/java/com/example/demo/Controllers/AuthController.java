package com.example.demo.Controllers;

import com.example.demo.DTOs.RiderSignupDTO;
import com.example.demo.DTOs.StudentSignupDTO;
import com.example.demo.Models.Role;
import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.RoleRepository;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.AuthService;
import com.example.demo.Services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/student/register")
    public String registerStudent(HttpSession session,
                           @ModelAttribute StudentSignupDTO signUpDTO,
                           RedirectAttributes redirectAttribute) {
        try {
                authService.registerStudent(signUpDTO, session);
                redirectAttribute.addFlashAttribute("success", "Registration Successful");
                logger.info("{} registered as STUDENT", signUpDTO.getName());
                return "redirect:/student/register";
        }
        catch (Exception ex) {
            redirectAttribute.addFlashAttribute("error", ex.getMessage());
            return "redirect:/student/register";
        }
    }

    @PostMapping("/rider/register")
    public String registerRider(HttpSession session,
                           @ModelAttribute RiderSignupDTO riderDTO,
                           RedirectAttributes redirectAttribute) {
        try {
            authService.registerRider(riderDTO, session);
            redirectAttribute.addFlashAttribute("success", "Registration Successful");
            logger.info("{} registered as RIDER", riderDTO.getFirstName());
            return "redirect:/rider/register";
        }
        catch (Exception ex) {
            redirectAttribute.addFlashAttribute("error", ex.getMessage());
            return "redirect:/rider/register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password,
                        HttpServletResponse response,
                        RedirectAttributes
                                redirectAttributes, HttpSession session) {
        UserEntity user = userRepository.findByUserId(userId);
        List<Role> role = user.getRole();
        List<String> nameOfRole = role.stream().map(Role::getName).toList();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userId, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);

        response.addCookie(cookie);

        if(nameOfRole.contains("RIDER")){
            session.setAttribute("role", "RIDER");
            session.setAttribute("kekeId", userId);
            logger.info("Rider {} logged in at " + LocalDateTime.now() + " as {} ", userId, nameOfRole);
            return "redirect:/rider-dashboard";
        }
        if(nameOfRole.contains("STUDENT")){
            logger.info("Student {} logged in at " + LocalDateTime.now() + " as {} ", userId, nameOfRole);
            session.setAttribute("role", "STUDENT");
            session.setAttribute("userId", userId);
            return "redirect:/student-dashboard";
        }
        if(nameOfRole.contains("ADMIN")){
            session.setAttribute("userId", userId);
            logger.info("Admin {} logged in at " + LocalDateTime.now() + " as {} ", userId, nameOfRole);
            session.setAttribute("role", "ADMIN");
            return "redirect:/admin-dashboard";
        }
        return "redirect:/login";
    }
}