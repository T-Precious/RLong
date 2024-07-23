package com.rongzer.suzhou.scm.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Description:
 * @Author: Administrator
 * @Date: 2023/2/9 15:18
 */
@Configuration
public class GlobalConfig {
    @Value("${mybatis.mapper-locations}")
    private String locationPattern;
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置，这里使用mybatis注解方式，没有配置xml文件
                new PathMatchingResourcePatternResolver().getResources(locationPattern));
        // 注册typehandler，供全局使用
        bean.setTypeHandlers(new MyStringTypeHandler());
        return bean.getObject();
    }
}
