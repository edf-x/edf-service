package com.mk.eap.validate;

/**
 * @author gaoxue
 *
 */
public final class ValidateConst {

    /** 数值类型在数据库定义的整数位长度 16 */
    public static final int DB_DECIMAL_INTEGER = 16;

    /** 数值类型在数据库定义的小数位长度 12 */
    public static final int DB_DECIMAL_FRACTION = 12;

    /** 字符串类型在数据库定义的默认长度 */
    public static final int DB_STRING_DEFAULT_LENGTH = 200;

    private ValidateConst() {
        // final const class, avoid instantiate
    }
}
