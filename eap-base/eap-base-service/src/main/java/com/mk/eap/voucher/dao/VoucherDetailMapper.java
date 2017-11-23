package com.mk.eap.voucher.dao;

import com.mk.eap.entity.dao.EntityMapper;
import com.mk.eap.voucher.vo.VoucherDetail;

/**
 * 单据明细实体数据库访问接口基类定义
 * @author gaoxue
 *
 * @param <V> 单据明细实体 vo 对象类型
 */
public interface VoucherDetailMapper<V extends VoucherDetail> extends EntityMapper<V> {

}
