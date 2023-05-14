package ro.bb.tranzactii.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.bb.tranzactii.model.Transaction;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CommonTxnRepository {

    private static final String DELETE_TRANSACTIONS_SQL = "DELETE FROM tranzactii";

    DataSource dataSource;
    NamedParameterJdbcTemplate template;

    @Autowired
    public CommonTxnRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public void deleteAll() {
        template.update(DELETE_TRANSACTIONS_SQL, Collections.emptyMap());
    }
}
