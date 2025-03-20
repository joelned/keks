package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table
@Data
public class Rider {
    @Id
    private String riderId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String firstName;
    private String lastName;
    private String password;
    private String gender;
    private String phoneNumber;
    private String kekePlateNumber;
    private String driverLicenseNumber;

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    private LocalDate dateJoined;
    private String residentialAddress;
    private String guarantorFullName;
    private String guarantorPhoneNumber;
    private String relationshipToGuarantor;
    private boolean status;

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setKekePlateNumber(String kekePlateNumber) {
        this.kekePlateNumber = kekePlateNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public void setGuarantorFullName(String guarantorFullName) {
        this.guarantorFullName = guarantorFullName;
    }

    public void setGuarantorPhoneNumber(String guarantorPhoneNumber) {
        this.guarantorPhoneNumber = guarantorPhoneNumber;
    }

    public void setRelationshipToGuarantor(String relationshipToGuarantor) {
        this.relationshipToGuarantor = relationshipToGuarantor;
    }

    public void setGuarantorAddress(String guarantorAddress) {
        this.guarantorAddress = guarantorAddress;
    }

    public String getRiderId() {
        return riderId;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getKekePlateNumber() {
        return kekePlateNumber;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public String getGuarantorFullName() {
        return guarantorFullName;
    }

    public String getGuarantorPhoneNumber() {
        return guarantorPhoneNumber;
    }

    public String getRelationshipToGuarantor() {
        return relationshipToGuarantor;
    }

    public String getGuarantorAddress() {
        return guarantorAddress;
    }

    private String guarantorAddress;
}
