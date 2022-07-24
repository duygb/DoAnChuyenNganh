package com.nlu.admin.borrow;

import com.nlu.common.entity.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends PagingAndSortingRepository<Borrow, Integer> {
    Long countById(Integer id);

    @Query("UPDATE Borrow b SET b.enabled = ?2 WHERE b.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT b FROM Borrow b WHERE CONCAT(b.user.firstName, ' ', b.book.name) LIKE %?1%")
    Page<Borrow> findAll(String keyword, Pageable pageable);
}
