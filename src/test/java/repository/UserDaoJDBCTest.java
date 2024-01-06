package repository;

import jwei26.model.User;
import jwei26.repository.UserDaoJDBC;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoJDBCTest {
    @Test
    public void getDepartmentsTest() {
        UserDaoJDBC userDao = new UserDaoJDBC();
        List<User> userList = userDao.getUsers();

        assertEquals(1, userList.size());
    }
}
