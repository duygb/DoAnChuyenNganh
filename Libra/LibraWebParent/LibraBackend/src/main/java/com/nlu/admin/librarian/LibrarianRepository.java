package com.nlu.admin.librarian;

import com.nlu.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends PagingAndSortingRepository<User, Integer> {
  User findByEmail(String email);

  Long countById(Integer id);

  @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
  @Modifying
  void updateEnabledStatus(Integer id, boolean enabled);

  @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1% AND u.role = 3")
  Page<User> findAll(String keyword, Pageable pageable);
}
