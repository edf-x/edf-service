package com.mk.eap.validate;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated field of a class must not be {@code null} or empty.
 * Supported types are:
 * <ul>
 *     <li>{@code CharSequence} (length of character sequence is evaluated)</li>
 *     <li>{@code Collection} (collection size is evaluated)</li>
 *     <li>{@code Map} (map size is evaluated)</li>
 *     <li>Array (array length is evaluated)</li>
 * </ul>
 * @author gaoxue
 */
@NotEmpty
@ReportAsSingleViolation
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface YJNotEmpty {

    String message() default "{com.mk.common.validate.YJNotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** throw exception when invalid */
    boolean throwException() default true;

    /** exception message with property path */
    boolean messageWithPath() default true;

    /** field alias name */
    String alias() default "";

    /**
     * Defines several {@link YJNotEmpty} annotations on the same field.
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        YJNotEmpty[] value();
    }
}
