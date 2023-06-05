package ro.bb.tranzactii.repositories;

import com.zaxxer.hikari.pool.HikariProxyConnection;
import com.zaxxer.hikari.pool.ProxyConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.bb.tranzactii.dbaccess.TxnConnectionWithPreparedStatements;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.util.HikariDelegateFinder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TransactionOneStatementTemplateRepository {

    private static final String INSERT_TRANSACTION_SQL = TransactionOneStatementRepository.INSERT_TRANSACTION_SQL;

    private JdbcTemplate jdbcTemplate;

    public TransactionOneStatementTemplateRepository(@Qualifier("reuseStmtDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Transaction txn) throws SQLException {
        jdbcTemplate.update(con -> {
            TxnConnectionWithPreparedStatements delegateConnection
                    = (TxnConnectionWithPreparedStatements) HikariDelegateFinder.extractDelegate((ProxyConnection) con);
            PreparedStatement preparedStatement = delegateConnection.getReusableStatement();
            try {
                setParameters(preparedStatement, txn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return preparedStatement;
        });
    }


    private void setParameters(PreparedStatement preparedStatement, Transaction txn) throws SQLException {
        int iParam = 0;
        preparedStatement.setLong(++iParam, txn.localId);
        preparedStatement.setString(++iParam, txn.transactionId);
        preparedStatement.setLong(++iParam, txn.checksum);
        preparedStatement.setString(++iParam, txn.debtorAcct1);
        preparedStatement.setString(++iParam, txn.debtorAcct2);
        preparedStatement.setString(++iParam, txn.debtorAcct3);
        preparedStatement.setString(++iParam, txn.debtorAcct4);
        preparedStatement.setString(++iParam, txn.creditorAcct1);
        preparedStatement.setString(++iParam, txn.creditorAcct2);
        preparedStatement.setString(++iParam, txn.creditorAcct3);
        preparedStatement.setString(++iParam, txn.creditorAcct4);
        preparedStatement.setBigDecimal(++iParam, txn.amount);
        preparedStatement.setLong(++iParam, txn.numeric01);
        preparedStatement.setLong(++iParam, txn.numeric02);
        preparedStatement.setLong(++iParam, txn.numeric03);
        preparedStatement.setLong(++iParam, txn.numeric04);
        preparedStatement.setLong(++iParam, txn.numeric05);
        preparedStatement.setLong(++iParam, txn.numeric06);
        preparedStatement.setLong(++iParam, txn.numeric07);
        preparedStatement.setLong(++iParam, txn.numeric08);
        preparedStatement.setLong(++iParam, txn.numeric09);
        preparedStatement.setLong(++iParam, txn.numeric10);
        preparedStatement.setLong(++iParam, txn.numeric11);
        preparedStatement.setLong(++iParam, txn.numeric12);
        preparedStatement.setLong(++iParam, txn.numeric13);
        preparedStatement.setLong(++iParam, txn.numeric14);
        preparedStatement.setLong(++iParam, txn.numeric15);
        preparedStatement.setLong(++iParam, txn.numeric16);
        preparedStatement.setLong(++iParam, txn.numeric17);
        preparedStatement.setLong(++iParam, txn.numeric18);
        preparedStatement.setLong(++iParam, txn.numeric19);
        preparedStatement.setLong(++iParam, txn.numeric20);
        preparedStatement.setLong(++iParam, txn.numeric21);
        preparedStatement.setLong(++iParam, txn.numeric22);
        preparedStatement.setLong(++iParam, txn.numeric23);
        preparedStatement.setLong(++iParam, txn.numeric24);
        preparedStatement.setLong(++iParam, txn.numeric25);
        preparedStatement.setLong(++iParam, txn.numeric26);
        preparedStatement.setLong(++iParam, txn.numeric27);
        preparedStatement.setLong(++iParam, txn.numeric28);
        preparedStatement.setLong(++iParam, txn.numeric29);
        preparedStatement.setLong(++iParam, txn.numeric30);
        preparedStatement.setLong(++iParam, txn.numeric31);
        preparedStatement.setLong(++iParam, txn.numeric32);
        preparedStatement.setLong(++iParam, txn.numeric33);
        preparedStatement.setLong(++iParam, txn.numeric34);
        preparedStatement.setLong(++iParam, txn.numeric35);
        preparedStatement.setLong(++iParam, txn.numeric36);
        preparedStatement.setLong(++iParam, txn.numeric37);
        preparedStatement.setLong(++iParam, txn.numeric38);
        preparedStatement.setLong(++iParam, txn.numeric39);
        preparedStatement.setLong(++iParam, txn.numeric40);
        preparedStatement.setLong(++iParam, txn.numeric41);
        preparedStatement.setLong(++iParam, txn.numeric42);
        preparedStatement.setLong(++iParam, txn.numeric43);
        preparedStatement.setLong(++iParam, txn.numeric44);
        preparedStatement.setLong(++iParam, txn.numeric45);
        preparedStatement.setLong(++iParam, txn.numeric46);
        preparedStatement.setLong(++iParam, txn.numeric47);
        preparedStatement.setLong(++iParam, txn.numeric48);
        preparedStatement.setLong(++iParam, txn.numeric49);
        preparedStatement.setLong(++iParam, txn.numeric50);
        preparedStatement.setLong(++iParam, txn.numeric51);
        preparedStatement.setLong(++iParam, txn.numeric52);
        preparedStatement.setLong(++iParam, txn.numeric53);
        preparedStatement.setLong(++iParam, txn.numeric54);
        preparedStatement.setString(++iParam, txn.alphanr01);
        preparedStatement.setString(++iParam, txn.alphanr02);
        preparedStatement.setString(++iParam, txn.alphanr03);
        preparedStatement.setString(++iParam, txn.alphanr04);
        preparedStatement.setString(++iParam, txn.alphanr05);
        preparedStatement.setString(++iParam, txn.alphanr06);
        preparedStatement.setString(++iParam, txn.alphanr07);
        preparedStatement.setString(++iParam, txn.alphanr08);
        preparedStatement.setString(++iParam, txn.alphanr09);
        preparedStatement.setString(++iParam, txn.alphanr10);
        preparedStatement.setString(++iParam, txn.alphanr11);
        preparedStatement.setString(++iParam, txn.alphanr12);
        preparedStatement.setString(++iParam, txn.alphanr13);
        preparedStatement.setString(++iParam, txn.alphanr14);
        preparedStatement.setString(++iParam, txn.alphanr15);
        preparedStatement.setString(++iParam, txn.alphanr16);
        preparedStatement.setString(++iParam, txn.alphanr17);
        preparedStatement.setString(++iParam, txn.alphanr18);
        preparedStatement.setString(++iParam, txn.alphanr19);
        preparedStatement.setString(++iParam, txn.alphanr20);
        preparedStatement.setString(++iParam, txn.alphanr21);
        preparedStatement.setString(++iParam, txn.alphanr22);
        preparedStatement.setString(++iParam, txn.alphanr23);
        preparedStatement.setString(++iParam, txn.alphanr24);
        preparedStatement.setString(++iParam, txn.alphanr25);
        preparedStatement.setString(++iParam, txn.alphanr26);
        preparedStatement.setString(++iParam, txn.alphanr27);
        preparedStatement.setString(++iParam, txn.alphanr28);
        preparedStatement.setString(++iParam, txn.alphanr29);
        preparedStatement.setString(++iParam, txn.alphanr30);
        preparedStatement.setString(++iParam, txn.alphanr31);
        preparedStatement.setString(++iParam, txn.alphanr32);
        preparedStatement.setString(++iParam, txn.alphanr33);
        preparedStatement.setString(++iParam, txn.alphanr34);
        preparedStatement.setString(++iParam, txn.alphanr35);
        preparedStatement.setString(++iParam, txn.alphanr36);
        preparedStatement.setString(++iParam, txn.alphanr37);
        preparedStatement.setString(++iParam, txn.alphanr38);
        preparedStatement.setString(++iParam, txn.alphanr39);
        preparedStatement.setString(++iParam, txn.alphanr40);
        preparedStatement.setString(++iParam, txn.alphanr41);
        preparedStatement.setString(++iParam, txn.alphanr42);
        preparedStatement.setString(++iParam, txn.alphanr43);
        preparedStatement.setString(++iParam, txn.alphanr44);
        preparedStatement.setString(++iParam, txn.alphanr45);
        preparedStatement.setString(++iParam, txn.alphanr46);
        preparedStatement.setString(++iParam, txn.alphanr47);
        preparedStatement.setString(++iParam, txn.alphanr48);
        preparedStatement.setString(++iParam, txn.alphanr49);
        preparedStatement.setString(++iParam, txn.alphanr50);
        preparedStatement.setString(++iParam, txn.alphanr51);
        preparedStatement.setString(++iParam, txn.alphanr52);
        preparedStatement.setString(++iParam, txn.alphanr53);
        preparedStatement.setString(++iParam, txn.alphanr54);
        //preparedStatement.setTimestamp(13, new Timestamp(txn.operationTmstmp.toInstant().toEpochMilli()));
        preparedStatement.setObject(++iParam, txn.operationTmstmp);  // s-o descurca?
        preparedStatement.setString(++iParam, txn.additionalInfo);
    }
}
