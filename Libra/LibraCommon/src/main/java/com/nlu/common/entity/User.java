package com.nlu.common.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 8, nullable = false, unique = true)
    private String identifier; // thẻ thư viện

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "first_name", length = 100, nullable = false) // nvarchar 100, not null
    @Nationalized
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    @Nationalized
    private String lastName;

    @Column(name = "citizen_identification", length = 12, nullable = false, unique = true)
    private String citizenIdentification;

    @Column(name = "date_of_birth", length = 12, nullable = false, unique = true)
    private String dateOfBirth;

    @Column(name = "phone", length = 11, nullable = false, unique = true)
    private String phoneNumber;

    private boolean enabled;

    @ManyToMany()
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String identifier, String email, String password, String firstName, String lastName,
                String citizenIdentification, String dateOfBirth, String phoneNumber) {
        this.identifier = identifier;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.citizenIdentification = citizenIdentification;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCitizenIdentification() {
        return citizenIdentification;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        this.citizenIdentification = citizenIdentification;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" + "identifier='" + identifier + '\'' + ", email='" + email + '\'' + ", firstName='" +
                firstName + '\'' + ", lastName='" + lastName + '\'' + ", citizenIdentification='" +
                citizenIdentification + '\'' + ", dateOfBirth='" + dateOfBirth + '\'' + ", phoneNumber='" +
                phoneNumber + '\'' + ", roles=" + roles + '}';
    }
}
