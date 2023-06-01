package ro.bb.tranzactii.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.TransactionOneStatementRepository;

import java.sql.SQLException;

@Service
public class TxnOneStatementService implements TxnInsertService {

    @Autowired
    private TransactionOneStatementRepository transactionOneStatementRepository;


    @Override
    @Transactional
    public void insertTransactionWithCommit(Transaction transaction) {
        try {
            transactionOneStatementRepository.insert(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serviceLabel() {
        return "one-statement";
    }
}