package com.example.demo.DTOs;


public class RiderSignupDTO {
    private String riderId;
    private String firstName;
    private String lastName;
    private String password;
    private String gender;
    private String phoneNumber;
    private String kekePlateNumber;
    private String driverLicenseNumber;
    private String residentialAddress;
    private String guarantorFullName;
    private String guarantorPhoneNumber;
    private String relationshipToGuarantor;
    private String guarantorAddress;
    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKekePlateNumber() {
        return kekePlateNumber;
    }

    public void setKekePlateNumber(String kekePlateNumber) {
        this.kekePlateNumber = kekePlateNumber;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getGuarantorFullName() {
        return guarantorFullName;
    }

    public void setGuarantorFullName(String guarantorFullName) {
        this.guarantorFullName = guarantorFullName;
    }

    public String getGuarantorPhoneNumber() {
        return guarantorPhoneNumber;
    }

    public void setGuarantorPhoneNumber(String guarantorPhoneNumber) {
        this.guarantorPhoneNumber = guarantorPhoneNumber;
    }

    public String getRelationshipToGuarantor() {
        return relationshipToGuarantor;
    }

    public void setRelationshipToGuarantor(String relationshipToGuarantor) {
        this.relationshipToGuarantor = relationshipToGuarantor;
    }

    public String getGuarantorAddress() {
        return guarantorAddress;
    }

    public void setGuarantorAddress(String guarantorAddress) {
        this.guarantorAddress = guarantorAddress;
    }

}
