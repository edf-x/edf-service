package com.mk.eap.voucher.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据表头实体 dto 基类
 * @author gaoxue
 *
 * @param <D> 单据明细实体 dto 类型
 */
public abstract class VoucherDto<D extends VoucherDetailDto> extends VoucherBaseDto {

    private static final long serialVersionUID = -8971123409094940026L;

    /** 编码 */
    private String code;

    /** 更新人 id 对应表 set_person 的 userid */
    private Long updator;

    /** 单据明细列表 没有明细时为空 List */
    private List<D> details = new ArrayList<>();

    /** 编码是否手工修改过 */
    private Boolean isCodeModify = false;

    /** 编码前缀 */
    private String codePrefix;

    /**
     * 获取编码
     * @return 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取更新人 id 对应表 set_person 的 userid
     * @return 更新人 id 对应表 set_person 的 userid
     */
    public Long getUpdator() {
        return updator;
    }

    /**
     * 设置更新人 id 对应表 set_person 的 userid
     * @param updator 更新人 id 对应表 set_person 的 userid
     */
    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    /**
     * 获取单据明细列表 没有明细时为空 List
     * @return 单据明细列表 没有明细时为空 List
     */
    public List<D> getDetails() {
        return details;
    }

    /**
     * 设置单据明细列表
     * @param details 单据明细列表 为 null 时设置为空 List
     */
    public void setDetails(List<D> details) {
        if (details == null) {
            this.details = new ArrayList<>();
        } else {
            this.details = details;
        }
    }

    /**
     * 获取编码是否手工修改过
     * @return 编码是否手工修改过
     */
    @JsonIgnore
    public Boolean getIsCodeModify() {
        return isCodeModify;
    }

    /**
     * 设置编码是否手工修改过
     * @param isCodeModify 编码是否手工修改过
     */
    @JsonProperty
    public void setIsCodeModify(Boolean isCodeModify) {
        this.isCodeModify = isCodeModify;
    }

    /**
     * 获取编码前缀
     * @return 编码前缀
     */
    @JsonIgnore
    public String getCodePrefix() {
        return codePrefix;
    }

    /**
     * 设置编码前缀
     * @param codePrefix 编码前缀
     */
    @JsonProperty
    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
    }

}
