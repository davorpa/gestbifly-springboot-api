package es.seresco.cursojee.gestbifly.config;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = MyWebMvcConfiguration.class)
@Setter
@Slf4j
public class MyWebMvcConfiguration
		implements WebMvcConfigurer, MessageSourceAware
{
	private MessageSource messageSource;


	@Override
	public LocalValidatorFactoryBean getValidator() {
		log.info("Configuring custom validator using configurated MessageSource...");
		LocalValidatorFactoryBean bean = new OptionalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

}
