package com.nlu.libra.book;

import com.nlu.common.entity.Borrow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BorrowRepository extends CrudRepository<Borrow, Integer> {

    Long countById(Integer id);

    @Query("SELECT b FROM Borrow b WHERE b.book.isbn = ?1 AND b.user.id = ?2")
    Borrow getBorrow(String isbn, Integer id);
}
