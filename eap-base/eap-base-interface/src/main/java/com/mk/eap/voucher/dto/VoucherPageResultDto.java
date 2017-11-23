package com.mk.eap.voucher.dto;

import com.mk.eap.base.DTO;
import com.mk.eap.entity.dto.PageResultDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据分页查询结果基类
 * @author gaox0
 *
 * @param <D> 单据 dto
 */
public class VoucherPageResultDto<D extends DTO> extends PageResultDto<D> {

    private static final long serialVersionUID = -4801924070870374709L;

    /** 列表设置信息 */
    private List<SetCardControlDto> columnSetting = new ArrayList<>();

    /**
     * 获取列表设置信息
     * @return 列表设置信息
     */
    public List<SetCardControlDto> getColumnSetting() {
        return columnSetting;
    }

    /**
     * 设置列表设置信息
     * @param columnSetting 列表设置信息
     */
    public void setColumnSetting(List<SetCardControlDto> columnSetting) {
        this.columnSetting = columnSetting;
    }

}
