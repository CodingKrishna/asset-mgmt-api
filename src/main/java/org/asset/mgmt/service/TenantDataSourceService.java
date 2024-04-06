package org.asset.mgmt.service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantDataSourceService implements Serializable {
    private HashMap<String, DataSource> dataSources = new HashMap<>();

   private static final  String filePath="src/main/resources/tenant-ids.txt";

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }

    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<String> strings = readStringsFromFile();
        Map<String, DataSource> result = new HashMap<>();
        for (String id : strings) {
            DataSource dataSource = getDataSource(id);
            result.put(id, dataSource);
        }
        return result;
    }

    private DataSource createDataSource(String name) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName( "com.mysql.cj.jdbc.Driver")
                    .username("root")
                    .password("root")
                    .url("jdbc:mysql://localhost:3306/cli_"+name);
        return factory.build();
    }

    public  static void writeTenantIdToFile(String tenantId) {
        List<String> strings = readStringsFromFile();
        strings.add(tenantId);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String str : strings) {
                writer.write(str);
                writer.newLine();
            }
        }catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    private static List<String> readStringsFromFile() {
        List<String> strings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TenantDataSourceService.filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return strings;
    }
}
