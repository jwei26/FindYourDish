package jwei26.controller;

import io.jsonwebtoken.Claims;
import jwei26.model.Ingredient;
import jwei26.model.Post;
import jwei26.model.User;
import jwei26.service.*;
import jwei26.service.Exception.InvalidFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/post")
@RestController
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    JWTService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    IngredientService ingredientService;
    @Autowired
    FileService fileService;
    @Autowired
    ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        logger.info("new posts");
        return postService.getAllPosts();
    }
    @GetMapping("/homepage")
    public ResponseEntity<List<HomePostDto>> getPostsForHomepage() {
        List<HomePostDto> postDtos = postService.getAllPostsWithTitleFirstImage();
        return ResponseEntity.ok(postDtos);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(HttpServletRequest request,
                                        @RequestParam("files") List<MultipartFile> files,
                                        @RequestParam List<String> ingredients,
                                        @RequestParam("title") String title,
                                        @RequestParam("description") String description) {
        try {
            String token = request.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if (token == null || token.isEmpty()) {
                return ResponseEntity.badRequest().body("User must login in to create post");
            }
            Claims claims = jwtService.decryptToken(token);
            if (claims.getId() == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            User user = userService.getUserById(Long.valueOf(claims.getId()));

            Post post = new Post();
            post.setTitle(title);
            post.setDescription(description);

            postService.createPost(post, user);
            if (!ingredients.isEmpty()) {
                ingredientService.handleIngredients(post, ingredients);
            }

            if (!files.isEmpty()) {
                for(MultipartFile file : files) {
                    String imageUrl = fileService.upload(file);
                    imageService.addImageToPost(post, imageUrl);
                }
            }
            return ResponseEntity.ok("Post created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating post");
        }
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePost(HttpServletRequest request,@RequestParam("postId") Long postId) {
        try {
            String token = request.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if (token == null || token.isEmpty()) {
                return ResponseEntity.badRequest().body("User must login in to delete post");
            }
            Claims claims = jwtService.decryptToken(token);
            if (claims.getId() == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            User user = userService.getUserById(Long.valueOf(claims.getId()));
            postService.deletePost(postId, user);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting post");
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity<PostDetailDto> getPostDetails(@RequestParam("postId") Long postId) {
        try {
            PostDetailDto postDetails = postService.getPostDetails(postId);
            return ResponseEntity.ok(postDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
