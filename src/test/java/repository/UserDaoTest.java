package repository;

import jwei26.model.User;
import jwei26.repository.IUserDao;
import jwei26.repository.UserDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {
    @Test
    public void userTest() {
        IUserDao userDao = new UserDao();
        List<User> userList = userDao.getUsers();

        assertEquals(1, userList.size());

        List<User> userList2 = new ArrayList<>();
        userList2.add(userDao.getUserById(1L));
        assertEquals(1, userList.size());

        User user = new User();
        user.setEmail("user1@test.com");
        user.setPassword("1234");
        user.setUsername("user1");
        userDao.createUser(user);

        userList = userDao.getUsers();
        assertEquals(2, userList.size());

        user.setUsername("anotherUser");
        userDao.updateUser(user);

        userDao.deleteUser(user.getUserId());

        userList = userDao.getUsers();
        assertEquals(1, userList.size());
    }
}
