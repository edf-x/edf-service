
package com.mk.eap.entity.common;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * 单据审核操作 sql 生成实现类
 * @author gaoxue
 *
 */
public class VoucherAuditProvider extends MapperTemplate {

    public VoucherAuditProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 单据审核更新，审核人 {@code auditorId}，审核人姓名 {@code auditorName}，审核时间 {@code auditTime}，单据状态 {@code status}，凭证 {@code docId}
     * @param ms
     * @return
     */
    public String auditUpdate(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumnsOfAuditUpdate(entityClass));
        sql.append(SqlHelper.wherePKColumnsWithTs(entityClass));
        return sql.toString();
    }

    /**
     * 单据反审核更新，审核人 {@code auditorId}，审核人姓名 {@code auditorName}，审核时间 {@code auditTime}，单据状态 {@code status}，凭证 {@code docId}
     * @param ms
     * @return
     */
    public String unauditUpdate(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumnsOfUnauditUpdate(entityClass));
        sql.append(SqlHelper.wherePKColumnsWithTs(entityClass));
        return sql.toString();
    }

}
