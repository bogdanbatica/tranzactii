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
        txn.numeric01 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric02 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric03 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric04 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric05 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric06 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric07 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric08 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric09 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric10 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric11 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric12 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric13 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric14 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric15 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric16 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric17 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric18 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric19 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric20 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric21 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric22 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric23 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric24 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric25 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric26 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric27 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric28 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric29 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric30 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric31 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric32 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric33 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric34 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric35 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric36 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric37 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric38 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric39 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric40 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric41 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric42 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric43 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric44 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric45 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric46 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric47 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric48 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric49 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric50 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric51 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric52 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric53 = ContentGenerator.randomInt(1, 999999999);
        txn.numeric54 = ContentGenerator.randomInt(1, 999999999);
        txn.alphanr01 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr02 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr03 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr04 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr05 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr06 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr07 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr08 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr09 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr10 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr11 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr12 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr13 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr14 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr15 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr16 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr17 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr18 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr19 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr20 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr21 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr22 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr23 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr24 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr25 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr26 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr27 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr28 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr29 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr30 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr31 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr32 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr33 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr34 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr35 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr36 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr37 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr38 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr39 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr40 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr41 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr42 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr43 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr44 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr45 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr46 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr47 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr48 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr49 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr50 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr51 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr52 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr53 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.alphanr54 = ContentGenerator.generateFixedLengthText(10, TEXT_CHRS);
        txn.operationTmstmp = OffsetDateTime.now().minusHours(1); // say operation submitted 1 hour ago
        txn.additionalInfo = ContentGenerator.generateFixedLengthText(2000, TEXT_CHRS); // fixed length to make things more comparable

        return txn;
    }


}
