package com.mk.eap.voucher.dao;

import com.mk.eap.entity.common.VoucherAuditMapper;
import com.mk.eap.voucher.vo.VoucherWithEnclosure;

/**
 * 
 * @author gaoxue
 *
 * @param <V>
 */
public interface VoucherWithEnclosureMapper<V extends VoucherWithEnclosure> extends VoucherMapper<V>, VoucherAuditMapper<V> {

}
