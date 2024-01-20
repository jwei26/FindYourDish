package jwei26.controller;

import jwei26.model.Post;
import jwei26.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5177")
@RequestMapping(value = "/post")
@RestController
public class PostController {
    @Autowired
    PostService postService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Post> getDepartment() {
        List<Post> posts = new ArrayList<>();
        logger.info("new posts");
        return posts;
    }
}
