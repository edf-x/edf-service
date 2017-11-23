package com.mk.eap.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mk.eap.base.DTO;

import java.io.Serializable;
import java.util.Date;

public class OrgDto extends DTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6552841336864133774L;

	private Long id;           //主键ID

    private String name;      	  //企业机构名称

    private Integer version;      //版本类型(0：内部版；1：试用版；2：软件版；3：服务版)
    
    private String versionName;	  // 版本名称

    private Integer orgType;      //标志(1:贷账公司;2:个人用户;3:个人贷账)
    
    private Boolean isInit;      //是否初始化

    private Date ts;
     
	private Long spOrgId;           //创建当前企业的服务商企业ID。
    

	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Long bizdataId;	//值为1，2...10000 时该对应对应的webapi域名为：api1.rrtimes.com,api2.rrtimes.com ... api10000.rrtimes.com
    
    private String apiDomain;//webapi对应的域名。
    
    private String domain;	//对应的域名

    private Long creator;
    
    private String creatorName;      //创建者姓名
    
    private String creatorMobile;      //创建者手机

    private Long users;		//激活用户数
    
    private String businessLicence;

    private String memo;
    
    private String createIP;
    /**  行业性质
     * "1"	"工业"
     * "2"	"商贸"
     * "3"	"服务"
     * "4"	"信息技术" */
    private Long industry;
    /**  会计准则*/
    private Long accountingStandards;
    /**  会计启用年*/
    private String enabledYear;
    /**  会计启用月*/
    private String enabledMonth;
    /**  账号  */
    private Integer accountNum;
    /** 识别码 */
    private String identificationCode;
    
    private Long appId;		//应用Id
    
    private String appName = null; 		// oem 品牌名称
     
    private Integer status;      //代账公司管理使用(0 待审核状态，1审核通过状态)
    
    private String spStatus;	 // 服务商账套状态(0：未审核 ;2：正常)
    
    private String orgStatus;	 // 企业账套状态(1：试用 ;2：正式： 2;3：过期.)
    
    // 来源(1: 来自服务商创建; 2: 来自企业端创建)
  	private Integer source;
  	
  	private Integer orderStatus;	// 是否存在未支付完成订单(0 不存在，大于 0 则表示存在未完成订单)
  	
  	private Boolean weatherNewBuy;	// 是否新购买订单
    

	/** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastLoginTime;
    
    /** 服务截止日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireTime;
    
    /** 是否已经过截止日期 */
    private Boolean expired;
    
    /** 纳税人身份
     * "41"	"一般纳税人"
     * "42"	"小规模纳税人"  */ 
	private Long vatTaxpayer;
    
	/** 用户ID */
    private Long userId;
    
    /** 纳税人识别号 */
    private String unifiedSocialCreditCode;

    /*
     * 流水账明细条数
     */
    private Integer totalReceipt;
    /*
     * 凭证明细条数
     */
    private Integer totalJournal;
    /*
     * 工资单明细条数
     */
    private Integer totalWage;
    /*
     * 人员数
     */
    private Integer totalPerson;
    /*
     * 操作次数
     */
    private Integer totalOperation;
    /*
     * 代账公司申请的账套数
     */
    private String requiredOrgCount;
    /*
     * 批准的代账公司账套数上限
     */
    private Integer maxOrgCount;
    /*
     * 服务商企业创建的一般纳税人企业数
     */
    private Integer totalGeneralOrg;
    /*
     * 服务商企业创建的小规模纳税人企业数
     */
    private Integer totalSmallOrg;
    /*
     * 服务商企业创建的企业数
     */
    private Integer totalOrg;

    /* 外部系统的ID*/
    private String externalId;
    
    /* 行业代码（国标）*/
    private String industryCode;
    
    private String vatTaxpayerName;	// 纳税人身份中文名称
    

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

    
    public Integer getTotalGeneralOrg() {
		return totalGeneralOrg;
	}

	public void setTotalGeneralOrg(Integer totalGeneralOrg) {
		this.totalGeneralOrg = totalGeneralOrg;
	}

	public Integer getTotalSmallOrg() {
		return totalSmallOrg;
	}

	public void setTotalSmallOrg(Integer totalSmallOrg) {
		this.totalSmallOrg = totalSmallOrg;
	}

	public Integer getTotalOrg() {
		return totalOrg;
	}

	public void setTotalOrg(Integer totalOrg) {
		this.totalOrg = totalOrg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getRequiredOrgCount() {
		return requiredOrgCount;
	}

	public void setRequiredOrgCount(String requiredOrgCount) {
		this.requiredOrgCount = requiredOrgCount;
	}

	public Integer getMaxOrgCount() {
		return maxOrgCount;
	}

	public void setMaxOrgCount(Integer maxOrgCount) {
		this.maxOrgCount = maxOrgCount;
	}

	public Integer getTotalReceipt() {
		return totalReceipt;
	}

	public void setTotalReceipt(Integer totalReceipt) {
		this.totalReceipt = totalReceipt;
	}

	public Integer getTotalJournal() {
		return totalJournal;
	}

	public void setTotalJournal(Integer totalJournal) {
		this.totalJournal = totalJournal;
	}

	public Integer getTotalWage() {
		return totalWage;
	}

	public void setTotalWage(Integer totalWage) {
		this.totalWage = totalWage;
	}

	public Integer getTotalPerson() {
		return totalPerson;
	}

	public void setTotalPerson(Integer totalPerson) {
		this.totalPerson = totalPerson;
	}

	public Integer getTotalOperation() {
		return totalOperation;
	}

	public void setTotalOperation(Integer totalOperation) {
		this.totalOperation = totalOperation;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVatTaxpayer() {
		return vatTaxpayer;
	}

	public void setVatTaxpayer(Long vatTaxpayer) {
		this.vatTaxpayer = vatTaxpayer;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 0：内部版；1：试用版；2：软件版；3：服务版
     * @return
     */
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getBizdataId() {
        return bizdataId;
    }

    public void setBizdataId(Long bizdataId) {
        this.bizdataId = bizdataId;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence == null ? null : businessLicence.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public Boolean getIsInit() {
		return isInit;
	}

	public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIndustry() {
		return industry;
	}

	public void setIndustry(Long industry) {
		this.industry = industry;
	}

	public Long getAccountingStandards() {
		return accountingStandards;
	}

	public void setAccountingStandards(Long accountingStandards) {
		this.accountingStandards = accountingStandards;
	}

	public String getEnabledYear() {
		return enabledYear;
	}

	public void setEnabledYear(String enabledYear) {
		this.enabledYear = enabledYear;
	}

	public String getEnabledMonth() {
		return enabledMonth;
	}

	public void setEnabledMonth(String enabledMonth) {
		this.enabledMonth = enabledMonth;
	}

	public Integer getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(Integer accountNum) {
		this.accountNum = accountNum;
	}

	public String getIdentificationCode() {
		return identificationCode;
	}

	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public String getCreateIP() {
		return createIP;
	}

	public void setCreateIP(String createIP) {
		this.createIP = createIP;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the creatorMobile
	 */
	public String getCreatorMobile() {
		return creatorMobile;
	}

	/**
	 * @param creatorMobile the creatorMobile to set
	 */
	public void setCreatorMobile(String creatorMobile) {
		this.creatorMobile = creatorMobile;
	}

	/**
	 * @return the users
	 */
	public Long getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Long users) {
		this.users = users;
	}

	public String getApiDomain() {
		return apiDomain;
	}

	public void setApiDomain(String apiDomain) {
		this.apiDomain = apiDomain;
	}

		
	
    public Long getSpOrgId() {
		return spOrgId;
	}

	public void setSpOrgId(Long spOrgId) {
		this.spOrgId = spOrgId;
	}
	/*
    * 服务商剩余账套数量
    */
	private Integer availableOrgCount;

	public Integer getAvailableOrgCount() {
		return availableOrgCount;
	}

	public void setAvailableOrgCount(Integer availableOrgCount) {
		this.availableOrgCount = availableOrgCount;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUnifiedSocialCreditCode() {
		return unifiedSocialCreditCode;
	}

	public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
		this.unifiedSocialCreditCode = unifiedSocialCreditCode;
	}

	public String getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}

	public Boolean getWeatherNewBuy() {
		return weatherNewBuy;
	}

	public void setWeatherNewBuy(Boolean weatherNewBuy) {
		this.weatherNewBuy = weatherNewBuy;
	}

	public String getVatTaxpayerName() {
		return vatTaxpayerName;
	}

	public void setVatTaxpayerName(String vatTaxpayerName) {
		this.vatTaxpayerName = vatTaxpayerName;
	}
	
}