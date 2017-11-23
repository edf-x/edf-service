package com.mk.eap.base;

import java.util.Map;

/**
 * Created by shenxy on 7/6/17.
 *
 * 解析基础档案的接口
 */
public interface IBaseArchiveFinder {
    Object getBaseArchives(Long orgId, String name, Map<String, Object> params);

    Object getDefaultValue(Long orgId, String archiveName, String fieldName, Map<String, Object> params);

    Object create(Long orgId, String name, Map<String, Object> params);

    // 根据字段类型,填写一些特定依赖字段
    Map<String, Object> addDependentParams(String archiveName, Object dto, Map<String, Object> params);

    Object getBaseArchivesById(Long orgId, String archiveName, Map<String, Object> params);
}
