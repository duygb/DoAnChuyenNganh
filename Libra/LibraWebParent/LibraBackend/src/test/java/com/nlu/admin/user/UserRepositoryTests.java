package com.nlu.admin.user;

import com.nlu.common.entity.Role;
import com.nlu.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void testCreateNewUser() {
    Role admin = entityManager.find(Role.class, 3);
    Role reader = entityManager.find(Role.class, 2);

    User userAdmin = new User("long@gmail.com", "long1234");
    userAdmin.setRole(reader);

    User savedUser1 = userRepo.save(userAdmin);

    assertThat(savedUser1.getId()).isGreaterThan(0);
  }

  @Test
  public void testGetUserById() {
    User librarianUser = userRepo.findById(1).get();
    assertThat(librarianUser.getEmail()).isEqualTo("admin1@gmail.com");
  }

  @Test
  public void testUpdateUser() {
    User librarianUser = userRepo.findById(1).get();
    librarianUser.setEmail("long@gmail.com");

    userRepo.save(librarianUser);

    assertThat(librarianUser.getEmail()).isEqualTo("long@gmail.com");
  }

  @Test
  public void testDeleteUser() {
    User librarianUser = userRepo.findById(1).get();

    userRepo.deleteById(librarianUser.getId());
  }

  @Test
  public void testListFirstPage() {
    int pageNumber = 0;
    int pageSize = 2;

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<User> page = userRepo.findAll(pageable);

    List<User> listUsers = page.getContent();

    listUsers.forEach(user -> System.out.println(user));

    assertThat(listUsers.size()).isEqualTo(pageSize);
  }

  @Test
  public void testSearchUser() {
    String keyword = "admin1";

    int pageNumber = 0;
    int pageSize = 1;

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<User> page = userRepo.findAll(keyword, pageable);

    List<User> listUsers = page.getContent();

    listUsers.forEach(user -> System.out.println(user));

    assertThat(listUsers.size()).isGreaterThan(0);
  }
}
