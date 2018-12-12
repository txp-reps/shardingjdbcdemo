package com.txp.shardingjdbctest.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Druid监控配置
 *
 * @author LinkinStar
 */
@Configuration
public class DataSourceConfig {

    /**
     * 配置spy数据源
     */
    @Bean("spyDataSource")
    @Primary
    public DataSource spyDataSource(@Qualifier("dataSource") DataSource druidDataSource) {
        return new DataSourceSpy(druidDataSource);
    }

    /**
     * 配置读取spring数据源
     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return new DruidDataSource();
//    }

    @Bean
    public DataSource dataSource() throws IOException, SQLException {
        return  YamlShardingDataSourceFactory.createDataSource(getYamlFile());
    }
    private static File getYamlFile() {
        return new File(Object.class.getResource("/sharding-databases.yaml").getFile());
    }

}
