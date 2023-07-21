package ru.pyrinoff.somebot.service;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.pyrinoff.somebot.api.service.IDatabaseDataProvider;
import ru.pyrinoff.somebot.api.service.ITelegramDataProvider;

@Service
@Getter
@PropertySource("classpath:application.properties")
public class PropertyService implements ITelegramDataProvider, IDatabaseDataProvider {

    //TG START

    @NotNull
    @Value("#{environment['tg.token']}")
    private String token;

    @NotNull
    @Value("#{environment['tg.botname']}")
    private String botname;

    //TG END
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

}
