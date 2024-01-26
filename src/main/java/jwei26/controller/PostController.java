package jwei26.controller;

import io.jsonwebtoken.Claims;
import jwei26.model.Ingredient;
import jwei26.model.Post;
import jwei26.model.User;
import jwei26.service.IngredientService;
import jwei26.service.JWTService;
import jwei26.service.PostService;
import jwei26.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5177")
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
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        logger.info("new posts");
        return postService.getAllPosts();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createPost(HttpServletRequest request, @RequestBody Post post, @RequestParam List<String> ingredients) {
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

            postService.createPost(post, user);
            ingredientService.handleIngredients(post, ingredients);

            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating post");
        }
    }

}
