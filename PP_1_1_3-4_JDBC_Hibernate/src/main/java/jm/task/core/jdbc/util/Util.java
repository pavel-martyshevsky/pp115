package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/my_database";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "12345";
    private static final String DB_DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String DB_SHOW_SQL = "true";
    private static final String DB_CSCS = "thread";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection is established");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection is not established");
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (getConnection() != null) {
            getConnection().close();
            System.out.println("Connection closed");
        }
    }

    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties set = new Properties();
                set.put(Environment.DRIVER, DB_DRIVER);
                set.put(Environment.URL, DB_URL);
                set.put(Environment.USER, DB_USERNAME);
                set.put(Environment.PASS, DB_PASSWORD);
                set.put(Environment.DIALECT, DB_DIALECT);
                set.put(Environment.SHOW_SQL, DB_SHOW_SQL);
                set.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, DB_CSCS);
                set.put(Environment.HBM2DDL_AUTO, "");
                configuration.setProperties(set).addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Connection is established");
            } catch (Exception e) {
                System.out.println("Connection is not established");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (getSessionFactory() != null) {
            getSessionFactory().close();
            System.out.println("Connection closed");
        }
    }
}
