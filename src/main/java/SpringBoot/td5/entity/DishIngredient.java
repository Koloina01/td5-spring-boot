package SpringBoot.td5.entity;

public class DishIngredient {

   private int dishId;
    private Ingredient ingredient;
    private Double requiredQuantity;
    private String unit;

    public DishIngredient(int dishId, Ingredient ingredient,
                          Double requiredQuantity, String unit) {
        this.dishId = dishId;
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    public Double getRequiredQuantity() {
        return requiredQuantity;
    }
    public void setRequiredQuantity(Double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
