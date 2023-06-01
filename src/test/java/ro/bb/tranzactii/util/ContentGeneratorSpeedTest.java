package ro.bb.tranzactii.util;

import org.junit.jupiter.api.Test;
import ro.bb.tranzactii.model.Transaction;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    void generate100000Transactions() throws InterruptedException {
        Transaction[] transactions = new Transaction[100000];
        TransactionFactory factory = new TransactionFactory("TRANSACTION0000000");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            final int iTxn = i; // stupid final-lambda rulw
            executor.submit(() -> transactions[iTxn] = factory.createTransaction(iTxn));
//            transactions[iTxn] = factory.createTransaction(iTxn);
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS); // should end way faster
        long end = System.currentTimeMillis();

        // let's pretend we make some use of the generated transactions
        int maxInitialChr = 0; for (Transaction t : transactions) if (t.debtorAcct1.charAt(0) > maxInitialChr) maxInitialChr = t.debtorAcct1.charAt(0);

        System.out.println("Generation took " + (end-start) + " ms   // " + maxInitialChr);
    }


}
