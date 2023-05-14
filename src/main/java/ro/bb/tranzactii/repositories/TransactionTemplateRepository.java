package ro.bb.tranzactii.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.bb.tranzactii.model.Transaction;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TransactionTemplateRepository {

    private static final String INSERT_TRANSACTION_SQL = """
               INSERT INTO tranzactii
               ( local_id
               , transaction_id
               , checksum
               , debtor_acct1
               , debtor_acct2
               , debtor_acct3
               , debtor_acct4
               , creditor_acct1
               , creditor_acct2
               , creditor_acct3
               , creditor_acct4
               , amount
               , operation_tmstmp
               , additional_info
               )
               VALUES
               ( :localId
               , :transactionId
               , :checksum
               , :debtorAcct1
               , :debtorAcct2
               , :debtorAcct3
               , :debtorAcct4
               , :creditorAcct1
               , :creditorAcct2
               , :creditorAcct3
               , :creditorAcct4
               , :amount
               , :operationTmstmp
               , :additionalInfo
               )
            """;

    DataSource dataSource;
    NamedParameterJdbcTemplate template;

    @Autowired
    public TransactionTemplateRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    public void insert(Transaction txn) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("localId", txn.localId);
        paramMap.put("transactionId", txn.transactionId);
        paramMap.put("checksum", txn.checksum);
        paramMap.put("debtorAcct1", txn.debtorAcct1);
        paramMap.put("debtorAcct2", txn.debtorAcct2);
        paramMap.put("debtorAcct3", txn.debtorAcct3);
        paramMap.put("debtorAcct4", txn.debtorAcct4);
        paramMap.put("creditorAcct1", txn.creditorAcct1);
        paramMap.put("creditorAcct2", txn.creditorAcct2);
        paramMap.put("creditorAcct3", txn.creditorAcct3);
        paramMap.put("creditorAcct4", txn.creditorAcct4);
        paramMap.put("amount", txn.amount);
        paramMap.put("operationTmstmp", txn.operationTmstmp);
        paramMap.put("additionalInfo", txn.additionalInfo);
        template.update(INSERT_TRANSACTION_SQL, paramMap);
    }
}
