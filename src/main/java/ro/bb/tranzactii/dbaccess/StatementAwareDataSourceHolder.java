package ro.bb.tranzactii.dbaccess;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * This class is meant to be used om a Hikari pool, instead of Hikari's DriverDataSource.
 * Hence, heavily inspired from that DriverDataSource
 */
@Component
public class StatementAwareDataSourceHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatementAwareDataSourceHolder.class);

    StatementAwareDataSource statementAwareDataSource;


    private final DataSource parentDataSource;

    public StatementAwareDataSourceHolder(
            @Value("${spring.datasource.url}") String jdbcUrl,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Autowired DataSource parentDataSource // trying to force the creation of this bean here after the creation of the parent DS
    ) {
        this.parentDataSource = parentDataSource;
        this.statementAwareDataSource = new StatementAwareDataSource(jdbcUrl, username, password);
    }

    @PostConstruct
    public void integrateInParentDs() {
        ((HikariDataSource)parentDataSource).setDataSource(this.statementAwareDataSource);
    }


}
