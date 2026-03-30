package SpringBoot.td5.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(System.getenv("JDBC_URL"))
                .username(System.getenv("USERNAME"))
                .password(System.getenv("PASSWORD"))
                .build();
    }
}
