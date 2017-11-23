package com.mk.eap.oid;

/**
 * @Title:       IDGenerator.java
 * @Package:     com.mk.rap.oid
 * @Description: ID生成器
 * @author yxq
 */
public class IDGenerator
{
    public static final IOidProvider idProvider = IdProviderFactory.getIdProvider();
    
    private IDGenerator()
    {
    }

    public static String generateObjectID(String module)
    {
        return idProvider.generatorID(module);
    }


}
