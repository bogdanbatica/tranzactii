package ro.bb.tranzactii.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.CommonTxnRepository;
import ro.bb.tranzactii.util.Time;
import ro.bb.tranzactii.util.TransactionFactory;

import java.sql.SQLException;

@Service
public class TxnService {

    private static Logger logger = LoggerFactory.getLogger(TxnService.class);

    @Autowired
    private CommonTxnRepository commonTxnRepository;

    @Autowired
    private TxnTemplateService txnTemplateService;

    @Autowired
    private TxnOneStatementService txnOneStatementService;


    public void prepareInitialContents() {
        logger.info("start initializing contents");
        commonTxnRepository.deleteAll();
        TransactionFactory factory = new TransactionFactory("EXISTING0000000000");

        for (int i = 1; i <= 10000; i++) {
            Transaction transaction = factory.createTransaction(i);
            txnTemplateService.insertTransactionWithCommit(transaction);
        }
        logger.info("finished initializing contents");

        Time.waitMillis(1000);
    }

    public String testJdbcTemplate() {
        prepareInitialContents();

        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");
        logger.info("start writing current transactions");
        long start = System.currentTimeMillis();
        for (int i = 1000001; i < 1010000; i++) {
            Transaction transaction = factory.createTransaction(i);
            txnTemplateService.insertTransactionWithCommit(transaction);
        }
        long end = System.currentTimeMillis();
        logger.info("finished writing current transactions");

        return "Generation took " + (end-start) + " ms";
    }

    public String testOneStatement() {
        prepareInitialContents();

        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");
        logger.info("start writing current transactions");
        long start = System.currentTimeMillis();
        for (int i = 1000001; i < 1010000; i++) {
            Transaction transaction = factory.createTransaction(i);
            txnOneStatementService.insertTransactionWithCommit(transaction);
        }
        long end = System.currentTimeMillis();
        logger.info("finished writing current transactions");

        return "Generation took " + (end-start) + " ms";
    }


}