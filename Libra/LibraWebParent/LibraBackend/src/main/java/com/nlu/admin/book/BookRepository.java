package com.nlu.admin.book;

import com.nlu.common.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Book findByIsbn(String isbn);

    Long countById(Integer id);

    @Query("UPDATE Book u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT b FROM Book b WHERE CONCAT(b.id, ' ', b.name, ' ', b.publisher, ' ', b.nameAuthor) LIKE %?1%")
    Page<Book> findAll(String keyword, Pageable pageable);
}
