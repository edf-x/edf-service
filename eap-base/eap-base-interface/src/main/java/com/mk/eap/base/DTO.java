package com.mk.eap.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mk.eap.utils.BeanExtendUtils;

public class DTO implements Serializable {

    private static final long serialVersionUID = -7030510712004741358L;

    private final static Logger logger = LoggerFactory.getLogger(DTO.class);

    /** 时间戳字段名称 */
    public static final String TS_FILED_NAME = "ts";

    /** 属性为 null 的字段是否更新到数据库，默认 false */
//    @JsonIgnore
    private boolean nullUpdate = false;

    /**
     * 是否存在对应的属性
     * @param field 属性名称
     * @return
     */
    public boolean existField(String field) {
        Class<?> clazz = getClass();
        while (clazz != Object.class) {
            try {
                clazz.getDeclaredField(field);
                return true;
            } catch (NoSuchFieldException ex) {
                // log.error(ex.toString());
                clazz = clazz.getSuperclass();
            } catch (SecurityException ex) {
                // log.error(ex.toString());
                break;
            }
        }
        return false;
    }

    /**
     * 是否存在时间戳字段 {@code ts}
     * @return
     */
    public boolean existTs() {
        return existField(TS_FILED_NAME);
    }

    public Object getFieldValue(String field) {
        String pre = field.substring(0, 1);
        String methodName = "get" + pre.toUpperCase() + field.substring(1);
        Class<?> clazz = getClass();
        while (clazz != Object.class) {
            try {
                // clazz.getMethod(methodName);
                Method method = clazz.getDeclaredMethod(methodName);
                return method.invoke(this);
            } catch (NoSuchMethodException ex) {
                clazz = clazz.getSuperclass();
                // log.error(ex.toString());
            } catch (Exception ex) {
                // log.error(ex.toString());
                break;
            }
        }
        return null;
    }

    public <TValue> void setFieldValue(String field, TValue value) {
        String pre = field.substring(0, 1);
        String methodName = "set" + pre.toUpperCase() + field.substring(1);
        Class<?> clazz = getClass();
        while (clazz != Object.class) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, clazz.getDeclaredField(field).getType());
                method.invoke(this, value);
                break;
            } catch (NoSuchMethodException | NoSuchFieldException ex) {
                clazz = clazz.getSuperclass();
                // log.error(ex.toString());
            } catch (Exception ex) {
                // log.error(ex.toString());
                break;
            }
        }
    }

    /**
     * dto 实体转换为 vo 实体
     * @param clazz vo 实体类型
     * @return vo 实体
     */
    public <TVo extends VO> TVo toVo(Class<TVo> clazz) {
        TVo result;
        try {
            result = clazz.newInstance();
            // TODO gaoxue date 不能转换，效率问题
            BeanExtendUtils.copyProperties(this, result);
            return result;
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.error(ex.getMessage());
            // Vo should have constructor, this should not happen
        }
        return null;
    }

    /**
     * dto 实体转换为 vo 实体
     * @param clazz vo 实体类型
     * @param propertyList 需要转换的属性
     * @return vo 实体
     */
    public <TVo extends VO> TVo toVo(Class<TVo> clazz, List<String> propertyList) {
        TVo result;
        try {
            result = clazz.newInstance();
            // TODO gaoxue date 不能转换，效率问题
            BeanExtendUtils.copyProperties(this, result, propertyList);
            return result;
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.error(ex.getMessage());
            // Vo should have constructor, this should not happen
        }
        return null;
    }

    /**
     * vo 实体转换为 dto 实体
     * @param vo 需要转换的 vo 实体
     * @return dto 实体
     */
    @SuppressWarnings("unchecked")
    public <TVo extends VO, TDto extends DTO> TDto fromVo(TVo vo) {
        if (vo == null) {
            return null;
        }
        TDto result;
        try {
            result = (TDto) getClass().newInstance();
            BeanExtendUtils.copyProperties(vo, result);
            return result;
        } catch (InstantiationException | IllegalAccessException ex) {
            logger.error(ex.getMessage());
            // Dto should have constructor, this should not happen
        }
        return null;
    }

    public <TVo extends VO, TDto extends DTO> List<TDto> fromVo(List<TVo> vos) {
        List<TDto> result = new ArrayList<>();
        for (TVo vo : vos) {
            TDto dto = fromVo(vo);
            if (dto != null) {
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 获取属性为 null 的字段是否更新到数据库，默认 false
     * @return 属性为 null 的字段是否更新到数据库，默认 false
     */
    public boolean getNullUpdate() {
        return nullUpdate;
    }

    /**
     * 设置属性为 null 的字段是否更新到数据库，默认 false
     * @param nullUpdate 属性为 null 的字段是否更新到数据库，默认 false
     */
    public void setNullUpdate(boolean nullUpdate) {
        this.nullUpdate = nullUpdate;
    }

}
