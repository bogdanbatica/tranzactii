package ro.bb.tranzactii.dbaccess;

import ro.bb.tranzactii.repositories.TransactionOneStatementRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class TxnConnectionWithPreparedStatements extends ConnectionWithPreparedStatements {

    public TxnConnectionWithPreparedStatements(Connection internalConnection) throws SQLException {
        super(internalConnection);
    }

    @Override
    protected void prepareReusableStatementSql() {
        reusableStatementSql = TransactionOneStatementRepository.INSERT_TRANSACTION_SQL;
    }
}
