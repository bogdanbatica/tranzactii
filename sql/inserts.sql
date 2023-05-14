SELECT TXN_SEQ.NEXTVAL FROM DUAL


INSERT INTO tranzactii
( local_id
, transaction_id
, checksum
, debtor_acct1
, debtor_acct2
, debtor_acct3
, debtor_acct4
, creditor_acct1
, creditor_acct2
, creditor_acct3
, creditor_acct4
, amount
, operation_tmstmp
, additional_info
)
VALUES
( :localId
, :transactionId
, :checksum
, :debtorAcct1
, :debtorAcct2
, :debtorAcct3
, :debtorAcct4
, :creditorAcct1
, :creditorAcct2
, :creditorAcct3
, :creditorAcct4
, :amount
, :operationTmstmp
, :additionalInfo
)





INSERT INTO tranzactii
( local_id
, transaction_id
, checksum
, debtor_acct1
, debtor_acct2
, debtor_acct3
, debtor_acct4
, creditor_acct1
, creditor_acct2
, creditor_acct3
, creditor_acct4
, amount
, operation_tmstmp
, additional_info
)
VALUES
( ?
, ?
, ?
, ?
, ?
, ?
, ?
, ?
, ?
, ?
, ?
, ?
, ?
, ?
)
