package jwei26.controller;

import jwei26.model.Post;
import jwei26.service.Exception.InvalidFileException;
import jwei26.service.FileService;
import jwei26.service.HomePostDto;
import jwei26.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping(value = "/file")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FileService fileService;
    @Autowired
    PostService postService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            String url = fileService.upload(file);
            logger.error("Successful upload file {} to S3", file.getOriginalFilename());
            return ResponseEntity.ok().body(url);
        } catch (InvalidFileException e) {
            logger.error("File type not support");
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            logger.error("Unable to upload file to AWS S3");
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
    }
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity getPostsForHomepage(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replaceAll("^(.*?) ", "");
        return ResponseEntity.ok().body(token);
    }
}
