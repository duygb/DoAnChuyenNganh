package com.nlu.libra.book;

import com.nlu.common.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE CONCAT(b.name, ' ', b.publisher, ' ', b.nameAuthor) LIKE %?1% AND b.available > 0")
    Page<Book> findAll(String keyword, Pageable pageable);
}
