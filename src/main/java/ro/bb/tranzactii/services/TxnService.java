package ro.bb.tranzactii.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bb.tranzactii.model.TestResultHolder;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.CommonTxnRepository;
import ro.bb.tranzactii.util.Time;
import ro.bb.tranzactii.util.TransactionFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {

    private final static Logger logger = LoggerFactory.getLogger(TxnService.class);

    /** Numpber of threads we use in parallel to write the transactions.
     * Value chosen so to exceed the multithreading capabiities of the database,
     * but not to hinder the functioning of the Java application */
    static int THREAD_POOL_SIZE = 3;

    @Autowired
    private CommonTxnRepository commonTxnRepository;

    @Autowired
    private TxnTemplateService txnTemplateService;

    @Autowired
    private TxnMyBatisService txnMyBatisService;

    @Autowired
    private TxnOneStatementService txnOneStatementService;

    @Autowired
    private TxnTemplateOneStatementService txnTemplateOneStatementService;


    public String testJdbcTemplate(int batchSize) {
        return testService(txnTemplateService, batchSize);
    }

    public String testMyBatis(int batchSize) {
        return testService(txnMyBatisService, batchSize);
    }

    public String testOneStatement(int batchSize) {
        return testService(txnOneStatementService, batchSize);
    }

    public String testJdbcTemplate1(int batchSize) {
        return testService(txnTemplateOneStatementService, batchSize);
    }

    public String testService(TxnInsertService insertService, int batchSize) {
        prepareInitialContents(insertService, batchSize);
        long duration = testInsert(insertService, batchSize);
        return "Generation took " + duration + " ms";
    }

    public void prepareInitialContents(TxnInsertService insertService, int nbr) {
        logger.info("start initializing contents with " + nbr + " transactions - " + insertService.serviceLabel());
        commonTxnRepository.deleteAll();
        TransactionFactory factory = new TransactionFactory("EXISTING0000000000");

        Transaction[] transactions = new Transaction[nbr];
        for (int i = 0; i < nbr; i++) transactions[i] = factory.createTransaction(1 + i);
        insertTransactionBatch(insertService, transactions);

        logger.info("finished initializing contents");

        Time.waitMillis(1000);
    }

    public long testInsert(TxnInsertService insertService, int nbr) {
        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");
        logger.info("creating " + nbr + " current transactions - " + insertService.serviceLabel());
        int numerotationOffset = 1000000; if (nbr > 1000000) /* strange but let's accept it */ numerotationOffset = nbr;

        Transaction[] transactions = new Transaction[nbr];
        for (int i = 0; i < nbr; i++) transactions[i] = factory.createTransaction(numerotationOffset + 1 + i);
        logger.info("start writing " + nbr + " current transactions - " + insertService.serviceLabel());

        long start = System.currentTimeMillis();
        insertTransactionBatch(insertService, transactions);
        long end = System.currentTimeMillis();

        logger.info("finished writing current transactions");

        return (end-start);
    }

    /**
     * Insert a number of transactions in the database table, using a thread pool
     */
    public void insertTransactionBatch(TxnInsertService insertService, Transaction[] transactions) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (Transaction transaction : transactions) {
            executor.execute(() ->
                insertService.insertTransactionWithCommit(transaction)
            );
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS); // should end way faster
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * Compare the inserts using "one statement", Spring JDBC, and MyBatis
     * @param size size of the transactions batch to measure (same size will be used for the initial contents of the table)
     * @param runs number of times the test is run (for each access mode)
     * @return the recap of the test results in a readable format
     */
    public String testBare1stmtTemplateMybatis(int size, int runs) {
        return comparativeTest(size, runs, txnOneStatementService, txnTemplateService, txnMyBatisService);
    }

    /**
     * Compare the inserts using
     *  "one statement" Spring JDBC with JdbcTemplate
     *   vs default Spring JDBC with NamedParameterJdbcTemplate,
     * Hikari data sources
     * @param size size of the transactions batch to measure (same size will be used for the initial contents of the table)
     * @param runs number of times the test is run (for each access mode)
     * @return the recap of the test results in a readable format
     */
    public String testTemplate1stmtVsDefault(int size, int runs) {
        return comparativeTest(size, runs, txnTemplateService, txnTemplateOneStatementService);
    }

    /**
     * Compare the inserts using different services
     * @param size size of the transactions batch to measure (same size will be used for the initial contents of the table)
     * @param runs number of times the test is run (for each access mode)
     * @param insertServices services to use
     * @return the recap of the test results in a readable format
     */
    public String comparativeTest(int size, int runs, TxnInsertService... insertServices) {
        int nServices = insertServices.length;
        TestResultHolder[] resultHolders = new TestResultHolder[nServices];
        for (int iSrv = 0; iSrv < nServices; iSrv++) resultHolders[iSrv] = new TestResultHolder(insertServices[iSrv].serviceLabel());

        /* first, one warm-up run */
        logger.info("Warming up...");
        for (TxnInsertService insertService : insertServices) {
            prepareInitialContents(insertService, size);
            testInsert(insertService, size);
        }
        /* then, the runs to be measured */
        for (int iRun = 0; iRun < runs; iRun++) {
            logger.info("Comparing, iteration #" + (iRun+1) + "...");
            for (int iSrv = 0; iSrv < nServices; iSrv++) {
                prepareInitialContents(insertServices[iSrv], size);
                long duration = testInsert(insertServices[iSrv], size);
                resultHolders[iSrv].updateWithNewRun(duration);
            }
        }

        return formatOutput(resultHolders);
    }

    private String formatOutput(TestResultHolder[] resultHolders) {
        StringBuilder sb = new StringBuilder(255);
        for (TestResultHolder resultHolder : resultHolders) {
            sb.append(resultHolder.getServiceLabel())
                    .append(": average duration ").append(resultHolder.averageDuration())
                    .append(" ms, minimum ").append(resultHolder.getMinDuration())
                    .append(", maximum ").append(resultHolder.getMaxDuration())
                    .append("<br>\n");
        }
        return sb.toString();
    }
}