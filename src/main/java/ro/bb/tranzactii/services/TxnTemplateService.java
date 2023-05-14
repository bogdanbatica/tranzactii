package ro.bb.tranzactii.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.CommonTxnRepository;
import ro.bb.tranzactii.repositories.TransactionTemplateRepository;
import ro.bb.tranzactii.util.TransactionFactory;

@Service
public class TxnTemplateService {

    private static Logger logger = LoggerFactory.getLogger(TxnTemplateService.class);

    @Autowired
    private TransactionTemplateRepository transactionTemplateRepository;



    @Transactional
    public void insertTransactionWithCommit(Transaction transaction) {
        transactionTemplateRepository.insert(transaction);
    }



}