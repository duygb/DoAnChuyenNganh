package com.nlu.admin.user;

import com.nlu.common.entity.Role;
import com.nlu.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

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

        User userAdmin = new User("admin1@gmail.com","admin","","");
        userAdmin.setRole(admin);

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
}
