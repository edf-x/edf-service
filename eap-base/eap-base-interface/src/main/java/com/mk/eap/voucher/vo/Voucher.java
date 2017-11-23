package com.mk.eap.voucher.vo;

/**
 * 单据表头 vo 基类
 * @author gaoxue
 *
 */
public abstract class Voucher extends VoucherBase {

    private static final long serialVersionUID = -6899783849840291704L;

    /** 编码 */
    private String code;

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

}
