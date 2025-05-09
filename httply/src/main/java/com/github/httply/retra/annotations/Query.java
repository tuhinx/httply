package com.github.httply.retra.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for URL query parameters.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
    /**
     * The query parameter name.
     */
    String value();
    
    /**
     * Whether the query parameter is already URL encoded.
     */
    boolean encoded() default false;
}
