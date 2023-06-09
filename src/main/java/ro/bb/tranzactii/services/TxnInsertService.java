package ro.bb.tranzactii.services;

import org.springframework.transaction.annotation.Transactional;
import ro.bb.tranzactii.model.Transaction;

public interface TxnInsertService {

    void insertTransactionWithCommit(Transaction transaction);

    String serviceLabel();
}
