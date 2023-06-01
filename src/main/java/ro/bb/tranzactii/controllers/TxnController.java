package ro.bb.tranzactii.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bb.tranzactii.services.TxnService;

@RestController
public class TxnController {

    private TxnService txnService;

    public TxnController(TxnService txnService) {
        this.txnService = txnService;
    }

    @GetMapping("/template")
    public String testJdbcTemplate() {
        return txnService.testJdbcTemplate();
    }

    @GetMapping("/mybatis")
    public String testMyBatis() {
        return txnService.testMyBatis();
    }

    @GetMapping("/onestmt")
    public String testOneStatement() {
        return txnService.testOneStatement();
    }



}
