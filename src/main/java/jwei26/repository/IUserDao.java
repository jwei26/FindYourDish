package jwei26.repository;

import jwei26.model.User;

import java.util.List;

public interface IUserDao {
    User getUserByCredientials(String email, String password);
    void createUser(User user);
    User getUserById(Long userId);
    List<User> getUsers();
    void updateUser(User user);
    void deleteUser(Long userId);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
}

