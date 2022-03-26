package pl.estrix.subiekt.commons.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kamil on 30-07-2020.
 */
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//@JacksonAnnotation
public @interface ConfigJsonIgnore {

	boolean value() default true;
}
