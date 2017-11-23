package com.mk.eap.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by shenxy on 26/4/17.
 * 基础档案转换的注解
 */
@Documented
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ArchiveConvertGroup {
    //基础档案类型
    enum Group{
        BankAccount,
        InBankAccount,
        Department,
        Person,
        Customer,
        Debtor,
        Vendor,
        Creditor,
        Project,
        Inventory,
        Asset,
        Unit,
        Currency,
        TaxRate,
        Invoice,
        Business, 
        Investor, 
        ByInvestor
    }

    //字段类型
    enum Type{
        require,            //需要根据查询到的档案, 自动填写. 当无法填充时,报错
        require_option,     //需要根据查询到的档案, 自动填写. 当无法填充时,跳过
        provide             //提供信息用来查询档案
    }

    //基础档案类型
    Group group();

    //字段类型
    Type type();

    //对应于vo里的字段名(需要与基础档案查询接口返回的字段名一致,通常为vo对象的字段名)
    String voFiledName();

    //当无法填写时, 是否填写默认值
    boolean useDefault() default false;

    boolean autoCreate() default false;
}
