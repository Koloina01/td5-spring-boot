package SpringBoot.td5.repository;

import SpringBoot.td5.entity.CategoryEnum;
import SpringBoot.td5.entity.Dish;
import SpringBoot.td5.entity.DishIngredient;
import SpringBoot.td5.entity.DishTypeEnum;
import SpringBoot.td5.entity.Ingredient;
import SpringBoot.td5.entity.UnitType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DishRepository {

    private final JdbcTemplate jdbcTemplate;

    public DishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Dish> findAll() {
        return jdbcTemplate.query(
                """
                select dish.id as dish_id, dish.name as dish_name, dish_type, dish.price as dish_price
                from dish
                """,
                (rs, rowNum) -> {
                    Dish dish = new Dish();
                    dish.setId(rs.getInt("dish_id"));
                    dish.setName(rs.getString("dish_name"));
                    dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                    dish.setPrice(rs.getObject("dish_price") == null
                            ? null
                            : rs.getDouble("dish_price"));

                    dish.setDishIngredients(findDishIngredientById(rs.getInt("dish_id")));
                    return dish;
                }
        );
    }

    public Dish findDishById(Integer id) {
        List<Dish> result = jdbcTemplate.query(
                """
                select dish.id as dish_id, dish.name as dish_name, dish_type, dish.price as dish_price
                from dish
                where dish.id = ?
                """,
                (rs, rowNum) -> {
                    Dish dish = new Dish();
                    dish.setId(rs.getInt("dish_id"));
                    dish.setName(rs.getString("dish_name"));
                    dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                    dish.setPrice(rs.getObject("dish_price") == null
                            ? null
                            : rs.getDouble("dish_price"));

                    dish.setDishIngredients(findDishIngredientById(id));
                    return dish;
                },
                id
        );

        if (result.isEmpty()) {
            throw new RuntimeException("Dish not found " + id);
        }

        return result.get(0);
    }


    private List<DishIngredient> findDishIngredientById(Integer idDish) {

        return jdbcTemplate.query(
                """
                SELECT id_ingredient,
                       ingredient.id,
                       ingredient.name,
                       ingredient.price,
                       ingredient.category,
                       DishIngredient.quantity_required,
                       DishIngredient.unit
                FROM DishIngredient
                JOIN Dish ON Dish.id = id_dish
                JOIN Ingredient ON Ingredient.id = id_ingredient
                WHERE id_dish = ?
                """,
                (rs, rowNum) -> {

                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(rs.getInt("id_ingredient"));
                    ingredient.setName(rs.getString("name"));
                    ingredient.setPrice(rs.getDouble("price"));
                    ingredient.setCategory(CategoryEnum.valueOf(rs.getString("category")));

                    DishIngredient di = new DishIngredient();
                    di.setIngredient(ingredient);
                    di.setQuantityRequired(rs.getDouble("quantity_required"));
                    di.setUnit(UnitType.valueOf(rs.getString("unit")));

                    return di;
                },
                idDish
        );
    }

   
    public Dish updateDishIngredients(Integer dishId, List<DishIngredient> ingredients) {

        jdbcTemplate.update(
                "DELETE FROM DishIngredient WHERE id_dish = ?",
                dishId
        );

        for (DishIngredient di : ingredients) {
            jdbcTemplate.update(
                    """
                    INSERT INTO DishIngredient(id_dish, id_ingredient, quantity_required, unit)
                    VALUES (?, ?, ?, ?)
                    """,
                    dishId,
                    di.getIngredient().getId(),
                    di.getQuantityRequired(),
                    di.getUnit().name()
            );
        }

        return findDishById(dishId);
    }
}