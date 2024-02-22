CREATE TABLE post_images (
    image_id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
);