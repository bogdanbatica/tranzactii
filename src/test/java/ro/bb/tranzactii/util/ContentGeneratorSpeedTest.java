package ro.bb.tranzactii.util;

import org.junit.jupiter.api.Test;
import ro.bb.tranzactii.model.Transaction;

public class ContentGeneratorSpeedTest {

    @Test
    void generate100000SequencesX1000characters() {
        String[] generatedSequences = new String[100000];

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            generatedSequences[i] = ContentGenerator.generateFixedLengthText(1000, TransactionFactory.TEXT_CHRS);
        }
        long end = System.currentTimeMillis();

        // let's pretend we make some use of the generated sequences
        int maxInitialChr = 0; for (String s : generatedSequences) if (s.charAt(0) > maxInitialChr) maxInitialChr = s.charAt(0);

        System.out.println("Generation took " + (end-start) + " ms   // " + maxInitialChr);
    }

    @Test
    void generate100000Transactions() {
        Transaction[] transactions = new Transaction[100000];
        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            transactions[i] = factory.createTransaction(i);
        }
        long end = System.currentTimeMillis();

        // let's pretend we make some use of the generated transactions
        int maxInitialChr = 0; for (Transaction t : transactions) if (t.debtorAcct1.charAt(0) > maxInitialChr) maxInitialChr = t.debtorAcct1.charAt(0);

        System.out.println("Generation took " + (end-start) + " ms   // " + maxInitialChr);
    }


}
