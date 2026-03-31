package SpringBoot.td5.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/td5_db");
        ds.setUser("postgres");
        ds.setPassword("Kokoo271107.");
        return ds;
    }
}