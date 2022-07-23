package com.nlu.libra.user;

import com.nlu.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserRepository userRepoRepo;

  @Autowired
  private RoleRepository roleRepository;

  public User create(User user) {
    if (!Objects.nonNull(userRepoRepo.findOneByEmail(user.getEmail()))){
      Random rnd = new Random();
      int number = rnd.nextInt(99999999);
      user.setIdentifier(String.format("%08d", number));
      user.setRole(roleRepository.findById(1).get());
      return userRepoRepo.save(user);
    }
    return null;
  }

  public User login(User user) {
    return userRepoRepo.findOneByEmailAndPassword(user.getEmail(), user.getPassword());
  }
}
