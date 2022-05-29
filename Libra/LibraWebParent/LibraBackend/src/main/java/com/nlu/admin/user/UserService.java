package com.nlu.admin.user;

import com.nlu.common.entity.Role;
import com.nlu.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

  public static final int USER_PER_PAGE = 1;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private RoleRepository roleRepo;

  public List<User> listAll() {
    return (List<User>) userRepo.findAll();
  }

  public List<Role> listRoles() {
    return (List<Role>) roleRepo.findAll();
  }

  public User save(User user) {
    return userRepo.save(user);
  }

  public void updateUserEnabledStatus(Integer id, boolean enabled) {
    userRepo.updateEnabledStatus(id, enabled);
  }

  public void delete(Integer id) throws UserNotFoundException {
    Long countById = userRepo.countById(id);

    if (countById == null || countById == 0) {
      throw new UserNotFoundException("Could not find any user with ID " + id);
    }

    userRepo.deleteById(id);
  }

  public Page<User> listByPage(int pageNum) {
    /*
     * chỉ định trang và giới hạn số lượng của 1 trang
     * tại sao pageNum - 1 vì page nó hiểu trang đầu là 0
     */

    Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE);

    return userRepo.findAll(pageable);
  }
}
