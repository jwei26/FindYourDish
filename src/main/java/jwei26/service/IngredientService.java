package jwei26.service;

import jwei26.model.Ingredient;
import jwei26.model.Post;
import jwei26.repository.IngredientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    IngredientDao ingredientDao;
    @Autowired
    PostService postService;
    public void handleIngredients(Post post, List<String> ingredientNames) {
        long uniqueCount = ingredientNames.stream()
                .map(String::toLowerCase)
                .distinct()
                .count();
        if (uniqueCount != ingredientNames.size()) {
            postService.deletePost(post.getPostId(), post.getUser());
            throw new IllegalArgumentException("Ingredients must be unique");
        }
        for (String name : ingredientNames) {
            name = name.toLowerCase();
            Ingredient ingredient = ingredientDao.getIngredientByName(name);
            if (ingredient == null) {
                ingredient = new Ingredient();
                ingredient.setName(name);
                ingredientDao.createIngredient(ingredient);
            }
            postService.addIngredientToPost(post.getPostId(), ingredient.getIngredientId());
        }
    }

}
