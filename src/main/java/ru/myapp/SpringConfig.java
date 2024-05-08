package ru.myapp;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("ru.myapp") 
@PropertySource("classpath:resources/application.properties")
public class SpringConfig {
	@Autowired
	private Environment env; 
	
	/**
	 * компонент источника данных,
	 * создается объект класса источника данных DriverManagerDataSource с указанными 
	 * в файле application.properties данными об используемой БД (драйвер, url, username, password)   
	 * 
	 * @return DataSource объект источника данных
	 */
	@Bean
	DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("dataSource.driverClassName"));
		dataSource.setUrl(env.getProperty("dataSource.url"));
		dataSource.setUsername(env.getProperty("dataSource.username"));
		dataSource.setPassword(env.getProperty("dataSource.password"));
		return dataSource;
	}
	
	
	@Bean 
	public JewelDao jewelDao() {
		return new JewelDao() ;
	} 
}