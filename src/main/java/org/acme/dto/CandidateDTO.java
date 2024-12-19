package org.acme.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class CandidateDTO {
   @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "egn")
    private String egn;

    @Column(name = "phone")
    private String phone;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "admin")
    private Boolean admin;

    @Column(name = "latin_name")
    private String latinName;

    @Column(name = "latin_surname")
    private String latinSurname;

    @Column(name = "latin_lastname")
    private String latinLastname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "date_id_created")
    private LocalDate dateIdCreated;

    @Column(name = "id_issued_by")
    private String idIssuedBy;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "district")
    private String district;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "school_city")
    private String schoolCity;

    @Column(name = "secondary_education")
    private String secondaryEducation;

    // Default constructor
    public CandidateDTO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getLatinSurname() {
        return latinSurname;
    }

    public void setLatinSurname(String latinSurname) {
        this.latinSurname = latinSurname;
    }

    public String getLatinLastname() {
        return latinLastname;
    }

    public void setLatinLastname(String latinLastname) {
        this.latinLastname = latinLastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getDateIdCreated() {
        return dateIdCreated;
    }

    public void setDateIdCreated(LocalDate dateIdCreated) {
        this.dateIdCreated = dateIdCreated;
    }

    public String getIdIssuedBy() {
        return idIssuedBy;
    }

    public void setIdIssuedBy(String idIssuedBy) {
        this.idIssuedBy = idIssuedBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSecondaryEducation() {
        return secondaryEducation;
    }

    public void setSecondaryEducation(String secondaryEducation) {
        this.secondaryEducation = secondaryEducation;
    }
}
