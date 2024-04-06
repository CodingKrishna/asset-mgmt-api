package org.asset.mgmt.util;

public class MultiTenancyContext {
    private static final ThreadLocal<String> tenant = new InheritableThreadLocal<>();


    public static String getTenant() {
        return tenant.get();
    }

    public static void setTenant(String tenant) {
        MultiTenancyContext.tenant.set(tenant);
    }


    public static void clear() {
        tenant.remove();
    }
}
