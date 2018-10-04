package com.example;

import java.util.*;
import java.sql.Connection;
import java.sql.Statement;

class PostgresTest {
    private static String insertStmt = "insert into keyvalueconfiguration \n" +
            "values('%s','%s','%s',null, '%s', '%s',TRUE, " +
            "'site_config',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
    private static String updateStmt = "update keyvalueconfiguration set savedConfiguration='%s', isDirty=true, " +
            "UpdatedAt=CURRENT_TIMESTAMP where tenantid='%s' and configid='%s'";
    private static String rolloutStmt = "update keyvalueconfiguration set rolledOutConfiguration=savedConfiguration, isDirty=false," +
            "UpdatedAt=CURRENT_TIMESTAMP where tenantid='%s' and configid='%s'";

    private static String configJson = "{\"siteId\":\"%s\",\"siteConsent\":\"implicit\",\"pages\":[{\"pageId\":1,\"pageType\":\"\",\"consent\":\"\",\"selectedRegexObject\":\"\",\"regexValue\":\"\",\"mappings\":[]},{\"pageId\":2,\"pageType\":\"General\",\"consent\":\"implicit\",\"selectedRegexObject\":\"\",\"regexValue\":\"\",\"mappings\":[{\"postProcessing\":[],\"key\":\"blub\",\"selector\":\"<img src=sdgdfh onerror=console.error(2011)>\",\"type\":\"html\"}]},{\"pageId\":3,\"pageType\":\"General\",\"consent\":\"implicit\",\"selectedRegexObject\":\"\",\"regexValue\":\"\",\"mappings\":[{\"postProcessing\":[],\"key\":\"third\",\"selector\":\"<img src=sdgdfh onerror=console.error(2011)>\",\"type\":\"html\"}]},{\"pageId\":4,\"pageType\":\"impression\",\"consent\":\"implicit\",\"selectedRegexObject\":\"\",\"regexValue\":\"\",\"mappings\":[{\"postProcessing\":[],\"key\":\"key\",\"selector\":\"<img src=\\\"invalid!!!!!!!!?????\\\" />\",\"type\":\"html\"},{\"postProcessing\":[],\"key\":\"key\",\"selector\":\"<img src=\\\"invalid\\\" />\",\"type\":\"html\"},{\"postProcessing\":[],\"key\":\"key\",\"selector\":\"<img src=\\\"invalid\\\" />\",\"type\":\"constant\"},{\"postProcessing\":[],\"key\":\"key\",\"selector\":\"<img src=\\\"invalid\\\" />\",\"type\":\"constant\"},{\"postProcessing\":[],\"key\":\"key\",\"selector\":\"<img src=\\\"invalid\\\" />\",\"type\":\"constant\"},{\"postProcessing\":[],\"key\":\"key\",\"selector\":\"<img src=\\\"invalid\\\" />\",\"type\":\"constant\"}]}]}";


    private static String selectStmt = "select * from keyvalueconfiguration where configid='%s'";

    private static Map<String, String> tenants = new LinkedHashMap<String, String>() {{
        put("tenant1", "redbull");
        put("tenant2", "fifa");
        put("tenant3", "hm");
        put("tenant4", "karstadt");
        put("tenant5", "lufthansa");
        put("tenant6", "expedia");
        put("tenant7", "sap");
        put("tenant8", "hybris");
        put("tenant9", "gigya");
        put("tenant10", "jochen-schweizer");
    }};

    public static void main(String[] args) {
        try {
            DbCommon.InitializeDb();
            Connection c = DbCommon.getConnection();
            for (int i = 0; i < 50000; i++) {

                Map.Entry<String, String> tenantEntry = getEntryByIndex(tenants, getRandomNumber());
                String configId = getGuid();
                String formattedConfigJson = String.format(configJson, configId);
                System.out.println("-----------");
                System.out.println(formattedConfigJson);
                System.out.println("-----------");

                String formattedInsert = String.format(insertStmt, configId, tenantEntry.getKey(), tenantEntry.getValue(), "s3://xyz", formattedConfigJson);
                System.out.println("Hello World " + formattedInsert);

                String formattedUpdate = String.format(updateStmt, formattedConfigJson, tenantEntry.getKey(), configId);
                String formattedRollout = String.format(rolloutStmt, tenantEntry.getKey(), configId);


                runStatement(formattedInsert, c);
                Thread.sleep(100);
                runStatement(formattedUpdate, c);
                Thread.sleep(100);
                runStatement(formattedRollout, c);
                Thread.sleep(100);
                runQuery(String.format(selectStmt, configId), c);
                Thread.sleep(100);

            }
            if (c != null) c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    private static void runStatement(String dmlStmt, Connection c) throws Exception {
        System.out.println("Creating/updating Record");

        if (c != null) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(dmlStmt);
            stmt.close();
            c.commit();
            System.out.println("Records created/updated successfully");
        }
    }

    private static void runQuery(String SelectStmt, Connection c) throws Exception {
        System.out.println("Running query");

        if (c != null) {
            Statement stmt = c.createStatement();
            stmt.executeQuery(SelectStmt);
            stmt.close();
            c.commit();
            System.out.println("Query returned successfully");
        }
    }

    private static Map.Entry<String, String> getEntryByIndex(Map<String, String> tenants, int index) {
        List<Map.Entry<String, String>> indexedList = new ArrayList<Map.Entry<String, String>>(tenants.entrySet());
        return indexedList.get(index);
    }

    private static int getRandomNumber() {
        int max = 9;
        int min = 0;
        return new Random().nextInt((max - min) + 1) + min;
    }

    private static String getGuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

