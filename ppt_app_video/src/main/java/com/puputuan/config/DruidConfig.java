package com.puputuan.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhuobin on 2017/5/25.
 */
@Component
@PropertySource(value = "classpath:/jdbc.properties", ignoreResourceNotFound = true)
public class DruidConfig {
    private static final Logger logger = Logger.getLogger(DruidConfig.class);


    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.public-key}")
    private String publicKey;

    @Bean
    @Primary
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setConnectionProperties("config.decrypt=true;config.decrypt.key="+publicKey);
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(2);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTimeBetweenLogStatsMillis(300000);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxOpenPreparedStatements(20);

        try {
            dataSource.setFilters("config");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Filter> filters = new ArrayList<>();
        filters.add(new WallFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

}
