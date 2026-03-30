package SpringBoot.td5.repository;

import SpringBoot.td5.entity.Ingredient;
import SpringBoot.td5.entity.CategoryEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, price, category FROM ingredient",
                (rs, rowNum) -> new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        CategoryEnum.valueOf(rs.getString("category").toUpperCase()),
                        rs.getDouble("price"),
                        null
                )
        );
    }


    public Ingredient findById(Integer id) {
        List<Ingredient> result = jdbcTemplate.query(
                "SELECT id, name, price, category FROM ingredient WHERE id = ?",
                (rs, rowNum) -> new Ingredient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        CategoryEnum.valueOf(rs.getString("category").toUpperCase()),
                        rs.getDouble("price"),
                        null
                ),
                id
        );

        if (result.isEmpty()) {
            throw new RuntimeException("Ingredient not found");
        }

        return result.get(0);
    }
}
