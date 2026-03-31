package SpringBoot.td5.repository;

import SpringBoot.td5.entity.*;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {

    private final DataSource dataSource;

    public DishRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT id, name, dish_type, price FROM dish";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dish dish = new Dish();
                int dishId = rs.getInt("id");
                dish.setId(dishId);
                dish.setName(rs.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                dish.setPrice(rs.getObject("price") == null ? null : rs.getDouble("price"));

                dish.setDishIngredients(findDishIngredientsByDishId(dishId, conn));
                dishes.add(dish);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishes;
    }

    public Dish findDishById(Integer id) {
        String sql = "SELECT id, name, dish_type, price FROM dish WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Dish dish = new Dish();
                    dish.setId(rs.getInt("id"));
                    dish.setName(rs.getString("name"));
                    dish.setDishType(DishTypeEnum.valueOf(rs.getString("dish_type")));
                    dish.setPrice(rs.getObject("price") == null ? null : rs.getDouble("price"));

                    dish.setDishIngredients(findDishIngredientsByDishId(id, conn));
                    return dish;
                } else {
                    throw new RuntimeException("Dish not found " + id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DishIngredient> findDishIngredientsByDishId(Integer dishId, Connection conn) throws SQLException {
        List<DishIngredient> list = new ArrayList<>();
        String sql = """
                SELECT di.id_dish, di.id_ingredient, i.name, i.price, i.category, di.quantity_required, di.unit
                FROM DishIngredient di
                JOIN Ingredient i ON i.id = di.id_ingredient
                WHERE di.id_dish = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(rs.getInt("id_ingredient"));
                    ingredient.setName(rs.getString("name"));
                    ingredient.setPrice(rs.getDouble("price"));
                    ingredient.setCategory(CategoryEnum.valueOf(rs.getString("category")));

                    DishIngredient di = new DishIngredient();
                    di.setIngredient(ingredient);
                    di.setQuantityRequired(rs.getDouble("quantity_required"));
                    di.setUnit(UnitType.valueOf(rs.getString("unit")));

                    list.add(di);
                }
            }
        }

        return list;
    }

    public Dish updateDishIngredients(Integer dishId, List<DishIngredient> ingredients) {
        String deleteSql = "DELETE FROM DishIngredient WHERE id_dish = ?";
        String insertSql = "INSERT INTO DishIngredient(id_dish, id_ingredient, quantity_required, unit) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psDelete = conn.prepareStatement(deleteSql)) {
                psDelete.setInt(1, dishId);
                psDelete.executeUpdate();
            }

            try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                for (DishIngredient di : ingredients) {
                    psInsert.setInt(1, dishId);
                    psInsert.setInt(2, di.getIngredient().getId());
                    psInsert.setDouble(3, di.getQuantityRequired());
                    psInsert.setString(4, di.getUnit().name());
                    psInsert.addBatch();
                }
                psInsert.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return findDishById(dishId);
    }
}