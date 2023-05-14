package ro.bb.tranzactii.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.bb.tranzactii.model.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TransactionOneStatementRepository {

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
            ( ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            , ?
            )
                        """;

    DataSource dataSource;
    PreparedStatement preparedStatement;

    @Autowired
    public TransactionOneStatementRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            Connection connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Transaction txn) throws SQLException {
        /* TODO de exploatat in mod inteligent parametrii din instructiunea named-parameter
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
        */
        preparedStatement.setLong(1, txn.localId);
        preparedStatement.setString(2, txn.transactionId);
        preparedStatement.setLong(3, txn.checksum);
        preparedStatement.setString(4, txn.debtorAcct1);
        preparedStatement.setString(5, txn.debtorAcct2);
        preparedStatement.setString(6, txn.debtorAcct3);
        preparedStatement.setString(7, txn.debtorAcct4);
        preparedStatement.setString(8, txn.creditorAcct1);
        preparedStatement.setString(9, txn.creditorAcct2);
        preparedStatement.setString(10, txn.creditorAcct3);
        preparedStatement.setString(11, txn.creditorAcct4);
        preparedStatement.setBigDecimal(12, txn.amount);
        //preparedStatement.setTimestamp(13, new Timestamp(txn.operationTmstmp.toInstant().toEpochMilli()));
        preparedStatement.setObject(13, txn.operationTmstmp);  // s-o descurca?
        preparedStatement.setString(14, txn.additionalInfo);
        preparedStatement.executeUpdate();
    }
}
