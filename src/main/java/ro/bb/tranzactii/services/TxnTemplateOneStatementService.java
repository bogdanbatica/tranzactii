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
        } catch (SQLException e) { // shoudn't happen, but practically it did, let's halt right now to investigate
            e.printStackTrace();
            System.out.println(transaction);
            System.exit(1);
        }
    }

    @Override
    public String serviceLabel() {
        return "Spring-JDBC 1stmt";
    }
}