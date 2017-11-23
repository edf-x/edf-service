package com.mk.eap.voucher.vo;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 单据明细 vo 基类
 * @author gaoxue
 *
 */
public abstract class VoucherDetail extends VoucherBase {

    private static final long serialVersionUID = -7420862383647102582L;

    /** 对应单据表头 id */
    @Id
    @Column(updatable = false)
    private Long voucherId;

    /** 顺序号 */
    private Integer orderNumber;

    /**
     * 获取单据表头 id
     * @return 单据表头 id
     */
    public Long getVoucherId() {
        return voucherId;
    }

    /**
     * 设置单据表头 id
     * @param voucherId 单据表头 id
     */
    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * 获取顺序号
     * @return 顺序号
     */
    public Integer getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置顺序号
     * @param orderNumber 顺序号
     */
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

}
