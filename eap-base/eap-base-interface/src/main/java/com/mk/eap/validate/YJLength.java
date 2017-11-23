package com.mk.eap.validate;

import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validate string length, between min and max.
 * <p/>
 * Supported type : {@code String}
 * <p/>
 * {@code null} elements are considered valid.
 * <p/>
 * min max, negative value as zero. if min > max, exchange values.
 * @author gaoxue
 *
 */
@Length
@ReportAsSingleViolation
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface YJLength {

    String message() default "{com.mk.common.validate.YJLength.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** string min length */
    @OverridesAttribute(constraint = Length.class, name = "min")
    int min() default 0;

    /** string max length */
    @OverridesAttribute(constraint = Length.class, name = "max")
    int max() default ValidateConst.DB_STRING_DEFAULT_LENGTH;

    /** throw exception when invalid */
    boolean throwException() default true;

    /** alias name */
    String alias() default "";

    /**
     * Defines several {@code @YJLength} annotations on the same element.
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        YJLength[] value();
    }
}
