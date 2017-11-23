package com.mk.eap.voucher.dao;

import com.mk.eap.entity.dao.EntityMapper;
import com.mk.eap.voucher.vo.Voucher;

/**
 * 单据实体数据库访问接口基类定义
 * @author gaoxue
 * 
 * @param <V> 单据实体 vo 对象类型
 */
public interface VoucherMapper<V extends Voucher> extends EntityMapper<V> {

}
