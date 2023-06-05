package ro.bb.tranzactii.services;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.pool.HikariPool;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class JoacaDataSource {
    @Autowired
    ApplicationContext context;

    /* trying to ensure this bean is initialized at the end */
    @Autowired
    @Qualifier("dataSource")
    HikariDataSource dataSource;
    @Autowired
    @Qualifier("reuseStmtDataSource")
    HikariDataSource reuseStmtDataSource;
    @Autowired
    TxnOneStatementService txnOneStatementService;

    @PostConstruct
    void ceAvemNoiAici() throws Exception {
//        Map<String, DataSource> dataSourceBeans = context.getBeansOfType(DataSource.class);
//        dataSourceBeans.values().forEach(System.out::println);

        /* finish the initialisation of the data sources */
        dataSource.getConnection();
        reuseStmtDataSource.getConnection();

        HikariPool pool = (HikariPool) dataSource.getHikariPoolMXBean();
        DataSource unwrappedDataSource = pool.getUnwrappedDataSource();
        System.out.println(unwrappedDataSource.getClass().getCanonicalName());

        pool = (HikariPool) reuseStmtDataSource.getHikariPoolMXBean();
        unwrappedDataSource = pool.getUnwrappedDataSource();
        System.out.println(unwrappedDataSource.getClass().getCanonicalName());
    }

}
