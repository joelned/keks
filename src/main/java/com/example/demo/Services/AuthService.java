package com.example.demo.Services;

import com.example.demo.DTOs.RiderSignupDTO;
import com.example.demo.DTOs.StudentSignupDTO;
import com.example.demo.Models.Rider;
import com.example.demo.Models.Role;
import com.example.demo.Models.Student;
import com.example.demo.Models.UserEntity;
import com.example.demo.Repositories.RiderRepository;
import com.example.demo.Repositories.RoleRepository;
import com.example.demo.Repositories.StudentRepository;
import com.example.demo.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);


    public void registerRider(RiderSignupDTO riderSignupDTO, HttpSession httpSession){
        try {
            String kekeId = (String) httpSession.getAttribute("kekeId");
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(kekeId);
            userEntity.setPassword(passwordEncoder.encode(riderSignupDTO.getPassword()));
            Role roleSignedUpAs = roleRepository.findByName("RIDER");
            userEntity.setRole(Collections.singletonList(roleSignedUpAs));
            String riderName = riderSignupDTO.getFirstName() + " " + riderSignupDTO.getLastName();
            userEntity.setName(riderName);
            userRepository.save(userEntity);

            Rider rider = new Rider();
            rider.setRiderId(kekeId);
            rider.setPassword(passwordEncoder.encode(riderSignupDTO.getPassword()));
            rider.setFirstName(riderSignupDTO.getFirstName());
            rider.setLastName(riderSignupDTO.getLastName());
            rider.setDateJoined(LocalDate.now());
            rider.setGender(riderSignupDTO.getGender());
            rider.setPhoneNumber(riderSignupDTO.getPhoneNumber());
            rider.setResidentialAddress(riderSignupDTO.getResidentialAddress());
            rider.setUser(userEntity);
            rider.setRelationshipToGuarantor(riderSignupDTO.getRelationshipToGuarantor());
            rider.setGuarantorAddress(riderSignupDTO.getGuarantorAddress());
            rider.setDriverLicenseNumber(riderSignupDTO.getDriverLicenseNumber());
            rider.setGuarantorPhoneNumber(riderSignupDTO.getGuarantorPhoneNumber());
            rider.setGuarantorFullName(riderSignupDTO.getGuarantorFullName());
            rider.setKekePlateNumber(riderSignupDTO.getKekePlateNumber());
            riderRepository.save(rider);
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

    public void registerStudent(StudentSignupDTO studentDTO, HttpSession session) throws Exception {

        if (userRepository.findByUserId(studentDTO.getStudentId()) != null) {
            throw new Exception("Matric number already exists");
        }

        UserEntity student = new UserEntity();
        student.setUserId(studentDTO.getStudentId());
        student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        student.setName(studentDTO.getName());
        Role role = roleRepository.findByName("STUDENT");
        student.setRole(Collections.singletonList(role));

        student = userRepository.save(student);

        session.setAttribute("studentId", student.getUserId());

        Student studentRecords = new Student();
        studentRecords.setStudentId(studentDTO.getStudentId()); // Ensure this matches the UserEntity ID
        studentRecords.setDateJoined(LocalDate.now());
        studentRecords.setEmail(studentDTO.getEmail());
        studentRecords.setName(studentDTO.getName());
        studentRecords.setUserEntity(student);
        studentRecords.setCourseOfStudy(studentDTO.getCourseOfStudy());
        studentRecords.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        studentRepository.save(studentRecords);
    }

}
