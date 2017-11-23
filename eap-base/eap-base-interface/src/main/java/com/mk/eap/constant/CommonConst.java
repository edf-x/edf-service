package com.mk.eap.constant;

/**
 * 公共常量自定义类
 * @author gaox
 *
 */
public final class CommonConst {

    /** 数量小数位精度，默认 4 */
    public static final int QUANTITY_DECIMAL_SCALE = 4;

    /** 单价小数位精度，默认 2 */
    public static final int PRICE_DECIMAL_SCALE = 2;

    /** 金额小数位精度，默认 2 */
    public static final int AMOUNT_DECIMAL_SCALE = 2;

    /** 结算方式，现金 */
    public static final long SETTLEMENT_TYPE_CASH = 1;

    /** 结算方式，银行 */
    public static final long SETTLEMENT_TYPE_BANK = 2;

    /** 结算方式，微信 */
    public static final long SETTLEMENT_TYPE_WECHAT = 3;

    /** 结算方式，支付宝 */
    public static final long SETTLEMENT_TYPE_ALIPAY = 4;

    /** 结算方式，冲减预收款 */
    public static final long SETTLEMENT_TYPE_REVERSE_PRE_RECEIVE = 5;

    /** 结算方式，冲减预付款 */
    public static final long SETTLEMENT_TYPE_REVERSE_PRE_PAYMENT = 6;

    /** 结算方式，客户欠款 */
    public static final long SETTLEMENT_TYPE_CUSTOMER_DEBT = 7;

    /** 结算方式，欠供应商款 */
    public static final long SETTLEMENT_TYPE_DEBT_TO_VENDOR = 8;

    /** 结算方式，冲员工结款 */
    public static final long SETTLEMENT_TYPE_REVERSE_EMPLOYEE_DEBT = 9;

    /** MySQL 标识符引用字符 */
    public static final String IDENTIFIER_QUOTE_CHARACTER = "`";

    /** 系统预置用户易嘉 id */
    public static final Long YJ_USER_ID = 1L;

    /** 系统预置用户名称 */
    public static final String YJ_USER_NAME = "预置用户名称";

    /** 银行账户-基本户 id */
    public static final long BASIC_DEPOSIT_ACCOUNT_ID = 1;
    
    public static enum FILTER_ROLE{ 
    	/**
    	 * 按当前用户(userId)过滤操作的数据行
    	 */
    	Creator,
    	/**
    	 * 按当前组织(orgId)过滤操作的数据行
    	 */
    	Org,
    	/**
    	 * 按当前应用(appId)过滤操作的数据行
    	 */
    	App,
    	/**
    	 * 不限制数据行
    	 */
    	Full
    }

}
