package jwei26.repository;

import jwei26.model.Ingredient;

import java.util.List;

public interface IIngredientDao {
    void createIngredient(Ingredient ingredient);
    Ingredient getIngredientById(Long ingredientId);
    List<Ingredient> getAllIngredients();
    void updateIngredient(Ingredient ingredient);
    void deleteIngredient(Long ingredientId);
}

