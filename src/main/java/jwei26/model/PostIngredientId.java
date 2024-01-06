package jwei26.model;

import java.io.Serializable;
import java.util.Objects;

public class PostIngredientId implements Serializable {
    private Long postId;
    private Long ingredientId;

    // 默认构造函数
    public PostIngredientId() {
    }

    // 全参数构造函数
    public PostIngredientId(Long postId, Long ingredientId) {
        this.postId = postId;
        this.ingredientId = ingredientId;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostIngredientId that = (PostIngredientId) o;
        return Objects.equals(postId, that.postId) && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, ingredientId);
    }
}
