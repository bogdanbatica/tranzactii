CREATE TABLE tranzactii
(
  local_id number(16) PRIMARY KEY,
  transaction_id char(16),
  checksum number(19),
  debtor_acct1 char(16),
  debtor_acct2 char(16),
  debtor_acct3 varchar2(35),
  debtor_acct4 varchar2(35),
  creditor_acct1 char(16),
  creditor_acct2 char(16),
  creditor_acct3 varchar2(35),
  creditor_acct4 varchar2(35),
  additional_info varchar2(2000)
)



