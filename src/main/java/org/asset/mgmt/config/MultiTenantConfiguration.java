package org.asset.mgmt.config;



import lombok.extern.slf4j.Slf4j;
import org.asset.mgmt.service.TenantDataSourceService;
import org.asset.mgmt.util.MultiTenancyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class MultiTenantConfiguration {

    @Value("${defaultTenant}")
    private String defaultTenant;

    @Autowired
    private ApplicationContext context;

    @Bean
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() {
        Map<Object, Object> resolvedDataSources = new HashMap<>();
        DataSource defaultDataSource=DataSourceBuilder
                .create().driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/asset_mgmt_db")
                .username("root")
                .password("root")
                .build();
        resolvedDataSources.put(defaultTenant,defaultDataSource);

        TenantDataSourceService tenantDataSource = context.getBean(TenantDataSourceService.class);
        resolvedDataSources.putAll(tenantDataSource.getAll());

        if (!resolvedDataSources.containsKey(defaultTenant)) {
            throw new RuntimeException("Default tenant '" + defaultTenant + "' not found in tenant configurations");
        }

        AbstractRoutingDataSource dataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return MultiTenancyContext.getTenant();
            }
        };
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        log.info("Data source initialized for default tenant: {}", defaultTenant);
        return dataSource;
    }

}