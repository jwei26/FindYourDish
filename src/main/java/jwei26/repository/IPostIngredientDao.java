package jwei26.repository;

import jwei26.model.PostIngredient;

public interface IPostIngredientDao {
    void addPostIngredient(PostIngredient postIngredient);
    void deletePostIngredient(Long postId, Long ingredientId);
}

