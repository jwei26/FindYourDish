CREATE TABLE users (
                       user_id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE ingredients (
                             ingredient_id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(255) UNIQUE NOT NULL,
                             description TEXT
);


CREATE TABLE posts (
                       post_id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT NOT NULL,
                       post_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       user_id BIGINT NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE post_ingredients (
                                  post_id BIGINT NOT NULL,
                                  ingredient_id BIGINT NOT NULL,
                                  PRIMARY KEY (post_id, ingredient_id),
                                  FOREIGN KEY (post_id) REFERENCES posts(post_id),
                                  FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id)
);
