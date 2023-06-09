package ro.bb.tranzactii.services;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bb.tranzactii.model.TestResultHolder;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.CommonTxnRepository;
import ro.bb.tranzactii.util.Time;
import ro.bb.tranzactii.util.TransactionFactory;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {

    private final static Logger logger = LoggerFactory.getLogger(TxnService.class);

    /** Numpber of threads we use in parallel to write the transactions, if not specified otherwise */
    public static int DEFAULT_THREAD_POOL_SIZE = 1;

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

    /** Regroups, for easy reference, all the modalities we have to write our transactions */
    public Map<Character, TxnInsertService> insertServiceMap;

    @PostConstruct
    public void creareServiceMap() {
        insertServiceMap = new TreeMap<>();

        /* "default behaviour" services */
        insertServiceMap.put('A', txnTemplateService);
        insertServiceMap.put('B', txnMyBatisService);

        /* one-statement services */
        insertServiceMap.put('Z', txnOneStatementService);
        insertServiceMap.put('Y', txnTemplateOneStatementService);
    }

    public String formatServiceMap() {
        StringBuilder sb = new StringBuilder(255);
        insertServiceMap.forEach((k, v) -> {
            sb.append(k).append(": ").append(v.serviceLabel())
                    .append(", &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;class ")
                    .append(v.getClass().getSimpleName()).append("<br>");
        });
        return sb.toString();
    }

    /* simple duration tests */

    public String testJdbcTemplate(int batchSize) {
        return testService(txnTemplateService, batchSize, batchSize);
    }

    public String testMyBatis(int batchSize) {
        return testService(txnMyBatisService, batchSize, batchSize);
    }

    public String testOneStatement(int batchSize) {
        return testService(txnOneStatementService, batchSize, batchSize);
    }

    public String testJdbcTemplate1(int batchSize) {
        return testService(txnTemplateOneStatementService, batchSize, batchSize);
    }

    public String testService(TxnInsertService insertService, int initSize, int testSize) {
        prepareInitialContents(insertService, initSize, DEFAULT_THREAD_POOL_SIZE);
        long duration = testInsert(insertService, testSize, DEFAULT_THREAD_POOL_SIZE);
        return "Generation took " + duration + " ms";
    }

    /** Single run test on a service identified by its service key */
    public String testService(char serviceKey, int initSize, int testSize, int threads) {
        TxnInsertService insertService = insertServiceMap.get(serviceKey);
        if (insertService == null) return "ERROR: Unknown service key " + serviceKey;

        prepareInitialContents(insertService, initSize, threads);
        long duration = testInsert(insertService, testSize, threads);
        return insertService.serviceLabel() + " " + testSize + " rows " + threads + " threads.  Generation took " + duration + " ms";
    }


    /* particular comparative tests */

    /**
     * Compare the inserts using "one statement", Spring JDBC, and MyBatis
     * @param size size of the transactions batch to measure (same size will be used for the initial contents of the table)
     * @param runs number of times the test is run (for each access mode)
     * @return the recap of the test results in a readable format
     */
    public String testBare1stmtTemplateMybatis(int size, int runs) {
        return comparativeTest(size, size, runs, DEFAULT_THREAD_POOL_SIZE, txnOneStatementService, txnTemplateService, txnMyBatisService);
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
        return comparativeTest(size, size, runs, DEFAULT_THREAD_POOL_SIZE, txnTemplateService, txnTemplateOneStatementService);
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
    public String testTemplate1stmtVsTemplatedefaultVsMybatis(int size, int runs) {
        return comparativeTest(size, size, runs, DEFAULT_THREAD_POOL_SIZE, txnTemplateOneStatementService, txnTemplateService, txnMyBatisService);
    }


    /**
     * A more general comparative test on the inserts using different services
     * @param initSize number of rows to use as initial contents of the table)
     * @param testSize number of rows to use in the measure
     * @param runs number of times the test is run (for each access mode)
     * @param threads number of parallel threads we run a test on
     * @param serviceKeys a "word" containing one letter per service, according to the insertServicesMap
     * @return the recap of the test results in a readable format
     */
    public String comparativeTest(int initSize, int testSize, int runs, int threads, String serviceKeys) {
        TxnInsertService[] insertServices = new TxnInsertService[serviceKeys.length()];
        for (int iS = 0; iS < insertServices.length; iS++) {
            TxnInsertService service = insertServiceMap.get(serviceKeys.charAt(iS));
            if (service == null) return "ERROR: Unknown service key " + serviceKeys.charAt(iS);
            insertServices[iS] = service;
        }
        return formatOutputHeader(initSize, testSize, runs, threads, serviceKeys)
                + comparativeTest(initSize, testSize, runs, threads, insertServices);
    }


    /**
     * Compare the inserts using different services
     * @param initSize number of rows to use as initial contents of the table)
     * @param testSize number of rows to use in the measure
     * @param runs number of times the test is run (for each access mode)
     * @param threads number of parallel threads we run a test on
     * @param insertServices services to use
     * @return the recap of the test results in a readable format
     */
    public String comparativeTest(int initSize, int testSize, int runs, int threads,
                                  TxnInsertService... insertServices) {
        int nServices = insertServices.length;
        TestResultHolder[] resultHolders = new TestResultHolder[nServices];
        for (int iSrv = 0; iSrv < nServices; iSrv++) resultHolders[iSrv] = new TestResultHolder(insertServices[iSrv].serviceLabel());

        /* first, one warm-up run */
        logger.info("Warming up...");
        for (TxnInsertService insertService : insertServices) {
            prepareInitialContents(insertService, initSize, threads);
            testInsert(insertService, testSize, threads);
        }
        /* then, the runs to be measured */
        for (int iRun = 0; iRun < runs; iRun++) {
            logger.info("Comparing, iteration #" + (iRun+1) + "...");
            for (int iSrv = 0; iSrv < nServices; iSrv++) {
                prepareInitialContents(insertServices[iSrv], initSize, threads);
                long duration = testInsert(insertServices[iSrv], testSize, threads);
                resultHolders[iSrv].updateWithNewRun(duration);
            }
        }

        return formatOutput(resultHolders);
    }


    /* ================= test process ==================== */

    public void prepareInitialContents(TxnInsertService insertService, int nbr, int threads) {
        logger.info("start initializing contents with " + nbr + " transactions - " + insertService.serviceLabel());
        commonTxnRepository.deleteAll();
        TransactionFactory factory = new TransactionFactory("EXISTING0000000000");

        Transaction[] transactions = new Transaction[nbr];
        for (int i = 0; i < nbr; i++) transactions[i] = factory.createTransaction(1 + i);
        insertTransactionBatch(insertService, threads, transactions);

        logger.info("finished initializing contents");

        Time.waitMillis(1000);
    }


    public long testInsert(TxnInsertService insertService, int nbr, int threads) {
        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");
        logger.info("creating " + nbr + " current transactions - " + insertService.serviceLabel());
        int numerotationOffset = 1000000; if (nbr > 1000000) /* strange but let's accept it */ numerotationOffset = nbr;

        Transaction[] transactions = new Transaction[nbr];
        for (int i = 0; i < nbr; i++) transactions[i] = factory.createTransaction(numerotationOffset + 1 + i);
        logger.info("start writing " + nbr + " current transactions - " + insertService.serviceLabel());

        long start = System.currentTimeMillis();
        insertTransactionBatch(insertService, threads, transactions);
        long end = System.currentTimeMillis();

        logger.info("finished writing current transactions");

        return (end-start);
    }


    /**
     * Insert a number of transactions in the database table, using a thread pool
     */
    public void insertTransactionBatch(TxnInsertService insertService, int threads, Transaction[] transactions) {
        if (threads <= 1) { // let's use the simplest single-thread implementation
            for (Transaction transaction : transactions) insertService.insertTransactionWithCommit(transaction);
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(threads);

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

    private String formatOutputHeader(int initSize, int testSize, int runs, int threads, String serviceKeys) {
        StringBuilder sb = new StringBuilder(255);
        sb.append("Comparing ");
        for (int iS = 0; iS < serviceKeys.length(); iS++) {
            sb.append(serviceKeys.charAt(iS)).append(": ")
                    .append(insertServiceMap.get(serviceKeys.charAt(iS)).serviceLabel())
                    .append(" ");
        }
        sb.append("<br>&nbsp;&nbsp;&nbsp;&nbsp; rows = ").append(testSize)
                .append(" (with initial ").append(initSize).append(')')
                .append(", runs = ").append(runs)
                .append(", threads = ").append(threads).append("<br>");
        return sb.toString();
    }
}