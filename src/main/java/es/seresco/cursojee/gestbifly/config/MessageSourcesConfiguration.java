package es.seresco.cursojee.gestbifly.config;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import io.micrometer.core.lang.Nullable;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Setter
@Slf4j
public class MessageSourcesConfiguration
		implements EnvironmentAware
{

	public static final String ROOT_PROPKEY               = "appz.message-source";
	public static final String PROPKEY_CLASS              = ROOT_PROPKEY + ".class";
	public static final String PROPKEY_BASENAMES          = ROOT_PROPKEY + ".basenames";
	public static final String PROPKEY_CACHE_SECONDS      = ROOT_PROPKEY + ".cache-seconds";
	public static final String PROPKEY_CONCURRENT_REFRESH = ROOT_PROPKEY + ".concurrent-refresh";
	public static final String PROPKEY_DEFAULT_ENCODING   = ROOT_PROPKEY + ".default-encoding";
	public static final String PROPKEY_FILE_ENCODINGS     = ROOT_PROPKEY + ".file-encodings";
	public static final String PROPKEY_DEFAULT_LOCALE     = ROOT_PROPKEY + ".default-locale";
	public static final String PROPKEY_FALLBACK_TO_SYSTEM_LOCALE   = ROOT_PROPKEY + ".fallback-to-system-locale";
	public static final String PROPKEY_USE_CODE_AS_DEFAULT_MESSAGE = ROOT_PROPKEY + ".use-code-as-default-message";


	private Environment environment;


	@Bean({ "messageSource" })
	public MessageSource messageSource() {
		log.info("Configuring messageSource using `{}.*` property keys...", ROOT_PROPKEY);

		MessageSourceConfigurerType configurerType = environment.getProperty(
				PROPKEY_CLASS, MessageSourceConfigurerType.class,
				MessageSourceConfigurerType.RESOURCE_BUNDLE);

		MessageSource messageSource = configurerType.createInstance();
		log.info("Bootstraping messageSource in `{}` class mode with `{}`",
				configurerType, messageSource.getClass().getTypeName());
		configurerType.configureProperties(this, messageSource);

		return messageSource;
	}


	/**
	 * @return array String
	 * @see AbstractResourceBasedMessageSource#setBasenames(String...)
	 */
	protected String[] resolveBasenames()
	{
		String[] basenames = environment.getProperty(PROPKEY_BASENAMES, String[].class);
		if (basenames == null || basenames.length == 0) {
			basenames = new String[]{ "classpath:messages" };
		}
		if (log.isDebugEnabled()) {
			log.debug("messageSource.basenames will be: {}", Arrays.toString(basenames));
		}
		return basenames;
	}


	/**
	 * @return encoding
	 * @see AbstractResourceBasedMessageSource#setDefaultEncoding(String)
	 */
	protected @Nullable String resolveDefaultEncoding() {
		String defaultEncoding = environment.getProperty(PROPKEY_DEFAULT_ENCODING);
		// TODO: MessageSource default ISO-8859-1 but platform dependant need null
		if (!StringUtils.hasText(defaultEncoding)) {
			defaultEncoding = null;
		}
		log.debug("messageSource.defaultEncoding will be: {}", defaultEncoding);
		return defaultEncoding;
	}


	/**
	 * @return boolean
	 * @see AbstractResourceBasedMessageSource#setDefaultLocale(Locale)
	 */
	protected @Nullable Locale resolveDefaultLocale() {
		final Locale defaultLocale = environment.getProperty(PROPKEY_DEFAULT_LOCALE, Locale.class);
		log.debug("messageSource.defaultLocale will be: {}", defaultLocale);
		return defaultLocale;
	}


	/**
	 * @return boolean
	 * @see AbstractResourceBasedMessageSource#setFallbackToSystemLocale(boolean)
	 */
	protected boolean resolveFallbackToSystemLocale() {
		final Boolean fallbackToSystemLocale = environment.getProperty(
				PROPKEY_FALLBACK_TO_SYSTEM_LOCALE, Boolean.class,
				// AbstractResourceBasedMessageSource default
				Boolean.TRUE);
		log.debug("messageSource.fallbackToSystemLocale will be: {}", fallbackToSystemLocale);
		return fallbackToSystemLocale.booleanValue();
	}


	/**
	 * @return boolean
	 * @see AbstractResourceBasedMessageSource#setCacheSeconds(int)
	 * @see AbstractResourceBasedMessageSource#setCacheMillis(long)
	 */
	protected int resolveCacheSeconds() {
		final Integer cacheSeconds = environment.getProperty(
				PROPKEY_CACHE_SECONDS, Integer.class,
				// AbstractResourceBasedMessageSource default
				Integer.valueOf(-1));
		log.debug("messageSource.cacheSeconds will be: {}", cacheSeconds);
		return cacheSeconds.intValue();
	}


	/**
	 * @return boolean
	 * @see AbstractMessageSource#setUseCodeAsDefaultMessage(boolean)
	 */
	protected Boolean resolveUseCodeAsDefaultMessage() {
		final Boolean useCodeAsDefaultMessage = environment.getProperty(
				PROPKEY_USE_CODE_AS_DEFAULT_MESSAGE, Boolean.class,
				// AbstractMessageSource default
				Boolean.FALSE);
		log.debug("messageSource.useCodeAsDefaultMessage will be: {}", useCodeAsDefaultMessage);
		return useCodeAsDefaultMessage.booleanValue();
	}


	/**
	 * @return boolean
	 * @see MessageSourceSupport#setAlwaysUseMessageFormat(boolean)
	 */
	protected Boolean resolveAlwaysUseMessageFormat() {
		final Boolean alwaysUseMessageFormat = environment.getProperty(
				PROPKEY_USE_CODE_AS_DEFAULT_MESSAGE, Boolean.class,
				// MessageSource default
				Boolean.FALSE);
		log.debug("messageSource.alwaysUseMessageFormat will be: {}", alwaysUseMessageFormat);
		return alwaysUseMessageFormat.booleanValue();
	}


	/**
	 * @return boolean
	 * @see ReloadableResourceBundleMessageSource#setFileEncodings(java.util.Properties)
	 */
	protected Properties resolveFileEncodings() {
		final Properties fileEncodings = environment.getProperty(PROPKEY_FILE_ENCODINGS, Properties.class);
		log.debug("messageSource.fileEncodings will be: {}", fileEncodings);
		return fileEncodings;
	}


	/**
	 * @return boolean
	 * @see ReloadableResourceBundleMessageSource#setConcurrentRefresh(boolean)
	 */
	protected boolean resolveConcurrentRefresh() {
		final Boolean concurrentRefresh = environment.getProperty(
				PROPKEY_CONCURRENT_REFRESH, Boolean.class,
				// ReloadableResourceBundleMessageSource default
				Boolean.TRUE);
		log.debug("messageSource.concurrentRefresh will be: {}", concurrentRefresh);
		return concurrentRefresh.booleanValue();
	}




	public enum MessageSourceConfigurerType
	{
		RESOURCE_BUNDLE(ResourceBundleMessageSource::new, (configurer, messageSource) -> {
			// --- ResourceBasedMessageSource
			messageSource.setBasenames(configurer.resolveBasenames());
			messageSource.setDefaultEncoding(configurer.resolveDefaultEncoding());
			messageSource.setDefaultLocale(configurer.resolveDefaultLocale());
			messageSource.setFallbackToSystemLocale(configurer.resolveFallbackToSystemLocale());
			messageSource.setCacheSeconds(configurer.resolveCacheSeconds());
			// --- MessageSourceSupport
			messageSource.setAlwaysUseMessageFormat(configurer.resolveAlwaysUseMessageFormat());
			// --- MessageSource
			messageSource.setUseCodeAsDefaultMessage(configurer.resolveUseCodeAsDefaultMessage());
		}),

		RELOADABLE_RESOURCE_BUNDLE(ReloadableResourceBundleMessageSource::new, (configurer, messageSource) -> {
			// --- ResourceBasedMessageSource
			messageSource.setBasenames(configurer.resolveBasenames());
			messageSource.setDefaultEncoding(configurer.resolveDefaultEncoding());
			messageSource.setDefaultLocale(configurer.resolveDefaultLocale());
			messageSource.setFallbackToSystemLocale(configurer.resolveFallbackToSystemLocale());
			messageSource.setCacheSeconds(configurer.resolveCacheSeconds());
			// --- MessageSourceSupport
			messageSource.setAlwaysUseMessageFormat(configurer.resolveAlwaysUseMessageFormat());
			// --- MessageSource
			messageSource.setUseCodeAsDefaultMessage(configurer.resolveUseCodeAsDefaultMessage());
			// --- Reloadable
			messageSource.setConcurrentRefresh(configurer.resolveConcurrentRefresh());
			messageSource.setFileEncodings(configurer.resolveFileEncodings());
		});

		private Supplier<MessageSource> creater;
		private BiConsumer<MessageSourcesConfiguration, MessageSource> processor;

		@SuppressWarnings("unchecked")
		private <T extends MessageSource> MessageSourceConfigurerType(
				Supplier<T> creater,
				BiConsumer<MessageSourcesConfiguration, T> processor) {
			this.creater = (Supplier<MessageSource>) creater;
			this.processor = (BiConsumer<MessageSourcesConfiguration, MessageSource>) processor;
		}

		public MessageSource createInstance() {
			return creater.get();
		}

		public void configureProperties(
				final MessageSourcesConfiguration messageSourcesConfiguration,
				final MessageSource messageSource) {
			processor.accept(messageSourcesConfiguration, messageSource);
		}
	}

}
