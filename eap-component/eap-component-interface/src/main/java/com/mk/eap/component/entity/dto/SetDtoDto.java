package com.mk.eap.component.entity.dto;

import com.mk.eap.base.DTO;

import java.sql.Timestamp;

/**
 * 实体类档案 dto 实体定义类
 * @author gaoxue
 *
 */
public class SetDtoDto extends DTO {

    private static final long serialVersionUID = 932099963548427758L;

    /** id */
    private Long id;

    /** 类名称 */
    private String name;

    /** 类全名称 */
    private String fullName;

    /** 标题 */
    private String title;

    /** vo 类名称 */
    private String voName;

    /** vo 类全名称 */
    private String voFullName;

    /** 实体为单据时，单据类型 id 对应枚举 voucherType */
    private Long voucherTypeId;

    /** 实体为单据时，单据编码前缀 */
    private String codePrefix;

    /** 时间戳 */
    private Timestamp ts;

    /**
     * 获取id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取类名称
     * @return 类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类名称
     * @param name 类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类全名称
     * @return 类全名称
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置类全名称
     * @param fullName 类全名称
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 获取标题
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取vo 类名称
     * @return vo 类名称
     */
    public String getVoName() {
        return voName;
    }

    /**
     * 设置vo 类名称
     * @param voName vo 类名称
     */
    public void setVoName(String voName) {
        this.voName = voName;
    }

    /**
     * 获取vo 类全名称
     * @return vo 类全名称
     */
    public String getVoFullName() {
        return voFullName;
    }

    /**
     * 设置vo 类全名称
     * @param voFullName vo 类全名称
     */
    public void setVoFullName(String voFullName) {
        this.voFullName = voFullName;
    }

    /**
     * 获取实体为单据时，单据类型 id 对应枚举 voucherType
     * @return 实体为单据时，单据类型 id 对应枚举 voucherType
     */
    public Long getVoucherTypeId() {
        return voucherTypeId;
    }

    /**
     * 设置实体为单据时，单据类型 id 对应枚举 voucherType
     * @param voucherTypeId 实体为单据时，单据类型 id 对应枚举 voucherType
     */
    public void setVoucherTypeId(Long voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    /**
     * 获取实体为单据时，单据编码前缀
     * @return 实体为单据时，单据编码前缀
     */
    public String getCodePrefix() {
        return codePrefix;
    }

    /**
     * 设置实体为单据时，单据编码前缀
     * @param codePrefix 实体为单据时，单据编码前缀
     */
    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
    }

    /**
     * 获取时间戳
     * @return 时间戳
     */
    public Timestamp getTs() {
        return ts;
    }

    /**
     * 设置时间戳
     * @param ts 时间戳
     */
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

}
