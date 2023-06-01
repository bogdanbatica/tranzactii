package ro.bb.tranzactii.util;

import ro.bb.tranzactii.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class TransactionFactory {

    public static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE_LETTERS = LOWERCASE_LETTERS.toUpperCase();
    public static final String DECIMAL_DIGITS = "0123456789";
    public static final String UPPERCASE_ID_CHRS = UPPERCASE_LETTERS + DECIMAL_DIGITS;
    public static final String TEXT_CHRS = " " + UPPERCASE_LETTERS + LOWERCASE_LETTERS + DECIMAL_DIGITS;

    /** YYYYMMDD */
    private final String datePrefix = LocalDate.now().toString().replace("-", "");

    /** Fixed-length prefix, added to the date prefix to make a distinction between different origins of transactions */
    private final String specificIdPrefix;


    public TransactionFactory(String specificIdPrefix) {
        this.specificIdPrefix = specificIdPrefix;
    }


    /** creates a new transaction object, with some not-so-aberrant contents in the fields.
     * This method is thread-safe. Or if should be... Hopefully... */
    public Transaction createTransaction(long internalId) {
        Transaction txn = new Transaction();

        txn.localId = internalId;
        txn.transactionId = datePrefix
                + specificIdPrefix
                + String.valueOf(1000000000 + internalId).substring(1)  // 000...id
        ;
        txn.checksum = ContentGenerator.randomLong();
        txn.debtorAcct1 = ContentGenerator.generateFixedLengthText(20, UPPERCASE_ID_CHRS);
        txn.debtorAcct2 = txn.debtorAcct1;
        txn.debtorAcct3 = ContentGenerator.generateFixedLengthText(34, UPPERCASE_ID_CHRS);
        txn.debtorAcct4 = txn.debtorAcct3;
        txn.creditorAcct1 = ContentGenerator.generateFixedLengthText(20, UPPERCASE_ID_CHRS);
        txn.creditorAcct2 = txn.creditorAcct1;
        txn.creditorAcct3 = ContentGenerator.generateFixedLengthText(34, UPPERCASE_ID_CHRS);
        txn.creditorAcct4 = txn.creditorAcct3;
        txn.amount = BigDecimal.valueOf(ContentGenerator.randomInt(100, 9900000), 2);
        txn.operationTmstmp = OffsetDateTime.now().minusHours(1); // say operation submitted 1 hour ago
        txn.additionalInfo = ContentGenerator.generateFixedLengthText(2000, TEXT_CHRS); // fixed length to make things more comparable

        return txn;
    }


}
