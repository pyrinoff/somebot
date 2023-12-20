package com.github.pyrinoff.somebot.configuration;

import com.github.pyrinoff.somebot.Somebot;
import com.github.pyrinoff.somebot.api.service.IDatabaseDataProvider;
import org.hibernate.cfg.Environment;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@ConditionalOnExpression("${database.enabled:true}")
@PropertySource("classpath:application.properties")
@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Autowired
    private @NotNull IDatabaseDataProvider databasePropertyService;

    @Bean
    public @NotNull DataSource dataSource() {
        System.out.println("DB URL: " + databasePropertyService.getDatabaseUrl());
        @NotNull final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databasePropertyService.getDatabaseDriver());
        dataSource.setUrl(databasePropertyService.getDatabaseUrl());
        dataSource.setUsername(databasePropertyService.getDatabaseUsername());
        dataSource.setPassword(databasePropertyService.getDatabasePassword());
        return dataSource;
    }

    @Bean
    public @NotNull PlatformTransactionManager transactionManager(@NotNull final LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        @NotNull final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public @NotNull LocalContainerEntityManagerFactoryBean entityManagerFactory(@NotNull final DataSource dataSource) {
        @NotNull final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("ru.pyrinoff.somebot.model", Somebot.databasePackages);
        @NotNull final Properties properties = new Properties();
        properties.put(Environment.DIALECT, databasePropertyService.getDatabaseDialect());
        properties.put(Environment.HBM2DDL_AUTO, databasePropertyService.getDatabaseHbm2ddlAuto());
        properties.put(Environment.SHOW_SQL, databasePropertyService.getDatabaseShowSql());
        properties.put(Environment.FORMAT_SQL, databasePropertyService.getDatabaseFormatSql());
        properties.put(Environment.USE_SECOND_LEVEL_CACHE, databasePropertyService.getCacheUseSecondLevelCache());
        if(Boolean.getBoolean(databasePropertyService.getCacheUseSecondLevelCache())) {
            properties.put(Environment.CACHE_REGION_FACTORY, databasePropertyService.getCacheRegionFactoryClass());
            properties.put(Environment.USE_QUERY_CACHE, databasePropertyService.getCacheUseQueryCache());
            properties.put(Environment.USE_MINIMAL_PUTS, databasePropertyService.getCacheUseMinimalPuts());
            properties.put(Environment.CACHE_REGION_PREFIX, databasePropertyService.getCacheRegionPrefix());
        }
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

}
