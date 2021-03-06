package com.nlu.admin.librarian;

import com.nlu.admin.role.RoleRepository;
import com.nlu.common.entity.Role;
import com.nlu.common.entity.User;
import com.nlu.common.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class LibrarianService {

    public static final int USER_PER_PAGE = 5;

    @Autowired
    private LibrarianRepository librarianRepo;

    @Autowired
    private RoleRepository roleRepo;

    public Role getRole() {
        return roleRepo.findById(3).get();
    }

    public User save(User user) {

        return librarianRepo.save(user);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = librarianRepo.findByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            if (userByEmail != null) return false;
        } else {
            if (userByEmail.getId() != id) return false;
        }

        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return librarianRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID" + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = librarianRepo.countById(id);

        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID" + id);
        }

        librarianRepo.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        librarianRepo.updateEnabledStatus(id, enabled);
    }

    public Page<User> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE);

        if (keyword != null)
            return librarianRepo.findAll(keyword, pageable);

        return librarianRepo.findAll("",pageable);
    }
}
