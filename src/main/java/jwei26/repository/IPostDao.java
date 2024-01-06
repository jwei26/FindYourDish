package jwei26.repository;

import jwei26.model.Post;

import java.util.List;

public interface IPostDao {
    void createPost(Post post);
    Post getPostById(Long postId);
    List<Post> getAllPosts();
    void updatePost(Post post);
    void deletePost(Long postId);
}

