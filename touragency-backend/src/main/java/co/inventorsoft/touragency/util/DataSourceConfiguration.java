package co.inventorsoft.touragency.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass()
    .getName());

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        try {
            Class jdbcDriver = Class.forName("org.postgresql.Driver");
            dataSource.setDriverClassName(jdbcDriver.getName());
        } catch (ClassNotFoundException e) {
            logger.error("Unable to find PostgreSQL driver class");
        }

        dataSource.setUrl("jdbc:postgresql://localhost:5432/touragency");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }
}
