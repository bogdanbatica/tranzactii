package ro.bb.tranzactii.dbaccess;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;


/**
 * PreparedStatement we should be able to reuse on Java side, even when the framework is trained
 *  to close systematically the Statements it uses.
 * Most of the overriden methods are copied/inspired from https://gist.github.com/EmilHernvall/956352, thanks for sharing!
 */
public class UnclosablePreparedStatement implements PreparedStatement {

    PreparedStatement internalPreparedStatement;

    public UnclosablePreparedStatement(PreparedStatement internalPreparedStatement) {
        this.internalPreparedStatement = internalPreparedStatement;
    }


    /**
     * As stated, we'll prevent clients that use this object (such as JdbcTemplate)
     * to close our PreparedStatement through the standard method.
     * The internal PreparedStatement can still be closed by accessing it directly.
     */
    @Override
    public void close() throws SQLException {
        // no action
    }

    /** However, if it's really needed to close the internal PreparedStatement... */
    public void closeTheUnclosable() {
        if (internalPreparedStatement != null) {
            try {
                internalPreparedStatement.close();
            } catch (SQLException e) {/* at least we've tried... */}
        }
    }

    /* all the other methods delegate to the internal PreparedStatement, without any change of behaviour */

    @Override
    public void addBatch() throws SQLException {
        internalPreparedStatement.addBatch();
    }

    @Override
    public void clearParameters() throws SQLException {
        internalPreparedStatement.clearParameters();
    }

    @Override
    public boolean execute() throws SQLException {
        return internalPreparedStatement.execute();
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return internalPreparedStatement.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return internalPreparedStatement.executeUpdate();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return internalPreparedStatement.getMetaData();
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return internalPreparedStatement.getParameterMetaData();
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        internalPreparedStatement.setArray(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        internalPreparedStatement.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        internalPreparedStatement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        internalPreparedStatement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        internalPreparedStatement.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        internalPreparedStatement.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        internalPreparedStatement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        internalPreparedStatement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        internalPreparedStatement.setBlob(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        internalPreparedStatement.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        internalPreparedStatement.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        internalPreparedStatement.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        internalPreparedStatement.setByte(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        internalPreparedStatement.setBytes(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        internalPreparedStatement.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        internalPreparedStatement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        internalPreparedStatement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        internalPreparedStatement.setClob(parameterIndex, x);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        internalPreparedStatement.setClob(parameterIndex, reader);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        internalPreparedStatement.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        internalPreparedStatement.setDate(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        internalPreparedStatement.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        internalPreparedStatement.setDouble(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        internalPreparedStatement.setFloat(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        internalPreparedStatement.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        internalPreparedStatement.setLong(parameterIndex, x);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        internalPreparedStatement.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        internalPreparedStatement.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        internalPreparedStatement.setNClob(parameterIndex, value);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        internalPreparedStatement.setNClob(parameterIndex, reader);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        internalPreparedStatement.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        internalPreparedStatement.setNString(parameterIndex, value);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        internalPreparedStatement.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        internalPreparedStatement.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        internalPreparedStatement.setObject(parameterIndex, x);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        internalPreparedStatement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        internalPreparedStatement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        internalPreparedStatement.setRef(parameterIndex, x);
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        internalPreparedStatement.setRowId(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        internalPreparedStatement.setShort(parameterIndex, x);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        internalPreparedStatement.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        internalPreparedStatement.setString(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        internalPreparedStatement.setTime(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        internalPreparedStatement.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        internalPreparedStatement.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        internalPreparedStatement.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        internalPreparedStatement.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        internalPreparedStatement.setURL(parameterIndex, x);
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        internalPreparedStatement.addBatch(sql);
    }

    @Override
    public void cancel() throws SQLException {
        internalPreparedStatement.cancel();
    }

    @Override
    public void clearBatch() throws SQLException {
        internalPreparedStatement.clearBatch();
    }

    @Override
    public void clearWarnings() throws SQLException {
        internalPreparedStatement.clearWarnings();
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return internalPreparedStatement.execute(sql);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return internalPreparedStatement.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return internalPreparedStatement.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return internalPreparedStatement.execute(sql, columnNames);
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return internalPreparedStatement.executeBatch();
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return internalPreparedStatement.executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return internalPreparedStatement.executeUpdate(sql);
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return internalPreparedStatement.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return internalPreparedStatement.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return internalPreparedStatement.executeUpdate(sql, columnNames);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return internalPreparedStatement.getConnection();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return internalPreparedStatement.getFetchDirection();
    }

    @Override
    public int getFetchSize() throws SQLException {
        return internalPreparedStatement.getFetchSize();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return internalPreparedStatement.getGeneratedKeys();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return internalPreparedStatement.getMaxFieldSize();
    }

    @Override
    public int getMaxRows() throws SQLException {
        return internalPreparedStatement.getMaxRows();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return internalPreparedStatement.getMoreResults();
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return internalPreparedStatement.getMoreResults(current);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return internalPreparedStatement.getQueryTimeout();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return internalPreparedStatement.getResultSet();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return internalPreparedStatement.getResultSetConcurrency();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return internalPreparedStatement.getResultSetHoldability();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return internalPreparedStatement.getResultSetType();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return internalPreparedStatement.getUpdateCount();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return internalPreparedStatement.getWarnings();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return internalPreparedStatement.isClosed();
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return internalPreparedStatement.isPoolable();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        internalPreparedStatement.setCursorName(name);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        internalPreparedStatement.setEscapeProcessing(enable);
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        internalPreparedStatement.setFetchDirection(direction);
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        internalPreparedStatement.setFetchSize(rows);
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        internalPreparedStatement.setMaxFieldSize(max);
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        internalPreparedStatement.setMaxRows(max);
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        internalPreparedStatement.setPoolable(poolable);
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        internalPreparedStatement.setQueryTimeout(seconds);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return internalPreparedStatement.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return internalPreparedStatement.unwrap(iface);
    }


    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        internalPreparedStatement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
        internalPreparedStatement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public long executeLargeUpdate() throws SQLException {
        return internalPreparedStatement.executeLargeUpdate();
    }

    @Override
    public long getLargeUpdateCount() throws SQLException {
        return internalPreparedStatement.getLargeUpdateCount();
    }

    @Override
    public void setLargeMaxRows(long max) throws SQLException {
        internalPreparedStatement.setLargeMaxRows(max);
    }

    @Override
    public long getLargeMaxRows() throws SQLException {
        return internalPreparedStatement.getLargeMaxRows();
    }

    @Override
    public long[] executeLargeBatch() throws SQLException {
        return internalPreparedStatement.executeLargeBatch();
    }

    @Override
    public long executeLargeUpdate(String sql) throws SQLException {
        return internalPreparedStatement.executeLargeUpdate(sql);
    }

    @Override
    public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return internalPreparedStatement.executeLargeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return internalPreparedStatement.executeLargeUpdate(sql, columnIndexes);
    }

    @Override
    public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
        return internalPreparedStatement.executeLargeUpdate(sql, columnNames);
    }

    @Override
    public String enquoteLiteral(String val) throws SQLException {
        return internalPreparedStatement.enquoteLiteral(val);
    }

    @Override
    public String enquoteIdentifier(String identifier, boolean alwaysQuote) throws SQLException {
        return internalPreparedStatement.enquoteIdentifier(identifier, alwaysQuote);
    }

    @Override
    public boolean isSimpleIdentifier(String identifier) throws SQLException {
        return internalPreparedStatement.isSimpleIdentifier(identifier);
    }

    @Override
    public String enquoteNCharLiteral(String val) throws SQLException {
        return internalPreparedStatement.enquoteNCharLiteral(val);
    }

    /* TODO see if leaving this method as is will clash with our purpose to avoid closing the internal PreparedStatement */
    @Override
    public void closeOnCompletion() throws SQLException {
        internalPreparedStatement.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return internalPreparedStatement.isCloseOnCompletion();
    }
}
