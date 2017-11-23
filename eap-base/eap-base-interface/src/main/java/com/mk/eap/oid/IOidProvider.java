package com.mk.eap.oid;

/**
 * @Title:       IOidProvider.java
 * @Package:     com.mk.acm.oid
 * @Description: ID生成器接口
 * @author yxq
 */
public interface IOidProvider
{
    public abstract String generatorID(String s);
}