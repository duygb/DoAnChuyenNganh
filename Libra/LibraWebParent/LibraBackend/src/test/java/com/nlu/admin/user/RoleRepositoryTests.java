package com.nlu.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.nlu.admin.librarian.RoleRepository;
import com.nlu.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

  @Autowired
  private RoleRepository roleRepository;

  @Test
  public void testCreateRole() {
    Role admin = new Role();
    admin.setName("Admin");

    Role savedRole = roleRepository.save(admin);

    assertThat(savedRole.getId()).isGreaterThan(0);
  }

  @Test
  public void testCreateMultipleRoles() {
    Role reader = new Role();
    reader.setName("Độc giả");
    Role librarian = new Role();
    librarian.setName("Thủ thư");

    roleRepository.saveAll(List.of(reader, librarian));
  }
}
