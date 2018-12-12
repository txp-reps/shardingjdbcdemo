package com.txp.shardingjdbctest;

import com.txp.shardingjdbctest.service.CommonService;
import com.txp.shardingjdbctest.service.SpringPojoServiceImpl;
import com.txp.shardingjdbctest.util.MyMapper;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.txp.shardingjdbctest.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ShardingJdbcTestApplication {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(ShardingJdbcTestApplication.class).bannerMode(Banner.Mode.OFF).run(args)){
            process(context);
        }
    }

    private static void process(final ConfigurableApplicationContext applicationContext) {
        CommonService commonService = getCommonService(applicationContext);
        commonService.initEnvironment();
        commonService.processSuccess();
        try {
            commonService.processFailure();
        } catch (final Exception ex) {
            System.out.println(ex.getMessage());
            commonService.printData();
        } finally {
            commonService.cleanEnvironment();
        }
    }

    private static CommonService getCommonService(final ConfigurableApplicationContext applicationContext) {
        return applicationContext.getBean(SpringPojoServiceImpl.class);
    }
}
