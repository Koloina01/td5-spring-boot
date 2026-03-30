package SpringBoot.td5.entity;

import java.util.List;

public class Dish {

    private Integer id;
    private Double price;
    private String name;
    private DishTypeEnum dishType;
    private List<DishIngredient> dishIngredients;

    public Dish() {}

    public Dish(Integer id, String name, DishTypeEnum dishType, List<DishIngredient> dishIngredients) {
        this.id = id;
        this.name = name;
        this.dishType = dishType;
        this.dishIngredients = dishIngredients;
    }

    public Integer getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public DishTypeEnum getDishType() {
        return dishType;
    }

    public List<DishIngredient> getDishIngredients() {
        return dishIngredients;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishType(DishTypeEnum dishType) {
        this.dishType = dishType;
    }

    public void setDishIngredients(List<DishIngredient> dishIngredients) {
        this.dishIngredients = dishIngredients;
    }
    public Double getDishCost() {

        double total = 0;

        for (DishIngredient di : dishIngredients) {
            total += di.getIngredient().getPrice() * di.getQuantityRequired();
        }

        return total;
    }

    public Double getGrossMargin() {
        return price - getDishCost();
    }
}