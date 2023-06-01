package ro.bb.tranzactii.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.bb.tranzactii.model.Transaction;
import ro.bb.tranzactii.repositories.TransactionMyBatisRepository;

@Service
public class TxnMyBatisService implements TxnInsertService {

    @Autowired
    private TransactionMyBatisRepository transactionMyBatisRepository;


    @Override
    @Transactional
    public synchronized void insertTransactionWithCommit(Transaction transaction) {
        transactionMyBatisRepository.insert(transaction);
    }

    @Override
    public String serviceLabel() {
        return "MyBatis";
    }
}