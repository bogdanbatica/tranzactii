package ro.bb.tranzactii.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.CommonTxnRepository;
import ro.bb.tranzactii.util.Time;
import ro.bb.tranzactii.util.TransactionFactory;

@Service
public class TxnService {

    private final static Logger logger = LoggerFactory.getLogger(TxnService.class);

    @Autowired
    private CommonTxnRepository commonTxnRepository;

    @Autowired
    private TxnTemplateService txnTemplateService;

    @Autowired
    private TxnMyBatisService txnMyBatisService;

    @Autowired
    private TxnOneStatementService txnOneStatementService;


    public String testJdbcTemplate() {
        prepareInitialContents(txnTemplateService);
        return testInsert(txnTemplateService);
    }

    public String testMyBatis() {
        prepareInitialContents(txnMyBatisService);
        return testInsert(txnMyBatisService);
    }

    public String testOneStatement() {
        prepareInitialContents(txnOneStatementService);
        return testInsert(txnOneStatementService);
    }

    public void prepareInitialContents(TxnInsertService insertService) {
        logger.info("start initializing contents - " + insertService.getClass().getSimpleName());
        commonTxnRepository.deleteAll();
        TransactionFactory factory = new TransactionFactory("EXISTING0000000000");

        for (int i = 1; i <= 10000; i++) {
            Transaction transaction = factory.createTransaction(i);
            insertService.insertTransactionWithCommit(transaction);
        }
        logger.info("finished initializing contents");

        Time.waitMillis(1000);
    }

    public String testInsert(TxnInsertService insertService) {
        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");
        logger.info("start writing current transactions - " + insertService.getClass().getSimpleName());
        long start = System.currentTimeMillis();

        for (int i = 1000001; i < 1010000; i++) {
            Transaction transaction = factory.createTransaction(i);
            insertService.insertTransactionWithCommit(transaction);
        }

        long end = System.currentTimeMillis();
        logger.info("finished writing current transactions");

        return "Generation took " + (end-start) + " ms";
    }


}