package SpringBoot.td5.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/td5_db")
                .username("postgres")
                .password("Kokoo271107.")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}