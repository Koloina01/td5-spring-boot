package SpringBoot.td5.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Dish {

    private Integer id;
    private Double price;
    private String name;
    private DishTypeEnum dishType;
    @JsonManagedReference
    private List<DishIngredient> dishIngredients;

    public Dish() {
    }

    public Dish(Integer id, Double price, String name, DishTypeEnum dishType, List<DishIngredient> dishIngredients) {
        this.id = id;
        this.price = price;
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
        if (dishIngredients == null) {
            return 0.0;
        }
        for (DishIngredient di : dishIngredients) {
            total += di.getIngredient().getPrice() * di.getQuantityRequired();
        }
        return total;
    }

    public Double getGrossMargin() {
        return price - getDishCost();
    }
}