package org.acme.dto;

public class CandidateDTO {
    private Long id;
    private String name;
    private String surname;
    private String lastName;
    private String email;
    private String egn;
    private String phone;

    // Default constructor (required for serialization)
    public CandidateDTO() {
    }

    // Parameterized constructor
    public CandidateDTO(Long id, String name, String surname, String lastName, String egn, String phone, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.email = email;
        this.egn = egn;
        this.phone = phone;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    // Optional: Override toString() for better logging/debugging
    @Override
    public String toString() {
        return "CandidateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
