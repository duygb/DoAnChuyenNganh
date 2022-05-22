package com.nlu.admin.user;

import com.nlu.common.entity.Role;
import com.nlu.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepo;

    @Test
    @Rollback(false)
    public void testCreateNewUser() {
        Role librarian = new Role(1);
        Role reader = new Role(2);

        User librarianUser = new User("11111112","long@gmail.com","long1234","long",
                "nguyễn","012345678911","0123456788");
        librarianUser.addRole(librarian);
        librarianUser.addRole(reader);

        User savedUser = userRepo.save(librarianUser);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetUserById() {
        User librarianUser = userRepo.findById(1).get();
        assertThat(librarianUser.getEmail()).isEqualTo("thong@gmail.com");
    }

    @Test
    @Rollback(false)
    public void testUpdateUser() {
        User librarianUser = userRepo.findById(1).get();
        librarianUser.setEmail("long@gmail.com");

        userRepo.save(librarianUser);

        assertThat(librarianUser.getEmail()).isEqualTo("long@gmail.com");
    }

    @Test
    @Rollback(false)
    public void testDeleteUser() {
        User librarianUser = userRepo.findById(1).get();

        userRepo.deleteById(librarianUser.getId());
    }
}
