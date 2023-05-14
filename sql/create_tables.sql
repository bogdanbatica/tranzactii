CREATE TABLE tranzactii
(
  local_id number(16) PRIMARY KEY,
  transaction_id char(35),
  checksum number(19),
  debtor_acct1 char(20),
  debtor_acct2 char(20),
  debtor_acct3 varchar2(35),
  debtor_acct4 varchar2(35),
  creditor_acct1 char(20),
  creditor_acct2 char(20),
  creditor_acct3 varchar2(35),
  creditor_acct4 varchar2(35),
  amount number(25,2),
  operation_tmstmp timestamp,
  insert_tmstmp timestamp DEFAULT CURRENT_TIMESTAMP,
  additional_info varchar2(2000)
)

CREATE UNIQUE INDEX CHECKSUM_TXN_IDX ON TRANZACTII (CHECKSUM,TRANSACTION_ID);

CREATE INDEX TXN_IDX ON TRANZACTII (TRANSACTION_ID);


DROP SEQUENCE TXN_SEQ; -- we may want to do this for re-initialization

CREATE SEQUENCE TXN_SEQ;
