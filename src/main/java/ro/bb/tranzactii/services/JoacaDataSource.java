package ro.bb.tranzactii.services;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import com.zaxxer.hikari.pool.HikariPool;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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
    HikariDataSource dataSource;
    @Autowired
    TxnOneStatementService txnOneStatementService;

//    @PostConstruct
    void ceAvemNoiAici() {
        Map<String, DataSource> dataSourceBeans = context.getBeansOfType(DataSource.class);
        dataSourceBeans.values().forEach(System.out::println);
        HikariDataSource dataSource = (HikariDataSource) dataSourceBeans.get("dataSource");
        HikariPool pool = (HikariPool) dataSource.getHikariPoolMXBean();
        DataSource unwrappedDataSource = pool.getUnwrappedDataSource();
        System.out.println(unwrappedDataSource.getClass().getCanonicalName());
    }

}
