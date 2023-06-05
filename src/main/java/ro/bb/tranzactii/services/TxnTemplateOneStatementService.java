package ro.bb.tranzactii.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.TransactionOneStatementTemplateRepository;

import java.sql.SQLException;

@Service
public class TxnTemplateOneStatementService implements TxnInsertService {

    @Autowired
    private TransactionOneStatementTemplateRepository repository;


    @Override
    @Transactional
    public void insertTransactionWithCommit(Transaction transaction) {
        try {
            repository.insert(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serviceLabel() {
        return "Spring-JDBC 1stmt";
    }
}