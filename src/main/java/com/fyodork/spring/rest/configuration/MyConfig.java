package com.fyodork.spring.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.fyodork.spring.rest")
@EnableWebMvc
@EnableTransactionManagement

public class MyConfig {

    @Bean
    public DataSource dataSource()
    {
        ComboPooledDataSource dataSource=new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/my_db2?useSSL=false&amp&serverTimezone=UTC");
            dataSource.setUser("bestuser");
            dataSource.setPassword("bestuser");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        return dataSource;

    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        LocalSessionFactoryBean sessionFactoryBean=new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.fyodork.spring.rest.entity");
        Properties hibernateproperties=new Properties();
        hibernateproperties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        hibernateproperties.setProperty("hibernate.show_sql","true");

        sessionFactoryBean.setHibernateProperties(hibernateproperties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager()
    {
        HibernateTransactionManager TransactionManager = new HibernateTransactionManager();
        TransactionManager.setSessionFactory(sessionFactoryBean().getObject());
        return TransactionManager;

    }
}
