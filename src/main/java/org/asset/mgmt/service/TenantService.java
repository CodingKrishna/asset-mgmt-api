package org.asset.mgmt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


@Service
@Slf4j
public class TenantService   {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private AbstractRoutingDataSource absDataSource;

    private final  String filePath="src/main/resources/tenant-ids.txt";

    private final Consumer<String> tenantDataSourceConsumer = tenantName -> {
        Map<Object, Object> targetedDataSource = new HashMap<>(absDataSource.getResolvedDataSources());

        targetedDataSource.put(tenantName, DataSourceBuilder
                .create().driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("root")
                .url("jdbc:mysql://localhost:3306/cli_" + tenantName).build());
        absDataSource.setTargetDataSources(targetedDataSource);
        absDataSource.afterPropertiesSet();
        //log.info("Data source initialized for default tenant: {}", absDataSource);
    };

    public void addTenant(String tenantName) {
        try  {
            Connection connection = dataSource.getConnection();
            executeSqlScript(connection,"cli_"+tenantName);
            tenantDataSourceConsumer.accept(tenantName);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeSqlScript(Connection connection,String tenantName) throws SQLException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("table_creation.sql");
        if (inputStream != null) {
            String script = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Replace placeholders with actual schema name
            script = script.replace("${schemaName}", tenantName);

            // Execute SQL script
            ScriptUtils.executeSqlScript(connection, new ByteArrayResource(script.getBytes()));
        } else {
            throw new IllegalArgumentException("Script not found: " + "src/main/resources/table_creation.sql");
        }
    }
    
    


    // Other methods for managing tenants: updateTenant, removeTenant, etc.
}

