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
  public void testListFirstPage() {
    int pageNumber = 0;
    int pageSize = 5;
    Role reader = entityManager.find(Role.class, 2);

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<User> page = userRepo.findAll("", pageable);

    List<User> listUsers = page.getContent();

    listUsers.forEach(user -> System.out.println(user));

    assertThat(listUsers.size()).isGreaterThan(0);
  }
  @Test
  public void testUpdateUserEnabledStatus() {
    Integer id = 2;

    userRepo.updateEnabledStatus(id, true);
  }

  @Test
  @Rollback(true)
  public void testDeleteUser() {
    User user = userRepo.findById(14).get();

    userRepo.deleteById(user.getId());
  }
}
