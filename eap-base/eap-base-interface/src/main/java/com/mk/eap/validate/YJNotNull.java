package com.mk.eap.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates object not null.
 * @author gaoxue
 *
 */
@NotNull
@ReportAsSingleViolation
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface YJNotNull {

    String message() default "{com.mk.common.validate.YJNotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** throw exception when invalid */
    boolean throwException() default true;

    /** exception message with property path */
    boolean messageWithPath() default true;

    /** alias name */
    String alias() default "";
    
    /**
     * Defines several {@code @YJNotNull} annotations on the same element.
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        YJNotNull[] value();
    }
}
