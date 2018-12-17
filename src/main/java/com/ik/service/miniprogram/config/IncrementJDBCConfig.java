package com.ik.service.miniprogram.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.ik.service.miniprogram.prop.IncrementDataSourceProperty;

@Configuration
public class IncrementJDBCConfig {

    @Autowired
    private IncrementDataSourceProperty incrementDataSourceProperty;

    private DruidDataSource incrementProp() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername(incrementDataSourceProperty.getIncrementUsername());
        ds.setUrl(incrementDataSourceProperty.getIncrementUrl());
        ds.setPassword(incrementDataSourceProperty.getIncrementPassword());
        ds.setFilters(incrementDataSourceProperty.getFilters());
        ds.setMaxActive(incrementDataSourceProperty.getMaxActive());
        ds.setInitialSize(incrementDataSourceProperty.getInitialSize());
        ds.setMaxWait(incrementDataSourceProperty.getMaxWait());
        ds.setMinIdle(incrementDataSourceProperty.getMinIdle());
        ds.setTimeBetweenEvictionRunsMillis(incrementDataSourceProperty.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(incrementDataSourceProperty.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(incrementDataSourceProperty.getValidationQuery());
        ds.setTestWhileIdle(incrementDataSourceProperty.getTestWhileIdle());
        ds.setTestOnBorrow(incrementDataSourceProperty.getTestOnBorrow());
        ds.setTestOnReturn(incrementDataSourceProperty.getTestOnReturn());
        ds.setPoolPreparedStatements(incrementDataSourceProperty.getPoolPreparedStatements());
        ds.setMaxPoolPreparedStatementPerConnectionSize(incrementDataSourceProperty.getMaxOpenPreparedStatements());
        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(600);
        ds.setLogAbandoned(false);
        ds.setConnectionInitSqls(Lists.newArrayList("set names utf8mb4"));
        return ds;
    }

    @Bean("incrementDataSource")
    public DataSource dataSource() throws SQLException {
        return incrementProp();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() throws SQLException {
        return new JdbcTemplate(dataSource());
    }
}
