This project has as goal to analyze the writing into a big table using a single PreparedStatement, 
compared to the default functionality of Spring JDBC or MyBatis.

Variant 1: starting from the DataSource present in the Spring context, produce directly a Connection and a PreparedStatement and use only these for writing N rows in the table.
(This is just for ease of coding during this test; the purpose is not to sustain the use of bare JDBC without the Spring / MyBatis layers.)

Variant 2: use NamedParameterJdbcTemplate.update(String sql, Map<String,?> paramMap)

Variant 3: use a MyBatis mapper

Test process:
- First, empty the table.
- Then, insert an initial contents of N rows (without taking into consideration this time)
- The, insert another N rows and measure the duration this is taking.

The contents of the rows to insert is generated ad-hoc, randomly.
All the access to the database is done in a single thread.

Endpoints:
- variant 1: /onestmt?size={N}
- variant 2:  /template?size={N}  
- variant 3:  /mybatis?size={N}
- comparison between the three variants: /compare?size={N}&runs={number of runs}

Material:
- Database server: Oracle XE (limited to using 2 CPU from the 1.8 GHz processor, 2 GB of RAM and 12 GB of disk space)
- Application server: Ryzen 5700U (8 cores, 16 threads, 1.8 GHz base frequency), 32 GB RAM. Not that I needed all that...
- Bandwith between the two: 100 Mbps in a local network

Results of 10 runs with N = 10000 rows:
- one-statement: average duration 31.913 seconds, minimum 31.204, maximum 32.654
- Spring-JDBC: average duration 41.303 seconds, minimum 39.956, maximum 44.402
- MyBatis: average duration 41.778 seconds, minimum 41.090, maximum 42.606

