package SpringBoot.td5.controller;

import SpringBoot.td5.entity.Dish;
import SpringBoot.td5.entity.DishIngredient;
import SpringBoot.td5.repository.DishRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody(required = false) List<DishIngredient> ingredients
    ) {

        if (ingredients == null) {
            return ResponseEntity.badRequest()
                    .body("Request body is required");
        }

        try {
            return ResponseEntity.ok(
                    dishRepository.updateDishIngredients(id, ingredients)
            );
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body("Dish.id=" + id + " is not found");
        }
    }
}