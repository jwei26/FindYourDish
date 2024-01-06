package jwei26.model;

import javax.persistence.*;

@Entity
@Table(name = "post_ingredients")
@IdClass(PostIngredientId.class)
public class PostIngredient {
    @Id
    @Column(name = "post_id")
    private Long postId;

    @Id
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private Ingredient ingredient;

    public PostIngredient() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}


