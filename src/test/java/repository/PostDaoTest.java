package repository;

import jwei26.model.Post;
import jwei26.model.User;
import jwei26.repository.IPostDao;
import jwei26.repository.IUserDao;
import jwei26.repository.PostDao;
import jwei26.repository.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PostDaoTest {
    IPostDao postDao;
    IUserDao userDao;
    Post post;
    User user;

    @Before
    public void setUp() {
        postDao = new PostDao();
        userDao = new UserDao();

        user = new User();
        user.setEmail("user1@test.com");
        user.setPassword("1234");
        user.setUsername("user1");
        userDao.createUser(user);

        post = new Post();
        post.setTitle("Test Post");
        post.setDescription("This is a test post.");
        post.setUser(user);

        postDao.createPost(post);
    }

    @After
    public void tearDown() {
        userDao.deleteUser(user.getUserId());
    }

    @Test
    public void postDaoTest() {
        Post retrievedPost = postDao.getPostById(post.getPostId());
        assertNotNull(retrievedPost);

        List<Post> postList = postDao.getAllPosts();
        assertEquals(1, postList.size());

        post.setTitle("Update Post");
        postDao.updatePost(post);

        retrievedPost = postDao.getPostById(post.getPostId());
        assertEquals("Update Post", retrievedPost.getTitle());
    }
}
