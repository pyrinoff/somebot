package ru.pyrinoff.somebot.api.service;

import org.jetbrains.annotations.NotNull;

public interface IDatabaseDataProvider {

    @NotNull String getDatabaseUrl();

    @NotNull String getDatabaseUsername();

    @NotNull String getDatabasePassword();

    @NotNull String getDatabaseDriver();

    @NotNull String getDatabaseDialect();

    @NotNull String getDatabaseHbm2ddlAuto();

    @NotNull String getDatabaseShowSql();

    @NotNull String getCacheUseSecondLevelCache();

    @NotNull String getCacheUseQueryCache();

    @NotNull String getCacheUseMinimalPuts();

    @NotNull String getCacheRegionPrefix();

    //@NotNull String getCacheProviderConfigurationFile();

    @NotNull String getCacheRegionFactoryClass();

}
