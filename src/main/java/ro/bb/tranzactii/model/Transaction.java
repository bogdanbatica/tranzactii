package ro.bb.tranzactii.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Transaction {

    public long   localId         ;
    public String transactionId   ;
    public long   checksum        ;
    public String debtorAcct1     ;
    public String debtorAcct2     ;
    public String debtorAcct3     ;
    public String debtorAcct4     ;
    public String creditorAcct1   ;
    public String creditorAcct2   ;
    public String creditorAcct3   ;
    public String creditorAcct4   ;
    public BigDecimal amount;
    public OffsetDateTime operationTmstmp;
    public String additionalInfo  ;


}
