package pl.estrix.subiekt.commons.annotation;


import java.lang.annotation.*;

/**
 * Created by Kamil on 30-07-2020.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
//@JacksonAnnotation
public @interface RemapId {

	String AUTHORIZATION_ORDER_ID_REMAP_NAME = "authOrderId";

	int MAX_LENGHT = 800;

	String value();

	String keyValue() default "";

	boolean useSalt() default true;

	String[] supportedValues() default {};

}
