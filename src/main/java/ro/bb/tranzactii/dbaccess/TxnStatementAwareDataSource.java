package ro.bb.tranzactii.dbaccess;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.bb.tranzactii.repositories.TransactionOneStatementRepository;

/**
 * This class is meant to be used in a Hikari pool, instead of Hikari's DriverDataSource.
 * Hence, heavily inspired from that DriverDataSource
 */
public class TxnStatementAwareDataSource implements DataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TxnStatementAwareDataSource.class);

    /** until we decide where to keep the SQL statements... */
    private static final String INSERT_TRANSACTION_SQL = TransactionOneStatementRepository.INSERT_TRANSACTION_SQL;

    private final String jdbcUrl;

//    String driverClassName="oracle.jdbc.OracleDriver";
    private final Properties driverProperties;
    private Driver driver;

    public TxnStatementAwareDataSource(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.driverProperties = new Properties();
//        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
//            this.driverProperties.setProperty(entry.getKey().toString(), entry.getValue().toString());
//        }
        this.driverProperties.put("user", username);
        this.driverProperties.put("password", password);

//        if (driverClassName != null) {
//            Enumeration<Driver> drivers = DriverManager.getDrivers();
//
//            while(drivers.hasMoreElements()) {
//                Driver d = drivers.nextElement();
//                if (d.getClass().getName().equals(driverClassName)) {
//                    this.driver = d;
//                    break;
//                }
//            }
//
//            if (this.driver == null) {
//                LOGGER.warn("Registered driver with driverClassName={} was not found, trying direct instantiation.", driverClassName);
//                Class<?> driverClass = null;
//                ClassLoader threadContextClassLoader = Thread.currentThread().getContextClassLoader();
//
//                try {
//                    if (threadContextClassLoader != null) {
//                        try {
//                            driverClass = threadContextClassLoader.loadClass(driverClassName);
//                            LOGGER.debug("Driver class {} found in Thread context class loader {}", driverClassName, threadContextClassLoader);
//                        } catch (ClassNotFoundException var12) {
//                            LOGGER.debug("Driver class {} not found in Thread context class loader {}, trying classloader {}", driverClassName, threadContextClassLoader, this.getClass().getClassLoader());
//                        }
//                    }
//
//                    if (driverClass == null) {
//                        driverClass = this.getClass().getClassLoader().loadClass(driverClassName);
//                        LOGGER.debug("Driver class {} found in the HikariConfig class classloader {}", driverClassName, this.getClass().getClassLoader());
//                    }
//                } catch (ClassNotFoundException var13) {
//                    LOGGER.debug("Failed to load driver class {} from HikariConfig class classloader {}", driverClassName, this.getClass().getClassLoader());
//                }
//
//                if (driverClass != null) {
//                    try {
//                        this.driver = (Driver)driverClass.getDeclaredConstructor().newInstance();
//                    } catch (Exception var11) {
//                        LOGGER.warn("Failed to create instance of driver class {}, trying jdbcUrl resolution", driverClassName, var11);
//                    }
//                }
//            }
//        }

        String sanitizedUrl = jdbcUrl.replaceAll("([?&;]password=)[^&#;]*(.*)", "$1<masked>$2");

        try {
            if (this.driver == null) {
                this.driver = DriverManager.getDriver(jdbcUrl);
                LOGGER.debug("Loaded driver with class name {} for jdbcUrl={}", this.driver.getClass().getName(), sanitizedUrl);
//            } else if (!this.driver.acceptsURL(jdbcUrl)) {
//                throw new RuntimeException("Driver " + driverClassName + " claims to not accept jdbcUrl, " + sanitizedUrl);
            }
        } catch (SQLException var10) {
            throw new RuntimeException("Failed to get driver instance for jdbcUrl=" + sanitizedUrl, var10);
        }
    }


    public Connection getConnection() throws SQLException {
        Connection connection = this.driver.connect(this.jdbcUrl, this.driverProperties);
        return new TxnConnectionWithPreparedStatements(connection);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        Properties clonedProperties = (Properties)this.driverProperties.clone();

        if (username != null) {
            clonedProperties.put("user", username);
            if (clonedProperties.containsKey("username")) {
                clonedProperties.put("username", username);
            }
        }
        if (password != null) {
            clonedProperties.put("password", password);
        }

        Connection connection = this.driver.connect(this.jdbcUrl, clonedProperties);
        return new TxnConnectionWithPreparedStatements(connection);
    }

    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    public void setLogWriter(PrintWriter logWriter) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.driver.getParentLogger();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
