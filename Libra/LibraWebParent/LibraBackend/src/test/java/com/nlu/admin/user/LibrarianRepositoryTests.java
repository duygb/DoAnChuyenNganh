package com.nlu.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.nlu.admin.librarian.LibrarianRepository;
import com.nlu.common.entity.Role;
import com.nlu.common.entity.User;
import org.junit.jupiter.api.Order;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class LibrarianRepositoryTests {
    @Autowired
    private LibrarianRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(1)
    public void testCreateNewUserWithOneRole() {
        Role admin = entityManager.find(Role.class, 1);

        User user = new User();
        user.setEmail("admin1");
        user.setPassword("admin");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEnabled(true);
        user.setRole(admin);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void testGetUserById() {
        User user = userRepository.findById(1).get();

        assertThat(user).isNotNull();
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        User user = userRepository.findById(1).get();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEnabled(true);

        User savedUser = userRepository.save(user);
        assertThat(savedUser.isEnabled()).isTrue();
    }


    @Test
    @Order(4)
    @Rollback(true)
    public void testDeleteUser() {
        User user = userRepository.findById(1).get();

        userRepository.deleteById(user.getId());
    }

    @Test
    @Order(5)
    public void testGetUserByEmail() {
        String email = "admin1";
        User user = userRepository.findByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    @Order(6)
    public void testCountById() {
        Integer id = 1;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    @Order(7)
    public void testUpdateUserEnabledStatus() {
        Integer id = 2;

        userRepository.updateEnabledStatus(id, true);
    }

    @Test
    @Order(8)
    public void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isGreaterThan(0);
    }

    @Test
    @Order(9)
    public void testSearchUser() {
        String keyword = "admin";

        int pageNumber = 0;
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isGreaterThan(0);
    }
}
