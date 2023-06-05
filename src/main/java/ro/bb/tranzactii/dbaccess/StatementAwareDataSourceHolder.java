package ro.bb.tranzactii.dbaccess;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StatementAwareDataSourceHolder {

    private final TxnStatementAwareDataSource txnStatementAwareDataSource;

    private final HikariDataSource parentDataSource;

    public StatementAwareDataSourceHolder(
            @Value("${spring.datasource.reusestmt.url}") String jdbcUrl,
            @Value("${spring.datasource.reusestmt.username}") String username,
            @Value("${spring.datasource.reusestmt.password}") String password,
            @Qualifier("reuseStmtDataSource") HikariDataSource parentDataSource
    ) {
        this.parentDataSource = parentDataSource;
        this.txnStatementAwareDataSource = new TxnStatementAwareDataSource(jdbcUrl, username, password);
    }

    @PostConstruct
    public void integrateInParentDs() {
        parentDataSource.setDataSource(this.txnStatementAwareDataSource);
    }


}
