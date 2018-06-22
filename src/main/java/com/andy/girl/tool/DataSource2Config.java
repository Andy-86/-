package com.andy.girl.tool;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.andy.girl.mapper.music", sqlSessionTemplateRef= "musicSqlSessionTemplate")
public class DataSource2Config {

    @Bean(name = "musicDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test2")

    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "musicSqlSessionFactory")

    public SqlSessionFactory testSqlSessionFactory(@Qualifier("musicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/music/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "musicTransactionManager")

    public DataSourceTransactionManager testTransactionManager(@Qualifier("musicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "musicSqlSessionTemplate")

    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("musicSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

