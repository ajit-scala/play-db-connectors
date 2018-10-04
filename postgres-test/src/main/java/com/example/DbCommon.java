package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbCommon {

    private static String verifyDbStmt = "SELECT count(*) AS total FROM pg_database WHERE datistemplate = false and datname='configurations'";
    private static String verifyTableStmt = "select count(*) AS total from information_schema.tables where table_name = 'keyvalueconfiguration'";

    static String initStmt = "create database configurations;\n";

    static String createTableStmt =
            "create table KeyValueConfiguration\n" +
                    "( configId varchar PRIMARY KEY not null,\n" +
                    "tenantId varchar not null,\n" +
                    "tenantName varchar not null,\n" +
                    "rolledOutConfiguration jsonb ,\n" +
                    "s3Url varchar,\n" +
                    "savedConfiguration jsonb,\n" +
                    "isDirty boolean not null,\n" +
                    "configType varchar(50) not null,\n" +
                    "createdAt TIMESTAMPTZ not null,\n" +
                    "UpdatedAt TIMESTAMPTZ not null);\n" +
                    "\n" +
                    "create index KeyValueConfiguration_tenant_id on  KeyValueConfiguration  (tenantId);\n" +
                    "\n" +
                    "create index KeyValueConfiguration_tenant_id_config_id on  KeyValueConfiguration  (tenantId, configId);\n" +
                    "\n" +
                    "create index KeyValueConfiguration_tenant_id_config_id_config_type on  KeyValueConfiguration  (tenantId, configId, configType);";

    private static String connectionString = "jdbc:postgresql://%s:5432/%s";

    public static void InitializeDb() throws Exception {
        Connection c = getDefaultConnection();
        Statement stmt = c.createStatement();
        ResultSet resultSet = stmt.executeQuery(verifyDbStmt);
        int count = 0;
        if (resultSet.next()) count = resultSet.getInt("total");
        if (count == 0) {
            stmt.executeUpdate(initStmt);

        }
        stmt.close();
        c.close();
        {
            c = getConnection();
            stmt = c.createStatement();
            count = 0;
            resultSet = stmt.executeQuery(verifyTableStmt);
            if (resultSet.next()) count = resultSet.getInt("total");
            if (count == 0) {
                stmt.executeUpdate(createTableStmt);
                c.commit();
            }
            stmt.close();
            c.close();
        }
    }

    private static String databaseHost = "stage-postgres-default-vpc.ctrduuiy90bn.us-east-1.rds.amazonaws.com";
    private static String userName = "serviceuser"; //postgres
    private static String password = "test1234";

    public static Connection getDefaultConnection() throws Exception {
        String databaseName = "postgres";
        Connection c = null;
        System.out.println(String.format(connectionString, databaseHost, databaseName));
        c = DriverManager.getConnection(String.format(connectionString, databaseHost, databaseName),
                userName, password);
        c.setAutoCommit(true);
        System.out.println("Opened postgres database successfully");

        return c;
    }

    public static Connection getConnection() throws Exception {
        String databaseName = "configurations";
        Connection c = null;
        c = DriverManager.getConnection(String.format(connectionString, databaseHost, databaseName),
                userName, password);
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
        return c;
    }
}
