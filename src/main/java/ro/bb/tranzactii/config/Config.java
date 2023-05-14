package ro.bb.tranzactii.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.bb.tranzactii.util.TransactionFactory;

@Configuration
public class Config {

    @Bean
    public TransactionFactory alreadyExistingTransactionFactory() {
        return new TransactionFactory("EXISTING0000000000");
    }

    @Bean
    public TransactionFactory newArrivingTransactionFactory() {
        return new TransactionFactory("TRANSACTION0000000");
    }
}
