package jwei26.service;

import jwei26.model.Image;
import jwei26.model.Post;
import jwei26.repository.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageDao imageDao;

    public void addImageToPost(Post post, String imageUrl) {
        Image image = new Image();
        image.setPost(post);
        image.setImageUrl(imageUrl);
        imageDao.createImage(image);
    }

    public String getFirstImageUrlByPostId(Long postId) {
        List<Image> images = imageDao.getImagesByPostId(postId);
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.get(0).getImageUrl();
    }
}
