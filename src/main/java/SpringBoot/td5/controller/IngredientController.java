package SpringBoot.td5.controller;

import SpringBoot.td5.entity.*;
import SpringBoot.td5.repository.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientRepository ingredientRepository;
    private final StockMovementRepository stockRepository;

    public IngredientController(IngredientRepository ingredientRepository,
                                StockMovementRepository stockRepository) {
        this.ingredientRepository = ingredientRepository;
        this.stockRepository = stockRepository;
    }

    @GetMapping
    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredient(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ingredientRepository.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getStock(
            @PathVariable Integer id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit
    ) {

        if (at == null || unit == null) {
            return ResponseEntity.badRequest()
                    .body("Missing parameters");
        }

        try {
            StockValue stock = stockRepository.getStockAt(
                    id,
                    Instant.parse(at),
                    UnitType.valueOf(unit)
            );

            return ResponseEntity.ok(stock);

        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }
    }
}