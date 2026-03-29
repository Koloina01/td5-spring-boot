package SpringBoot.td5.entity;

public class DishIngredient {

    private Long dishId;
    private Long ingredientId;
    private Double quantity;
    private String unit;

    public DishIngredient() {}

    public DishIngredient(Long dishId, Long ingredientId, Double quantity, String unit) {
        this.dishId = dishId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
