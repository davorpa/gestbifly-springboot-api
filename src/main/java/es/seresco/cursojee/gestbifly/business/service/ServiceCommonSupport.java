package es.seresco.cursojee.gestbifly.business.service;

import javax.validation.constraints.NotNull;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase de soporte base con las funcionalidades que necesitan
 * la mayoría de los {@literal Service}.
 */
@Getter
@Setter
public abstract class ServiceCommonSupport
		implements Service, EnvironmentAware, MessageSourceAware
{

	@NotNull
	@lombok.NonNull
	private Environment environment;

	@NotNull
	@lombok.NonNull
	private MessageSource messageSource;

}
