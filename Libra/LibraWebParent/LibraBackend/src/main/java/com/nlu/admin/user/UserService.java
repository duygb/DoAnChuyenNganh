package com.nlu.admin.user;

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

@Service
@Transactional
public class UserService {

    public static final int USER_PER_PAGE = 5;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    public Role getRole() {
        return roleRepo.findById(2).get();
    }

    public Page<User> listByPage(int pageNum, String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE);

        if (keyword != null)
            return userRepo.findAll(keyword, pageable);

        return userRepo.findAll("", pageable);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);

        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID" + id);
        }

        userRepo.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepo.updateEnabledStatus(id, enabled);
    }
}
