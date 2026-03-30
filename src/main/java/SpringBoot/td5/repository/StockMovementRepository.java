package SpringBoot.td5.repository;

import SpringBoot.td5.entity.StockValue;
import SpringBoot.td5.entity.MovementTypeEnum;
import SpringBoot.td5.entity.UnitType;
import SpringBoot.td5.entity.StockMovement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StockMovementRepository {

    private final JdbcTemplate jdbcTemplate;

    public StockMovementRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StockValue getStockAt(Integer ingredientId, Instant at, UnitType unit) {

        List<StockMovement> movements = jdbcTemplate.query(
                """
                SELECT id, quantity, unit, type, creation_datetime
                FROM stock_movement
                WHERE id_ingredient = ? AND creation_datetime <= ?
                """,
                (rs, rowNum) -> new StockMovement(
                        rs.getInt("id"),
                        new StockValue(
                                rs.getDouble("quantity"),
                                UnitType.valueOf(rs.getString("unit"))
                        ),
                        MovementTypeEnum.valueOf(rs.getString("type")),
                        rs.getTimestamp("creation_datetime").toInstant()
                ),
                ingredientId,
                Timestamp.from(at)
        );

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
