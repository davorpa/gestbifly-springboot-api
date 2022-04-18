package es.seresco.cursojee.gestbifly.config;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
	public void addArgumentResolvers(
			final List<HandlerMethodArgumentResolver> resolvers)
	{
		log.info("Configuring method argument resolvers...");
		// TODO Add custom method argument resolvers
	}


	@Override
	public void addFormatters(
			final FormatterRegistry registry)
	{
		log.info("Configuring custom formatters and converters...");
		// TODO Add custom formatters or converters
	}


	@Override
	public void addViewControllers(
			final ViewControllerRegistry registry)
	{
		log.info("Configuring custom view controllers...");
		// TODO Add custom view controllers

	}


	@Override
	public LocalValidatorFactoryBean getValidator() {
		log.info("Configuring custom validator using configurated MessageSource...");
		LocalValidatorFactoryBean bean = new OptionalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}

}
