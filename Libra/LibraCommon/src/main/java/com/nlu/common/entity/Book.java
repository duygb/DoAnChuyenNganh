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
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String alias;

    @Column(length = 13, nullable = false)
    private String isbn;

    @Column(length = 64)
    private String photos;

    @Column(name = "name_author")
    @Nationalized
    private String nameAuthor;

    @Nationalized
    private String publisher;

    @Column(length = 3, nullable = false)
    private byte quantity;

    @Column(name = "print_year", length = 4, nullable = false)
    private short printYear;

    @Column(length = 3, nullable = false)
    private byte available;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    @Override
    public String toString() {
        return "Book{" + "name='" + name + '\'' + ", alias='" + alias + '\'' + ", isbn='" + isbn + '\'' +
                ", nameAuthor='" + nameAuthor + '\'' + ", publisher='" + publisher + '\'' + ", quantity=" + quantity +
                ", printYear=" + printYear + ", available=" + available + '}';
    }

    @Transient
    public String getPhotosImagePath() {
        if (id == null || photos == null) return "/images/default-book.jpg";

        return "/images/book-photos/" + this.id + "/" + this.photos;
    }

}
