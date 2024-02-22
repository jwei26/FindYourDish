package jwei26.service;

import jwei26.model.Ingredient;
import jwei26.model.Post;
import jwei26.model.User;
import jwei26.model.Image;
import jwei26.repository.ImageDao;
import jwei26.repository.IngredientDao;
import jwei26.repository.PostDao;
import jwei26.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    PostDao postDao;
    @Autowired
    IngredientDao ingredientDao;
    @Autowired
    ImageService imageService;
    @Autowired
    ImageDao imageDao;

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

    public void deletePost(Long postId, User user) {
        if (postId == null) {
            throw new IllegalArgumentException("Post ID must not be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Post post = postDao.getPostById(postId);
        if (post != null) {
            if(!user.getUserId().equals(post.getUser().getUserId())) {
                throw new IllegalArgumentException("User is not authorized to delete this post");
            }
            post.getIngredients().clear();
            postDao.updatePost(post);
            imageDao.deleteImagesByPostId(postId);
            postDao.deletePost(postId);
        } else {
            throw new IllegalArgumentException("Post not found");
        }
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

    public PostDetailDto getPostDetails(Long postId) {
        Post post = postDao.getPostById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Post not found");
        }

        PostDetailDto dto = new PostDetailDto();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());

        // 获取食材名称
        List<String> ingredientNames = post.getIngredients().stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
        dto.setIngredientNames(ingredientNames);

        // 获取图片 URL
        List<String> imageUrls = imageDao.getImagesByPostId(postId).stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
        dto.setImageUrls(imageUrls);

        return dto;
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

    public List<HomePostDto> getAllPostsWithTitleFirstImage() {
        List<Post> posts = postDao.getAllPosts();
        List<HomePostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            HomePostDto dto = new HomePostDto();
            dto.setPostId(post.getPostId());
            dto.setTitle(post.getTitle());
            dto.setImageUrl(imageService.getFirstImageUrlByPostId(post.getPostId()));
            postDtos.add(dto);
        }
        return postDtos;
    }

}

