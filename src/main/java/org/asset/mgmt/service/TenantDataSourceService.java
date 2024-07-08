package org.asset.mgmt.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
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
//        List<String> strings = readStringsFromFile()
        try {
            DataSource dataSource = DataSourceBuilder
                    .create().driverClassName( "com.mysql.cj.jdbc.Driver")
                    .username("root")
                    .password("root")
                    .url("jdbc:mysql://localhost:3306/asset_mgmt_db").build();
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM asset_mgmt_db.client;");
            List<String> clients=new ArrayList<>();
            while (resultSet.next()) {
                clients.add(resultSet.getString(resultSet.findColumn("company")));  // Adjust based on your query and expected result
            }
            Map<String, DataSource> result = new HashMap<>();
            for (String client : clients) {
                DataSource tenantDataSource = getDataSource(client);
                result.put(client, tenantDataSource);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource createDataSource(String name) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName( "com.mysql.cj.jdbc.Driver")
                    .username("root")
                    .password("root")
                    .url("jdbc:mysql://localhost:3306/cli_"+name);
        return factory.build();
    }

//    public  static void writeTenantIdToFile(String tenantId) {
//        List<String> strings = readStringsFromFile();
//        strings.add(tenantId);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            for (String str : strings) {
//                writer.write(str);
//                writer.newLine();
//            }
//        }catch (IOException e) {
//            System.err.println("Error writing to file: " + e.getMessage());
//        }
//    }
//    private static List<String> readStringsFromFile() {
//        List<String> strings = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(TenantDataSourceService.filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                strings.add(line);
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading file: " + e.getMessage());
//        }
//        return strings;
//    }
}
