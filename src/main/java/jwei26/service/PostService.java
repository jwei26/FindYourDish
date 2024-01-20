package jwei26.service;

import jwei26.model.Ingredient;
import jwei26.model.Post;
import jwei26.model.User;
import jwei26.repository.IngredientDao;
import jwei26.repository.PostDao;
import jwei26.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostDao postDao;
    @Autowired
    IngredientDao ingredientDao;

    public PostService(PostDao postDao, IngredientDao ingredientDao) {
        this.postDao = postDao;
        this.ingredientDao = ingredientDao;
    }

    public void createPost(Post post, User user) {
        if (post == null || post.getTitle() == null || post.getDescription() == null) {
            throw new IllegalArgumentException("Post, title, and description must not be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        post.setUser(user);
        postDao.createPost(post);
    }

    public Post getPostById(Long postId) {
        if (postId == null) {
            throw new IllegalArgumentException("Post ID must not be null");
        }
        return postDao.getPostById(postId);
    }

    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }

    public void updatePost(Post post) {
        if (post == null || post.getPostId() == null || postDao.getPostById(post.getPostId()) == null) {
            throw new IllegalArgumentException("Post must exist and have a valid ID");
        }
        postDao.updatePost(post);
    }

    public void deletePost(Long postId) {
        if (postId == null || postDao.getPostById(postId) == null) {
            throw new IllegalArgumentException("Post ID must be valid and exist");
        }
        postDao.deletePost(postId);
    }

    public void addIngredientToPost(Long postId, Long ingredientId) {
        if (postId == null || ingredientId == null) {
            throw new IllegalArgumentException("Post ID and Ingredient ID must not be null");
        }
        Ingredient ingredient = ingredientDao.getIngredientById(ingredientId);
        if (ingredient != null) {
            Post post = postDao.getPostById(postId);
            if (post != null) {
                post.getIngredients().add(ingredient);
                postDao.updatePost(post);
            }
        }
    }

    public void removeIngredientFromPost(Long postId, Long ingredientId) {
        if (postId == null || ingredientId == null) {
            throw new IllegalArgumentException("Post ID and Ingredient ID must not be null");
        }
        Ingredient ingredient = ingredientDao.getIngredientById(ingredientId);
        if (ingredient != null) {
            Post post = postDao.getPostById(postId);
            if (post != null) {
                post.getIngredients().remove(ingredient);
                postDao.updatePost(post);
            }
        }
    }
}

