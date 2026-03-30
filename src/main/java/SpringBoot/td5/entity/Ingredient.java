package SpringBoot.td5.entity;

import java.time.Instant;
import java.util.List;

public class Ingredient {

    private Integer id;
    private String name;
    private CategoryEnum category;
    private Double price;
    private List<StockMovement> stockMovementList;

    public Ingredient() {}

    public Ingredient(Integer id, String name, CategoryEnum category, Double price, List<StockMovement> stockMovementList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockMovementList = stockMovementList;
    }

    public Ingredient(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public List<StockMovement> getStockMovementList() {
        return stockMovementList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStockMovementList(List<StockMovement> stockMovementList) {
        this.stockMovementList = stockMovementList;
    }

    public StockValue getStockValueAt(Instant instant) {

        double total = 0.0;
        UnitType unit = UnitType.KG;

        if (stockMovementList == null) {
            return new StockValue(total, unit);
        }

        for (StockMovement movement : stockMovementList) {

            if (movement.getCreationDatetime().isAfter(instant)) {
                continue;
            }

            if (movement.getType() == MovementTypeEnum.IN) {
                total += movement.getValue().getQuantity();
            } else {
                total -= movement.getValue().getQuantity();
            }
        }

        return new StockValue(total, unit);
    }
}
