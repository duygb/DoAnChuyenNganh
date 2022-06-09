package com.nlu.common.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 8)
  private String identifier; // thẻ thư viện

  @Column(length = 128, nullable = false, unique = true)
  private String email;

  @Column(length = 64, nullable = false)
  private String password;

  @Column(name = "first_name", length = 100, nullable = false)
  @Nationalized
  private String firstName;

  @Column(name = "last_name", length = 45, nullable = false)
  @Nationalized
  private String lastName;

  @Column(name = "citizen_identification", length = 12)
  private String citizenIdentification;

  @Column(name = "phone", length = 11)
  private String phoneNumber;

  private boolean enabled;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  public User() {
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", identifier='" + identifier + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", citizenIdentification='" + citizenIdentification + '\'' +
      ", phoneNumber='" + phoneNumber + '\'' +
      ", enabled=" + enabled +
      ", role=" + role +
      '}';
  }

  @Transient
  public String getFullName() {
    return lastName + " " + firstName;
  }
}
