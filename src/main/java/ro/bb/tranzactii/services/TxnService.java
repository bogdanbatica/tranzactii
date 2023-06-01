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

    @Autowired
    private CommonTxnRepository commonTxnRepository;

    @Autowired
    private TxnTemplateService txnTemplateService;

    @Autowired
    private TxnMyBatisService txnMyBatisService;

    @Autowired
    private TxnOneStatementService txnOneStatementService;


    public String testJdbcTemplate(int batchSize) {
        return testService(txnTemplateService, batchSize);
    }

    public String testMyBatis(int batchSize) {
        return testService(txnMyBatisService, batchSize);
    }

    public String testOneStatement(int batchSize) {
        return testService(txnOneStatementService, batchSize);
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

        insertTransactionBatch(insertService, factory, nbr, 0);

        logger.info("finished initializing contents");
        Time.waitMillis(1000);
    }

    public long testInsert(TxnInsertService insertService, int nbr) {
        logger.info("start writing " + nbr + " current transactions - " + insertService.serviceLabel());
        int numerotationOffset = 1000000; if (nbr > 1000000) /* strange but let's accept it */ numerotationOffset = nbr;
        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");

        long start = System.currentTimeMillis();
        insertTransactionBatch(insertService, factory, nbr, numerotationOffset);
        long end = System.currentTimeMillis();

        logger.info("finished writing current transactions");
        return (end-start);
    }

    /**
     * Insert a number of transactions in the database table, using the given factory to initialize the objects with some parallelism.
     * The access to the database is single-threaded and with commit on each row.
     * @param insertService
     * @param factory
     * @param nbr number of transactions to create
     * @param numerotationOffset used for the internal id; the first id to be inserted is numerotationOffset + 1
     */
    public void insertTransactionBatch(TxnInsertService insertService, TransactionFactory factory, int nbr, int numerotationOffset) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // don't need a lot of threads, just to ensure the creation of objects is not on the same thread as the database access

        for (int i = numerotationOffset + 1; i <= numerotationOffset + nbr; i++) {
            final int iTxn = i; // yeah, final...
            executor.submit(() -> {
                Transaction transaction = factory.createTransaction(iTxn);
                insertService.insertTransactionWithCommit(transaction); // synchronized
            });
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
    public String comparativeTest(int size, int runs) {
        TxnInsertService[] insertServices = {txnOneStatementService, txnTemplateService, txnMyBatisService};
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