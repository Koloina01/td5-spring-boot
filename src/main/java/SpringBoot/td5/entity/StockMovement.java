package SpringBoot.td5.entity;

import java.time.Instant;

public class StockMovement {

    private Long id;
    private Long ingredientId;
    private Double quantity;
    private String unit;
    private Instant movementDate;

    public StockMovement() {}

    public StockMovement(Long id, Long ingredientId, Double quantity, String unit, Instant movementDate) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
        this.movementDate = movementDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Instant getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Instant movementDate) {
        this.movementDate = movementDate;
    }
}
