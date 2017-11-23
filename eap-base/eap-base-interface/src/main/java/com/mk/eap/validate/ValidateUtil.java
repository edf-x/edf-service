package com.mk.eap.validate;

import com.mk.eap.base.BusinessException;
import com.mk.eap.base.DTO;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Map;
import java.util.Set;

/**
 * @author gaoxue
 *
 */
public final class ValidateUtil {

    private static final String THROW_EXCEPTION_FIELD_NAME = "throwException";

    private static final String MESSAGE_WITH_PATH_FIELD_NAME = "messageWithPath";

    private ValidateUtil() {
        // final util class
    }

    public static <T extends DTO> Set<ConstraintViolation<T>> validate(T dto) {
        return validate(dto, Default.class);
    }

    public static <T extends DTO> Set<ConstraintViolation<T>> validate(T dto, Class<?>... groups) {
        PlatformResourceBundleLocator userResourceBundleLocator = new PlatformResourceBundleLocator("config." + ResourceBundleMessageInterpolator.USER_VALIDATION_MESSAGES);
        ValidatorFactory factory = Validation.byDefaultProvider()
                                             .configure()
                                             .messageInterpolator(new ResourceBundleMessageInterpolator(userResourceBundleLocator))
                                             .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto, groups);
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            ConstraintDescriptor<?> constraintDescriptor = violation.getConstraintDescriptor();
            Map<String, Object> attributesMap = constraintDescriptor.getAttributes();

            // 处理是否抛出异常
            if (attributesMap.containsKey(THROW_EXCEPTION_FIELD_NAME)) {
                boolean throwException = Boolean.parseBoolean(attributesMap.get(THROW_EXCEPTION_FIELD_NAME).toString());
                boolean messageWithPath = false;
                if (attributesMap.containsKey(MESSAGE_WITH_PATH_FIELD_NAME)) {
                    messageWithPath = Boolean.parseBoolean(attributesMap.get(MESSAGE_WITH_PATH_FIELD_NAME).toString());
                }
                if (throwException) {
                    message.append(violation.getMessage());
                    if (messageWithPath) {
                        String propertyPath = violation.getPropertyPath().toString();
                        message.append("(" + propertyPath + ")");
                    }
                    message.append(";");
                }
            }
        }
        if (message.length() > 0) {
            throw new BusinessException("", message.toString());
        }
        return violations;
    }

}
