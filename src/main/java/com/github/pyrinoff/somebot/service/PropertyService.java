package com.github.pyrinoff.somebot.service;

import com.github.pyrinoff.somebot.api.service.IDatabaseDataProvider;
import com.github.pyrinoff.somebot.api.service.ITelegramDataProvider;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@Getter
@PropertySource("classpath:application.properties")
public class PropertyService implements ITelegramDataProvider, IDatabaseDataProvider {

    //TG START
    @NotNull
    @Value("#{environment['tg.enabled']}")
    private Boolean tgEnabled;

    @NotNull
    @Value("#{environment['tg.token']}")
    private String tgToken;

    @NotNull
    @Value("#{environment['tg.botname']}")
    private String tgBotname;

    @NotNull
    @Value("#{environment['tg.admin.id']}")
    private Long tgAdminId;
    //TG END

    //VK START
    @NotNull
    @Value("#{environment['vk.enabled']}")
    private Boolean vkEnabled;

    @NotNull
    @Value("#{environment['vk.groupid']}")
    private Integer vkGroupId;

    @NotNull
    @Value("#{environment['vk.grouptoken']}")
    private String vkGroupToken;

    @NotNull
    @Value("#{environment['vk.admintoken']}")
    private String vkAdminToken;
    @NotNull

    @Value("#{environment['vk.adminid']}")
    private Integer vkAdminId;
    @NotNull

    @Value("#{environment['vk.appid']}")
    private Integer vkAppId;
    //VK END

    //DATABASE START
    @NotNull
    @Value("#{environment['database.url']}")
    private String databaseUrl;

    @NotNull
    @Value("#{environment['database.username']}")
    private String databaseUsername;

    @NotNull
    @Value("#{environment['database.password']}")
    private String databasePassword;

    @NotNull
    @Value("#{environment['database.driver']}")
    private String databaseDriver;

    @NotNull
    @Value("#{environment['database.dialect']}")
    private String databaseDialect;

    @NotNull
    @Value("#{environment['database.hbm2ddl_auto']}")
    private String databaseHbm2ddlAuto;

    @NotNull
    @Value("#{environment['database.show_sql']}")
    private String databaseShowSql;

    @NotNull
    @Value("#{environment['database.format_sql']}")
    private String databaseFormatSql;

    @NotNull
    @Value("#{environment['database.cache.use_second_level_cache']}")
    private String cacheUseSecondLevelCache;

    @NotNull
    @Value("#{environment['database.cache.use_query_cache']}")
    private String cacheUseQueryCache;

    @NotNull
    @Value("#{environment['database.cache.use_minimal_puts']}")
    private String cacheUseMinimalPuts;

    @NotNull
    @Value("#{environment['database.cache.region_prefix']}")
    private String cacheRegionPrefix;

    //@NotNull
    //@Value("#{environment['database.cache.provider_configuration_file_resource_path']}")
    //private String cacheProviderConfigurationFile;

    @NotNull
    @Value("#{environment['database.cache.region.factory_class']}")
    private String cacheRegionFactoryClass;
    //DATABASE END

    //QIWI
    @Nullable
    @Value("#{environment['qiwi.public_key']}")
    private String qiwiPublicKey;

    @Nullable
    @Value("#{environment['qiwi.secret_key']}")
    private String qiwiSecretKey;

    @Nullable
    @Value("#{environment['qiwi.mock_enabled']}")
    private Boolean qiwiMockEnabled;

    @NotNull
    @Value("#{environment['qiwi.flood_interval_seconds']}")
    private Integer qiwiFloodIntervalSeconds;
    //QIWI END

}
