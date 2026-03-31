package SpringBoot.td5.repository;

import SpringBoot.td5.entity.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementRepository {

    private final DataSource dataSource;

    public StockMovementRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public StockValue getStockAt(Integer ingredientId, Instant at, UnitType unit) {
        List<StockMovement> movements = new ArrayList<>();
        String sql = "SELECT id, quantity, unit, type, creation_datetime FROM stock_movement WHERE id_ingredient = ? AND creation_datetime <= ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ingredientId);
            ps.setTimestamp(2, Timestamp.from(at));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockMovement movement = new StockMovement();
                    movement.setId(rs.getInt("id"));
                    movement.setValue(new StockValue(rs.getDouble("quantity"), UnitType.valueOf(rs.getString("unit"))));
                    movement.setType(MovementTypeEnum.valueOf(rs.getString("type")));
                    movement.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());

                    movements.add(movement);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        double total = 0;
        for (StockMovement m : movements) {
            if (m.getType() == MovementTypeEnum.IN) {
                total += m.getValue().getQuantity();
            } else {
                total -= m.getValue().getQuantity();
            }
        }

        return new StockValue(total, unit);
    }
}
