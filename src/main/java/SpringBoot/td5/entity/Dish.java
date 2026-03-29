package SpringBoot.td5.entity;

import java.util.List;

public class Dish {

    private int id;
    private String name;
    private Double sellingPrice;
    private List<DishIngredient> dishIngredients;

    public Dish() {}

    public Dish(int id, String name, Double sellingPrice, List<DishIngredient> dishIngredients) {
        this.id = id;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.dishIngredients = dishIngredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public List<DishIngredient> getDishIngredients() {
        return dishIngredients;
    }
    public void setDishIngredients(List<DishIngredient> dishIngredients) {
        this.dishIngredients = dishIngredients;
    }
}