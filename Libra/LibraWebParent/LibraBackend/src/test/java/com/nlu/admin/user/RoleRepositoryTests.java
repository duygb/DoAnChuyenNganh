package com.nlu.admin.user;

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
    public void testCreateAllRoles() {
        Role librarian = new Role("Thủ thư");
        Role reader = new Role("Độc giả");

        roleRepository.saveAll(List.of(librarian, reader));
    }
}
