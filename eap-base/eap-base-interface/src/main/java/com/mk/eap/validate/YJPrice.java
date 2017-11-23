package com.mk.eap.validate;

import com.mk.eap.constant.CommonConst;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates price, integer and fraction.
 * <p/>
 * Supported types are:
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code byte}, {@code short}, {@code int}, {@code long}, and their respective
 *     wrapper types</li>
 * </ul>
 * <p/>
 * {@code null} elements are considered valid.
 * @author gaoxue
 *
 */
@Digits(fraction = ValidateConst.DB_DECIMAL_FRACTION, integer = ValidateConst.DB_DECIMAL_INTEGER)
@DecimalMin(value = "0.0")
@ReportAsSingleViolation
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface YJPrice {

    String message() default "{com.mk.common.validate.YJPrice.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** default scale */
    int yjScale() default CommonConst.PRICE_DECIMAL_SCALE;

    @OverridesAttribute(constraint = Digits.class, name = "integer")
    int integer() default ValidateConst.DB_DECIMAL_INTEGER;

    @OverridesAttribute(constraint = Digits.class, name = "fraction")
    int fraction() default ValidateConst.DB_DECIMAL_FRACTION;

    /** throw exception when invalid */
    boolean throwException() default true;

    /** alias name */
    String alias() default "";

    /**
     * Defines several {@code @YJPrice} annotations on the same element.
     */
    @Target({ FIELD })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        YJPrice[] value();
    }

}
