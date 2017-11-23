package com.mk.eap.oid;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:       DefaultIdProvider.java
 * @Package:     com.mk.acm.oid
 * @Description: ID生成器(UUID)
 * @author yxq
 */
public class DefaultIdProvider
    implements IOidProvider
{
    private Logger logger;
    
    public DefaultIdProvider()
    {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public String generatorID(String module)
    {
        String result = UUID.randomUUID().toString();
        logger.info("get primary key form uuid, the value is {}.", result);
        return result;
    }
}
