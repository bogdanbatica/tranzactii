package ro.bb.tranzactii.util;

import com.zaxxer.hikari.pool.ProxyConnection;
import com.zaxxer.hikari.util.FastList;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;

public class HikariDelegateFinder {

    public static Connection extractDelegate(ProxyConnection proxyConnection) {
        try {
            Field field = ProxyConnection.class.getDeclaredField("delegate");
            field.setAccessible(true);
            return (Connection) field.get(proxyConnection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
