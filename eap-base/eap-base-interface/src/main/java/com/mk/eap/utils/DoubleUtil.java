package com.mk.eap.utils;

import com.mk.eap.constant.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @Description: Double类型相关
 * @author : gaoen
 *
 */
public class DoubleUtil {
	public static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * Double 类型格式化为 N 位小数
	 * 
	 * @param value
	 *            进行格式化的 Double 数值
	 * @param null2Zero
	 *            Null是否返回0
	 * @param scale
	 *            小数位数
	 * @return 格式化后的double
	 */
	public static Double formatDouble(Double value, boolean null2Zero, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		if (value == null) {
			if (null2Zero) {
				return 0.00;
			} else {
				return null;
			}
		}
		BigDecimal bigDecimal = BigDecimal.valueOf(value.doubleValue());
		bigDecimal = bigDecimal.setScale(scale, RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}

	/**
	 * Double 类型格式化为 2 位小数
	 * 
	 * @param value
	 *            进行格式化的 Double 数值
	 * @param null2Zero
	 *            Null是否返回0
	 * @return 格式化后的double
	 */
	public static Double formatDoubleScale2(Double value, boolean null2Zero) {
		return formatDouble(value, null2Zero, 2);
	}

	/**
	 * Double 类型格式化为 2 位小数, Null是否返回0
	 * 
	 * @param value
	 *            进行格式化的 Double 数值
	 * @return 格式化后的double
	 */
	public static Double formatDoubleScale2(Double value) {
		return formatDoubleScale2(value, true);
	}

	/**
	 * Double 类型保留几位小数比较，null 视为 0.0 进行比较
	 * 
	 * @param num1
	 * @param num2
	 * @param scale
	 *            保留几位小数
	 * @return -1,0,1 num1 小于，等于，大于 num2
	 * @see BigDecimal#compareTo(BigDecimal)
	 */
	public static int compareTo(Double num1, Double num2, int scale) {
		if (num1 == null) {
			num1 = 0.0;
		}
		if (num2 == null) {
			num2 = 0.0;
		}
		BigDecimal bigDecimal1 = BigDecimal.valueOf(num1).setScale(scale, RoundingMode.HALF_UP);
		BigDecimal bigDecimal2 = BigDecimal.valueOf(num2).setScale(scale, RoundingMode.HALF_UP);
		return bigDecimal1.compareTo(bigDecimal2);
	}

	/**
	 * Double 类型保留两位小数比较，null 视为 0.0 进行比较
	 * 
	 * @param num1
	 * @param num2
	 * @return -1,0,1 num1 小于，等于，大于 num2
	 * @see BigDecimal#compareTo(BigDecimal)
	 */
	public static int compareTo(Double num1, Double num2) {
		return compareTo(num1, num2, 2);
	}

	/**
	 * Double 类型加法，当加数为空时直接返回被加数，使用 {@link BigDecimal} 进行计算，null 视为 0.0 进行计算
	 * 
	 * @param summand
	 *            被加数
	 * @param augends
	 *            加数
	 * @return 计算结果
	 */
	public static Double add(Double summand, Double... augends) {
		int length = augends.length;
		if (summand == null) {
			summand = 0.0;
		}
		if (length == 0) {
			return formatDoubleScale2(summand);
		}
		BigDecimal result = BigDecimal.valueOf(summand.doubleValue());
		for (int index = 0; index < length; index++) {
			if (augends[index] == null) {
				continue;
			}
			BigDecimal temp = BigDecimal.valueOf(augends[index].doubleValue());
			result = result.add(temp);
		}
		return formatDoubleScale2(result.doubleValue());
	}

	/**
	 * Double 类型减法，当减数为空时直接返回被减数，使用 {@link BigDecimal} 进行计算，null 视为 0.0 进行计算
	 * 
	 * @param minuend
	 *            被减数
	 * @param subtrahends
	 *            减数
	 * @return
	 */
	public static Double sub(Double minuend, Double... subtrahends) {
		int length = subtrahends.length;
		if (minuend == null) {
			minuend = 0.0;
		}
		if (length == 0) {
			return formatDoubleScale2(minuend);
		}
		BigDecimal result = BigDecimal.valueOf(minuend.doubleValue());
		for (int index = 0; index < length; index++) {
			if (subtrahends[index] == null) {
				continue;
			}
			BigDecimal temp = BigDecimal.valueOf(subtrahends[index]);
			result = result.subtract(temp);
		}
		return formatDoubleScale2(result.doubleValue());
	}

	/**
	 * Double 类型乘法，使用 {@link BigDecimal} 进行计算，精度保留两位小数
	 * 
	 * @param multiplicand
	 *            被乘数
	 * @param multiplicators
	 *            乘数
	 * @return
	 */
	public static Double multi(Double multiplicand, Double... multiplicators) {
		int length = multiplicators.length;
		if (null == multiplicand) {
			return null;
		}
		if (length == 0) {
			return formatDoubleScale2(multiplicand);
		}
		BigDecimal result = BigDecimal.valueOf(multiplicand);
		for (int index = 0; index < length; index++) {
			if (null == multiplicators[index]) {
				return null;
			}
			BigDecimal temp = BigDecimal.valueOf(multiplicators[index]);
			result = result.multiply(temp);
		}
		return formatDoubleScale2(result.doubleValue());
	}

	/**
	 * Double 类型除法，当除数为空时直接返回被除数，被除数为 null 返回 null，除数存在 null 或者 0.0 返回 null，使用
	 * {@link BigDecimal} 进行计算，精度保留两位小数
	 * 
	 * @param dividend
	 *            被除数
	 * @param divisors
	 *            除数
	 * @return
	 */
	public static Double div(Double dividend, Double... divisors) {
		int length = divisors.length;
		if (dividend == null) {
			return null;
		}
		if (length == 0) {
			return formatDoubleScale2(dividend);
		}

		BigDecimal result = BigDecimal.valueOf(dividend.doubleValue());
		for (int index = 0; index < length; index++) {
			if (isNullOrZero(divisors[index])) {
				return null;
			}
			BigDecimal temp = BigDecimal.valueOf(divisors[index]);
			result = result.divide(temp, 2, RoundingMode.HALF_UP);
		}
		return formatDoubleScale2(result.doubleValue());
	}

	/**
	 * Double 类型数据是否为 null 或者为 0
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrZero(Double value) {
		if (value == null) {
			return true;
		}
		BigDecimal bigDecimal = BigDecimal.valueOf(value.doubleValue());
		return bigDecimal.compareTo(BigDecimal.ZERO) == 0;
	}
	/**
	 * Double 类型数据是否为 null 
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(Double value) {
		if (value == null) {
			return true;
		}
		return false;
	}
	

    /**
     * Double 类型数据是否大于 0
     * @param value 
     * @return {@code value} 为 {@code null} 返回 false
     */
    public static boolean isGreatThenZero(Double value) {
        return compareTo(value, 0.0) > 0;
    }

    /**
     * Double 类型数据是否小于 0
     * @param value
     * @return {@code value} 为 {@code null} 返回 false
     */
    public static boolean isLessThenZero(Double value) {
        return compareTo(value, 0.0) < 0;
    }

	/**
     * Double类型 0.0 值转换成 null
     * @param value
     * @return
     */
    public static Double zero2null(Double value) {
        if (compareTo(value,0.0) == 0) {
            return null;
        }
        return value;
    }

    /**
     * Double类型 null 值转换成 0.0
     * @param value
     * @return
     */
    public static Double null2Zero(Double value) {
        if (value == null) {
            return 0.0;
        }
        return value;
    }

    /**
     * 返回 Double 类型绝对值，null 返回 0.0
     * @param value
     * @return
     */
    public static Double abs(Double value) {
        if (value == null) {
            return 0.0;
        }
        return Math.abs(value);
    }

    public static String format2String(Double value, boolean null2Zero, int scale) {
        if (value == null) {
            if (!null2Zero) {
                return "";
            }
            value = 0.0;
        }
        NumberFormat nFormat = NumberFormat.getNumberInstance();
        nFormat.setRoundingMode(RoundingMode.HALF_UP);
        nFormat.setMaximumFractionDigits(scale);
        nFormat.setMinimumFractionDigits(scale);
        return nFormat.format(value);
    }

    /**
     * 格式化数量的小数位精度，小数位精度参考{@link CommonConst.QUANTITY_DECIMAL_SCALE}
     * @param quantity 数量
     * @return 格式化后的数量
     */
    public static Double formatQuantity(Double quantity) {
        Double result = formatDouble(quantity, false, CommonConst.QUANTITY_DECIMAL_SCALE);
        return result;
    }
    
    /**
     * 格式化数量的小数位精度并转换成字符串形式，小数位精度参考{@link CommonConst.QUANTITY_DECIMAL_SCALE}
     * @param quantity 数量
     * @return 格式化后的数量字符串，null 返回空字符串
     */
    public static String formatQuantity2String(Double quantity) {
        return format2String(quantity, false, CommonConst.QUANTITY_DECIMAL_SCALE);
    }

    /**
     * 格式化单价的小数位精度，小数位精度参考{@link CommonConst.PRICE_DECIMAL_SCALE}
     * @param price 单价
     * @return 格式化后的单价
     */
    public static Double formatPrice(Double price) {
        Double result = formatDouble(price, false, CommonConst.PRICE_DECIMAL_SCALE);
        return result;
    }
    
    /**
     * 格式化单价的小数位精度并转换成字符串形式，小数位精度参考{@link CommonConst.PRICE_DECIMAL_SCALE}
     * @param price 单价
     * @return 格式化后的单价字符串，null 返回空字符串
     */
    public static String formatPrice2String(Double price) {
        return format2String(price, false, CommonConst.PRICE_DECIMAL_SCALE);
    }

    /**
     * 格式化金额的小数位精度，小数位精度参考{@link CommonConst.AMOUNT_DECIMAL_SCALE}
     * @param amount 金额
     * @return 格式化后的金额
     */
    public static Double formatAmount(Double amount) {
        Double result = formatDouble(amount, false, CommonConst.AMOUNT_DECIMAL_SCALE);
        return result;
    }
    
    /**
     * 格式化金额的小数位精度并转换成字符串形式，小数位精度参考{@link CommonConst.AMOUNT_DECIMAL_SCALE}
     * @param amount 金额
     * @return 格式化后的金额字符串，null 返回空字符串
     */
    public static String formatAmount2String(Double amount) {
        return format2String(amount, false, CommonConst.AMOUNT_DECIMAL_SCALE);
    }

    /**
     * 格式化数量的小数位精度，小数位精度参考{@link CommonConst.QUANTITY_DECIMAL_SCALE}
     * @param quantity 数量
     * @param null2Zero null是否返回0
     * @return 格式化后的数量
     */
    public static Double formatQuantity(Double quantity, boolean null2Zero) {
        Double result = formatDouble(quantity, null2Zero, CommonConst.QUANTITY_DECIMAL_SCALE);
        return result;
    }
    
    /**
     * 格式化数量的小数位精度并转换成字符串形式，小数位精度参考{@link CommonConst.QUANTITY_DECIMAL_SCALE}
     * @param quantity 数量
     * @param null2Zero null是否返回0
     * @return 格式化后的数量字符串
     */
    public static String formatQuantity2String(Double quantity, boolean null2Zero) {
        return format2String(quantity, null2Zero, CommonConst.QUANTITY_DECIMAL_SCALE);
    }

    /**
     * 格式化单价的小数位精度，小数位精度参考{@link CommonConst.PRICE_DECIMAL_SCALE}
     * @param price 单价
     * @param null2Zero null是否返回0
     * @return 格式化后的单价
     */
    public static Double formatPrice(Double price, boolean null2Zero) {
        Double result = formatDouble(price, null2Zero, CommonConst.PRICE_DECIMAL_SCALE);
        return result;
    }
    
    /**
     * 格式化单价的小数位精度并转换成字符串形式，小数位精度参考{@link CommonConst.PRICE_DECIMAL_SCALE}
     * @param price 单价
     * @param null2Zero null是否返回0
     * @return 格式化后的单价字符串，null 返回空字符串
     */
    public static String formatPrice2String(Double price, boolean null2Zero) {
        return format2String(price, null2Zero, CommonConst.PRICE_DECIMAL_SCALE);
    }

    /**
     * 格式化金额的小数位精度，小数位精度参考{@link CommonConst.AMOUNT_DECIMAL_SCALE}
     * @param amount 金额
     * @param null2Zero null是否返回0
     * @return 格式化后的金额
     */
    public static Double formatAmount(Double amount, boolean null2Zero) {
        Double result = formatDouble(amount, null2Zero, CommonConst.AMOUNT_DECIMAL_SCALE);
        return result;
    }
    
    /**
     * 格式化金额的小数位精度并转换成字符串形式，小数位精度参考{@link CommonConst.AMOUNT_DECIMAL_SCALE}
     * @param amount 金额
     * @param null2Zero null是否返回0
     * @return 格式化后的金额字符串，null 返回空字符串
     */
    public static String formatAmount2String(Double amount, boolean null2Zero) {
        return format2String(amount, null2Zero, CommonConst.AMOUNT_DECIMAL_SCALE);
    }

}
