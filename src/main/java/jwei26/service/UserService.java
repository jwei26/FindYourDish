package jwei26.service;

import jwei26.model.User;
import jwei26.repository.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public User getUserByCredientials(String email, String password) {
        return userDao.getUserByCredientials(email, password);
    }
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }
    public void registerUser(User user) {
        validateRegistrationInfo(user);
        userDao.createUser(user);
    }


    private void validateRegistrationInfo(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User object must not be null");
        }
        if(Objects.isNull(user.getUsername()) || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if(Objects.isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }
        if(Objects.isNull(user.getEmail()) || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        if(user.getUsername().length() > 255) {
            throw new IllegalArgumentException("Username must not be less than 255 characters");
        }
        if(user.getPassword().length() > 255) {
            throw new IllegalArgumentException("Password must not be less than 255 characters");
        }
        if(user.getEmail().length() > 255) {
            throw new IllegalArgumentException("Email must not be less than 255 characters");
        }
        if(isUsernameTaken(user.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if(isEmailTaken(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
    }

    private boolean isUsernameTaken(String username) {
        return userDao.getUserByUsername(username) != null;
    }

    private boolean isEmailTaken(String email) {
        return userDao.getUserByEmail(email) != null;
    }

}
