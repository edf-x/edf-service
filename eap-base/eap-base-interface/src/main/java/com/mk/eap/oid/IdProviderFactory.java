package com.mk.eap.oid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.eap.utils.PropertyUtil;
import com.mk.eap.utils.StringUtil;



/**
 * @Title:       IdProviderFactory.java
 * @Package:     com.mk.acm.oid
 * @Description: ID生成器工厂
 * @author yxq
 */
public class IdProviderFactory
{
	public static final String ID_PROVIDER_TYPE = "idtype";
	public static final String ID_PROVIDER_CLASS = "idproviderclass";
	public static final String IDTYPE_UUID = "uuid";
	public static final String IDTYPE_REDIS = "redis";
	public static final String IDTYPE_SNOWFLAKE = "snowflake";
	private static Logger logger = LoggerFactory.getLogger(IdProviderFactory.class);

    public IdProviderFactory()
    {
    }

    public static IOidProvider getIdProvider()
    {
		String idProviderClass = PropertyUtil.getPropertyByKey(ID_PROVIDER_CLASS);
		String genIdType = PropertyUtil.getPropertyByKey(ID_PROVIDER_TYPE);


		
		IOidProvider provider = findProvider(idProviderClass, genIdType);
		return provider;
    }

    public static IOidProvider getIdProvider(String extenstionType, String providerType)
    {
        IOidProvider provider = findProvider(extenstionType, providerType);
        return provider;
    }

	private static IOidProvider findProvider(String extenstionType, String idProviderClass) {
		IOidProvider provider = null;
		if (!StringUtil.isEmtryStr(extenstionType))
			try {
				provider = (IOidProvider) Class.forName(extenstionType).newInstance();
			} catch (Exception e) {
				logger.error("create custom IOidProvider error! please check config file!", e);
			}
		else if (IDTYPE_UUID.equalsIgnoreCase(idProviderClass) || StringUtil.isEmtryStr(idProviderClass))
			provider = new DefaultIdProvider();
		else if (IDTYPE_SNOWFLAKE.equalsIgnoreCase(idProviderClass))
		{
			String workerIdStr = PropertyUtil.getPropertyByKey(SnowFlakeProvider.OID_WORKID);
			System.setProperty(SnowFlakeProvider.OID_WORKID, workerIdStr);//设置workID
			provider = new SnowFlakeProvider();
		}
		return provider;
	}

}
