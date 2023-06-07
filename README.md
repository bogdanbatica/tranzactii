This project has as goal to analyze the writing into a big table using a single PreparedStatement, 
compared to the default functionality of Spring JDBC or MyBatis.

Variant A ("Spring-JDBC"): use NamedParameterJdbcTemplate.update(String sql, Map<String,?> paramMap)

Variant B ("MyBatis"): use a MyBatis mapper

Variant Z ("one-statement"): starting from the Hikari DataSource present in the Spring context,
produce directly a Connection and a PreparedStatement
and use only these for writing N rows in the table.
(Used as a first proof-of-concept; not reliable for a multithread test)

Variant Y ("Spring-JDBC 1stmt"): use a customized DataSource and a customized Connection type
having attached a unique, dedicated PreparedStatement;
integrate those in a Hikari DataSource
and use with JdbcTemplate.update(PreparedStatementCreator)

Test process:
- First, empty the table.
- Then, insert an initial contents of N rows (without taking into consideration this time)
- The, insert another N rows and measure the duration this is taking.

The contents of the rows to insert is generated ad-hoc, randomly.
All the access to the database is done in a single thread.

Endpoints:
- variant A: /template?size={N}
- variant B: /mybatis?size={N}
- variant Z: /onestmt?size={N}
- variant Y: /template1?size={N}
- generic run of one A,B,Z, or Y: /runtest?service={code letter}&size={N}&threads={thread pool size}
- comparison between 2 or more services:  /compare?services={code letters}&size={N}&runs={number of runs}&threads={thread pool size}
- "help" (list of services and possible requests): /

Material used in the tests:
- Database server: Oracle XE (limited to using 2 CPU from the 1.8 GHz processor, 2 GB of RAM and 12 GB of disk space)
- Application server: Ryzen 5700U (8 cores, 16 threads, 1.8 GHz base frequency), 32 GB RAM.
- Bandwith between the two: 100 Mbps in a local network

Results of 10 single-thread runs with N = 10000 rows:
- one-statement: average duration 31.913 seconds, minimum 31.204, maximum 32.654
- Spring-JDBC: average duration 41.303 seconds, minimum 39.956, maximum 44.402
- MyBatis: average duration 41.778 seconds, minimum 41.090, maximum 42.606

Multi-thread comparison, with batch size = 10000, runs = 10, threads = 8
- Spring-JDBC: average duration 11.501 s, minimum 10.678, maximum 13.263
- MyBatis: average duration 12.669 s, minimum 11.748, maximum 14.294
- Spring-JDBC 1stmt: average duration 6.452 s, minimum 4.872, maximum 8.390
 