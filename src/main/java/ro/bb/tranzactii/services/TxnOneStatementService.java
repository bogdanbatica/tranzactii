package ro.bb.tranzactii.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.TransactionOneStatementRepository;
import ro.bb.tranzactii.repositories.TransactionTemplateRepository;

import java.sql.SQLException;

@Service
public class TxnOneStatementService {

    @Autowired
    private TransactionOneStatementRepository transactionOneStatementRepository;



    @Transactional
    public void insertTransactionWithCommit(Transaction transaction) {
        try {
            transactionOneStatementRepository.insert(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}