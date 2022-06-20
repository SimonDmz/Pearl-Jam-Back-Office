package fr.insee.pearljam.api.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PgDataSourceConfig {

    @Value("${fr.insee.pearljam.persistence.database.driver}")
    String driver;
    @Value("${fr.insee.pearljam.persistence.database.user}")
    String username;
    @Value("${fr.insee.pearljam.persistence.database.password}")
    String password;
    @Value("${fr.insee.pearljam.persistence.database.host}")
    String host;
    @Value("${fr.insee.pearljam.persistence.database.port}")
    String port;
    @Value("${fr.insee.pearljam.persistence.database.schema}")
    String schema;

    String url = new StringBuilder().append("jdbc:postgresql://")
            .append(host)
            .append(":")
            .append(port)
            .append("/")
            .append(schema).toString();

    @Bean
    @ConditionalOnProperty(prefix = "fr.insee.pearljam.persistence.database", name = "driver", havingValue = "org.postgresql.Driver")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}