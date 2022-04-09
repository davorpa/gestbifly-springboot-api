package es.seresco.cursojee.gestbifly.mapper;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;

/**
 * A Mapstruct qualifier for ignoring {@code id} properties.
 * <p>
 * To be applied to {@link Mapper} methods.
 */
@Documented
@Retention(CLASS)
@Target(METHOD)
@Qualifier
@Mapping(target = "id", ignore = true)
public @interface MappingWithoutId {

}
