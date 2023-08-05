package model.order;

import java.util.List;

public class OrderRequestIngredients {
    private List<String> ingredients;

    public OrderRequestIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}