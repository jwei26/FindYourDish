package jwei26.repository;
import jwei26.model.Image;

import java.util.List;


public interface IImageDao {
    void createImage(Image image);
    Image getImageById(Long imageId);
    void updateImage(Image image);
    void deleteImage(Long imageId);
    List<Image> getImagesByPostId(Long postId);
    void deleteImagesByPostId(Long postId);
}
