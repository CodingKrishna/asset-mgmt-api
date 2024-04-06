package org.asset.mgmt.util;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    @Value("${defaultTenant}")
    private String defaultTenant;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String t =  MultiTenancyContext.getTenant();
        return Objects.requireNonNullElse(t, defaultTenant);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
