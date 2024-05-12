package ru.myapp.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


/** 
 * @author Khoroshko Ekaterina ZKI21-16B 08.05.2024 
 * <h3>Вариант 12 - Ювелирное изделие</h3>
 * 
 * <h2> Spring MVC</h2>
 *
 * <p>Цель работы: ознакомиться c шаблоном MVC в Spring и тем, как он используется при создании web-приложений..</p>
 * 
 * <h4>Общая постановка задачи. </h4>
 *
 * <p> Изменить практическую работу №4 таким образом, чтобы она представляла собой web-приложение. </p>
 * <p>Web-приложение должно иметь следующие страницы:</p>
 *	<ol>
 * <li>  Главная страница содержит приветствие и ссылки на другие, которые дублируют по функционалу пункты меню из работы №4.</li>
 * <li>  Страница просмотра таблицы записей.
 * <li>  Страница добавления новой записи в таблицу.</li>
 * <li>  Страница редактирования записи.</li>
 * <li>  Страница удаления записи.</li>
 * <li>  Страница просмотра записей согласно некоторому критерию (аналогично пункту из практической работы №4).</li>
 *</ol>
 *
 *<p>Помимо этого должны быть осуществлены проверки (не менее двух) входных данных, сопровождающиеся соответствующими диагностическими сообщениями.</p>
 */

@Configuration
@ComponentScan("ru.myapp")
@PropertySource("classpath:resources/application.properties")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private Environment env; 
	
	private final ApplicationContext applicationContext;
	
	@Autowired
	public WebConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new
		SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		registry.viewResolver(resolver);
	}
	
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
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
