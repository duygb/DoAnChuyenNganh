package com.nlu.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "borrow")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "loan_date")
    private Date loanDate;

    @Column(name = "returned_date")
    private Date returnedDate;

    private boolean enabled;

    @Override
    public String toString() {
        return "Borrow{" + "user=" + user + ", book=" + book + ", loanDate=" + loanDate +
                ", returnedDate=" + returnedDate + ", enabled=" + enabled + '}';
    }
}

