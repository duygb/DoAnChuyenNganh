package com.nlu.libra.user;

import com.nlu.common.entity.User;
import com.nlu.common.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    public User create(User user) {
        if (!Objects.nonNull(userRepo.findOneByEmail(user.getEmail()))) {
            Random rnd = new Random();
            int number = rnd.nextInt(99999999);
            user.setIdentifier(String.format("%08d", number));
            user.setRole(roleRepository.findById(2).get());
            user.setEnabled(true);
            return userRepo.save(user);
        }
        return null;
    }

    public User login(User user) {
        return userRepo.findOneByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

}
