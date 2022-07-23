package com.nlu.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", identifier='" + identifier + '\'' + ", email='" + email + '\'' +
                ", password='" + password + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' +
                ", citizenIdentification='" + citizenIdentification + '\'' + ", phoneNumber='" + phoneNumber + '\'' +
                ", enabled=" + enabled + ", role=" + role + '}';
    }

    @Transient
    public String getFullName() {
        return lastName + " " + firstName;
    }
}
