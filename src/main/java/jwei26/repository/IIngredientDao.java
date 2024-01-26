package jwei26.repository;

import jwei26.model.Ingredient;
import jwei26.model.Post;

import java.util.List;
import java.util.Set;

public interface IIngredientDao {
    void createIngredient(Ingredient ingredient);
    Ingredient getIngredientById(Long ingredientId);
    List<Ingredient> getAllIngredients();
    void updateIngredient(Ingredient ingredient);
    void deleteIngredient(Long ingredientId);
    Ingredient getIngredientByName(String ingredientName);
}

