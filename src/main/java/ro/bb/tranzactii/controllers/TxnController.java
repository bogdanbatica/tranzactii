package ro.bb.tranzactii.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.bb.tranzactii.services.TxnService;

@RestController
public class TxnController {

    /** Number of transactions to use for a test if not specified in the request */
    private static final int DEFAULT_SIZE = 1000;
    private static final int DEFAULT_RUNS = 1;
    private final TxnService txnService;

    public TxnController(TxnService txnService) {
        this.txnService = txnService;
    }

    @GetMapping("/template")
    public String testJdbcTemplate(@RequestParam("size") String size) {
        return txnService.testJdbcTemplate(parseNumericParameter(size, DEFAULT_SIZE));
    }

    @GetMapping("/mybatis")
    public String testMyBatis(@RequestParam("size") String size) {
        return txnService.testMyBatis(parseNumericParameter(size, DEFAULT_SIZE));
    }

    @GetMapping("/onestmt")
    public String testOneStatement(@RequestParam("size") String size) {
        return txnService.testOneStatement(parseNumericParameter(size, DEFAULT_SIZE));
    }

    @GetMapping("/template1")
    public String testJdbcTemplate1(@RequestParam("size") String size) {
        return txnService.testJdbcTemplate1(parseNumericParameter(size, DEFAULT_SIZE));
    }

    @GetMapping("/runtest")
    public String testService(
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "threads", required = false) String threads,
            @RequestParam(value = "service") String service
    ) {
        if (service.length() != 1 || service.trim().equals("")) {
            return "ERROR: invalid service key " + service;
        }
        char serviceKey = service.charAt(0);
        return txnService.testService(serviceKey, parseNumericParameter(size, DEFAULT_SIZE),
                parseNumericParameter(threads, TxnService.DEFAULT_THREAD_POOL_SIZE));
    }


    @GetMapping("/compare1")
    public String comparativeTest1(@RequestParam("size") String size, @RequestParam("runs") String runs) {
        return txnService.testBare1stmtTemplateMybatis(
                parseNumericParameter(size, DEFAULT_SIZE), parseNumericParameter(runs, DEFAULT_RUNS));
    }

    @GetMapping("/compare2")
    public String comparativeTest2(@RequestParam("size") String size, @RequestParam("runs") String runs) {
        return txnService.comparativeTest(
                parseNumericParameter(size, DEFAULT_SIZE), parseNumericParameter(runs, DEFAULT_RUNS),
                TxnService.DEFAULT_THREAD_POOL_SIZE, "YAB"
        );
    }

    @GetMapping("/compare")
    public String comparativeTest(
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "runs", required = false) String runs,
            @RequestParam(value = "threads", required = false) String threads,
            @RequestParam(value = "services") String services
    ) {
        return txnService.comparativeTest(
                parseNumericParameter(size, DEFAULT_SIZE), parseNumericParameter(runs, DEFAULT_RUNS),
                parseNumericParameter(threads, TxnService.DEFAULT_THREAD_POOL_SIZE), services.toUpperCase()
        );
    }

    private int parseNumericParameter(String parameter, int defaultValue) {
        int nbr;
        try {
            nbr = Integer.parseInt(parameter);
            if (nbr <= 0) nbr = defaultValue;
        } catch (Exception e) {
            nbr = defaultValue;
        }
        return nbr;
    }


    /** Provide a few indication on the parameters */
    @GetMapping("/")
    public String help() {
        return "Single run of a service test: &nbsp;&nbsp;&nbsp;&nbsp;" +
                "GET /runtest?service={service key}&size={N}&threads={thread pool size}<br><br>"
            +  "Comparing two or more services: &nbsp;&nbsp;&nbsp;&nbsp;" +
                "GET /compare?services={service keys}&size={N}&runs={number of runs}&threads={thread pool size}<br><br>"
                + txnService.formatServiceMap();
    }



}
