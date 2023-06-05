package ro.bb.tranzactii.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.bb.tranzactii.util.TransactionFactory;

import java.util.Properties;

@Configuration
public class Config {

    @Bean
    @Qualifier("normalDataSourceProperties")
    @ConfigurationProperties("spring.datasource.reusestmt")
    public DataSourceProperties normalDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("dataSource")
    public HikariDataSource dataSource(@Qualifier("normalDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("reuseStmtDataSourceProperties")
    @ConfigurationProperties("spring.datasource.reusestmt")
    public DataSourceProperties reusestmtDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("reuseStmtDataSource")
    public HikariDataSource reuseStmtDataSource(@Qualifier("reuseStmtDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("dbcp2DataSource")
    public BasicDataSource dbcp2DataSource(@Qualifier("normalDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(BasicDataSource.class).build();
    }

    @Bean
    public TransactionFactory alreadyExistingTransactionFactory() {
        return new TransactionFactory("EXISTING0000000000");
    }

    @Bean
    public TransactionFactory newArrivingTransactionFactory() {
        return new TransactionFactory("TRANSACTION0000000");
    }
}
