package ro.bb.tranzactii.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.bb.tranzactii.services.TxnService;

@RestController
public class TxnController {

    /** Number of transactions to use for a test if not specified in the request */
    private static final int DEFAULT_SIZE = 10000;
    private static final int DEFAULT_RUNS = 1;
    private final TxnService txnService;

    public TxnController(TxnService txnService) {
        this.txnService = txnService;
    }

    @GetMapping("/template")
    public String testJdbcTemplate(@RequestParam("size") String size) {
        return txnService.testJdbcTemplate(parseSizeParameter(size));
    }

    @GetMapping("/mybatis")
    public String testMyBatis(@RequestParam("size") String size) {
        return txnService.testMyBatis(parseSizeParameter(size));
    }

    @GetMapping("/onestmt")
    public String testOneStatement(@RequestParam("size") String size) {
        return txnService.testOneStatement(parseSizeParameter(size));
    }

    @GetMapping("/template1")
    public String testJdbcTemplate1(@RequestParam("size") String size) {
        return txnService.testJdbcTemplate1(parseSizeParameter(size));
    }

    @GetMapping("/compare1")
    public String comparativeTest1(@RequestParam("size") String size, @RequestParam("runs") String runs) {
        return txnService.testBare1stmtTemplateMybatis(
                parseSizeParameter(size), parseRunsParameter(runs));
    }

    @GetMapping("/compare")
    public String comparativeTest(@RequestParam("size") String size, @RequestParam("runs") String runs) {
        return txnService.testTemplate1stmtVsTemplatedefaultVsMybatis(
                parseSizeParameter(size), parseRunsParameter(runs));
    }

    private int parseSizeParameter(String sizeParameter) {
        int nbTxn;
        try {
            nbTxn = Integer.parseInt(sizeParameter);
            if (nbTxn <= 0) nbTxn = DEFAULT_SIZE;
        } catch (NumberFormatException e) {
            nbTxn = DEFAULT_SIZE;
        }
        return nbTxn;
    }

    private int parseRunsParameter(String runsParameter) {
        int nbr;
        try {
            nbr = Integer.parseInt(runsParameter);
            if (nbr <= 0) nbr = DEFAULT_RUNS;
        } catch (NumberFormatException e) {
            nbr = DEFAULT_RUNS;
        }
        return nbr;
    }


}
