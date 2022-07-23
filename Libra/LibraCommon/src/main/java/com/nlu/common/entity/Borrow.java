package com.nlu.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "borrow")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, unique = true)
    @Nationalized
    private String name;

    @Column(length = 2, nullable = false)
    private byte quantity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "borrow_book",
            joinColumns = @JoinColumn(name = "borrow_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();

    @Override
    public String toString() {
        return "Borrow{" + "name='" + name + '\'' + ", quantity=" + quantity + ", books=" + books + '}';
    }
}
