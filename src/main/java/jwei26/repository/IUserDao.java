package jwei26.repository;

import jwei26.model.User;

import java.util.List;

public interface IUserDao {
    void createUser(User user);
    User getUserById(Long userId);
    List<User> getUsers();
    void updateUser(User user);
    void deleteUser(Long userId);
}

