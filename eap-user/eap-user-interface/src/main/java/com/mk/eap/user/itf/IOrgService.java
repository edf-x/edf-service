/*      						
 * Copyright 2016 Beijing RRTIMES, Inc. All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    			|  		Who  			|  		What  
 * 2016-05-03		| 	    zl 			| 	create the file                       
 */
package com.mk.eap.user.itf;

import com.mk.eap.annotation.ApiContext;
import com.mk.eap.base.BusinessException;
import com.mk.eap.user.dto.OrgDto;
import com.mk.eap.user.dto.OrgUser;
import com.mk.eap.user.dto.UserDto;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Title: ISetService.java
 * @Package: com.rrtimes.user.itf
 * @Description: 企业操作服务接口
 * 
 *               <p>
 *               企业操作服务接口
 *               </p>
 * 
 * @author zl
 * 
 */

@RequestMapping("/")
public interface IOrgService {

	/**
	 * 创建企业
	 *
	 * @return
	 */
	public boolean insert(OrgDto sysOrg) throws BusinessException;

	/**
	 * @api {POST} org/delete delete
	 * @apiName delete
	 * @apiGroup org
	 * @apiVersion 1.0.0
	 * @apiDescription 删除组
	 * @apiPermission anyone
	 *
	 * @apiParam {Object} json json
	 * @apiParamExample {json} 请求示例
	 *                  {"id":1562465016612864}
	 * @apiSuccessExample {json} 返回结果
	 *                    {"result":true,"value":true}
	 *
	 */
	@RequestMapping(value = "/org/delete")
	public boolean delete(@ApiContext("userId,appId") OrgDto sysOrg) throws BusinessException;

	/**
	 * 根据用户ID取得所有企业信息
	 * 
	 * @param sysUser
	 * @return
	 */
	public List<OrgDto> getSysOrgListByUserId(UserDto sysUser);

	/**
	 * 设置默认企业
	 * 
	 * @param sysOrgUser
	 * @return
	 */
	public boolean setIsDefaultOrgByUserIdByOrgId(OrgUser sysOrgUser);

	/**
	 * 根据ID查询企业
	 */
	public OrgDto findOrgById(Long id);

	/**
	 * 修改企业
	 */
	public boolean updateOrgByField(OrgDto sysOrg) throws BusinessException;

	/**
	 * 通过 id 查询 企业关系
	 */
	public OrgUser findOrgUserByIdOrgid(Long userId, Long orgId);

	/**
	 * 通过 id 查询 企业关系
	 */
	public OrgUser findOrgUserByMobileOrgid(String mobile, Long orgId);

	/**
	 * 修改状态清空时间
	 */
	public boolean updateStatusByUserIdOrgId(Long userId, Long orgId) throws BusinessException;

	/**
	 * 修改状态清空时间
	 */
	public boolean updateStatusByUserIdOrgId(Long userId, Long orgId, Byte status) throws BusinessException;

	/**
	 * 修改状态清空时间
	 *
	 */
	public boolean updateStatusByUserIdForForget(UserDto user) throws BusinessException;

	/**
	 * 修改最后登录时间
	 */
	public boolean updateLastLoginTime(OrgUser sysOrgUser) throws BusinessException;

	/**
	 * 清除闲置用户企业
	 */
	public boolean deleteidleUserOrg(String Mobile, Long orgId, Long appId) throws BusinessException;

	/**
	 * 插入用户企业机构表
	 */
	public boolean insertOrgUser(OrgUser sysOrgUser) throws BusinessException;

	/**
	 * 批量插入企业机构表
	 */
	public int insertOrgUser(List<OrgUser> sysOrgUserList) throws BusinessException;

	/**
	 * 删除企业机构用户关系表
	 */
	public boolean deleteSysOrgUser(OrgUser sysOrgUser, Long appId) throws BusinessException;

	/**
	 * 删除企业机构用户关系表
	 */
	public int deleteSysOrgUser(List<OrgUser> sysOrgUsers, Long appId) throws BusinessException;

	/**
	 * 设置默认企业 设置的默认优先级规则： 1首先设置非外派的并且激活的企业 2其次设置已经激活的企业 3最后随便找个企业设置为默认企业
	 */
	public void setDefaultOrg(Long userId, Long appId);

	/**
	 * 新增与易嘉关系
	 * 
	 * @param sysOrgUser
	 */
	public void setYJRelationship(OrgUser sysOrgUser);

//	/**
//	 * @api {POST} org/count count
//	 * @apiName count
//	 * @apiGroup org
//	 * @apiVersion 1.0.0
//	 * @apiDescription 统计最近1年用户和企业注册情况，按天分组，
//	 * @apiPermission anyone
//	 *
//	 * @apiSuccessExample {json} 返回结果
//	 *                    {"result":true,"value":[{}]}
//	 *
//	 */
//	@RequestMapping(value = "/org/count")
//	public List<SysOrgUserRegCountDto> getUserOrgCount();
//
//	/**
//	 * 根据企业和创建者信息搜索企业信息
//	 *
//	 * @param OrgUserQueryDto
//	 * @return
//	 */
//	public Map<String, Object> query(OrgUserQueryDto queryDto) throws BusinessException;
//
//	/**
//	 * 根据注册时间统计分析
//	 *
//	 * @param OrgUserQueryDto
//	 * @return
//	 */
//	public Map<String, Object> analyze(OrgUserQueryDto queryDto) throws BusinessException;

	/**
	 * 更新企业截止日期，版本，代账公司账套数上限
	 * 
	 * @param sysOrg
	 * @return
	 */
	boolean updateManageInfo(OrgDto sysOrg) throws BusinessException;

	/**
	 * @api {POST} spOrg/updateManageInfo updateManageInfo
	 * @apiName updateManageInfo
	 * @apiGroup spOrg
	 * @apiVersion 1.0.0
	 * @apiDescription 更新企业的截止日期、备注和版本类型
	 * @apiPermission anyone
	 *
	 * @apiParamExample {json} 请求示例
	 *                  {"id":1948811650664449,"expireTime":
	 *                  "2017-02-17 23:59:59"
	 *                  ,"memo":"a","version":1,"maxOrgCount":10}
	 * @apiParamExample {json} 参数说明
	 *                  {"version":"版本类型(0：内部版；1：试用版；2：软件版；3：服务版)",
	 *                  "MaxOrgCount"
	 *                  :"账套上限"}
	 * @apiSuccessExample {json} 返回结果
	 *                    {"result":true,"value":true}
	 *
	 */
	@RequestMapping(value = "spOrg/updateManageInfo")
	boolean updateSpManageInfo(@ApiContext("userId") OrgDto sysOrg) throws BusinessException;
//
//	/**
//	 * 导出企业信息
//	 *
//	 * @param queryDto
//	 * @return
//	 * @throws BusinessException
//	 */
//	byte[] export(OrgUserQueryDto queryDto) throws BusinessException;

	/**
	 * 更新企业统计信息
	 */
	void updateTotal();

	/**
	 * 更新服务商的企业统计信息
	 */
	void updateSpTotalOrg(Long spOrgId);

	/**
	 * 获取服务商的可建账数
	 */
	Integer getSpAvailableOrgCount(Long spOrgId);
//
//	/**
//	 * 导出新增企业信息
//	 *
//	 * @param queryDto
//	 * @return
//	 */
//	public byte[] analyzeExport(OrgUserQueryDto queryDto) throws BusinessException;
//
//	void taskUpdateTotal();
//
//	/**
//	 * @ api {POST} spOrg/query query
//	 * @ apiName query
//	 * @ apiGroup spOrg
//	 * @ apiVersion 1.0.0
//	 * @ apiDescription 企业列表查询
//	 * @ apiPermission anyone
//	 *
//	 * @ apiParam {Object} json json
//	 * @ apiParamExample {json} 请求示例
//	 *                  {"orgName":"abc","mobile":"188155555","beginDate":
//	 *                  "2016-12-9","endDate":"2017-1-1","page":{"currentPage":2
//	 *                  ,"pageSize":10}
//	 * @ apiParamExample {json} 详细说明
//	 *                  {"orgName":"用关键字模糊搜索企业名","beginDate":"企业创建日期-开始",
//	 *                  "endDate":"企业创建日期-截止","mobile":
//	 *                  "创建人的手机号-精确查找；企业和手机号不能同时为空；未运营人员登录该接口只返回当前用户的全部企业",
//	 *                  "page":{"currentPage":2,"pageSize":10}
//	 *
//	 * @ apiSuccess {boolean} result 结果状态
//	 * @ apiSuccess {Map} value 成功结果集合
//	 * @ apiSuccess {string} orgList 当前登录用户的企业列表
//	 *
//	 * @ apiSuccessExample {json} 返回结果
//	 *                    {
//	 *                    "result": true,
//	 *                    "value": {
//	 *                    "page": {
//	 *                    "pageSize": 10,
//	 *                    "currentPage": 1,
//	 *                    "totalPage": 2,
//	 *                    "sumCloum": 12,
//	 *                    "offset": 0
//	 *                    },
//	 *                    "orgList": [
//	 *                    {
//	 *                    "id": 100028,
//	 *                    "name": "李ddd",
//	 *                    "version": 1,
//	 *                    "orgType": 2,
//	 *                    "ts": "2016-12-01 09:21:19",
//	 *                    "bizdataId": 0,
//	 *                    "creator": 100000028,
//	 *                    "industry": 1,
//	 *                    "accountingStandards": 19,
//	 *                    "accountNum": 0,
//	 *                    "totalJournal":0,
//	 *                    "totalOperation":3,
//	 *                    "totalPerson":2,
//	 *                    "totalReceipt":0,
//	 *                    "totalWage":0,
//	 *                    "vatTaxpayer": 42,
//	 *                    "totalOrg":"代账公司已经开通的账套数",
//	 *                    "totalGeneralOrg":"代账公司已经开通的一般纳税人账套数",
//	 *                    "totalSmallOrg":"代账公司已经开通的小规模纳税人账套数",
//	 *                    "appId":0,
//	 *                    "requiredOrgCount":"代账公司注册时申请的账套数",
//	 *                    "maxOrgCount":"运营平台人员批准的账套数",
//	 *                    },
//	 *                    {
//	 *                    "id": 1562465016612864,
//	 *                    "name": "aa",
//	 *                    "version": 1,
//	 *                    "orgType": 2,
//	 *                    "ts": "2016-12-01 09:30:42",
//	 *                    "createTime": "2016-12-01 09:30:40",
//	 *                    "bizdataId": 0,
//	 *                    "creator": 100000028,
//	 *                    "industry": 1,
//	 *                    "accountingStandards": 19,
//	 *                    "accountNum": 0,
//	 *                    "totalJournal":0,
//	 *                    "totalOperation":3,
//	 *                    "totalPerson":2,
//	 *                    "totalReceipt":0,
//	 *                    "totalWage":0,
//	 *                    "identificationCode": "UHBBCJ",
//	 *                    "vatTaxpayer": 42,
//	 *                    "totalOrg":10,
//	 *                    "totalGeneralOrg":5,
//	 *                    "totalSmallOrg":5,
//	 *                    "appId":0,
//	 *                    "requiredOrgCount":"",
//	 *                    "maxOrgCount":90,
//	 *                    }
//	 *                    ]
//	 *                    }
//	 *                    }
//	 *
//	 *
//	 */
//	@RequestMapping(value = "spOrg/query")
//	public Map<String, Object> querySP(@ApiContext("userId") OrgUserQueryDto queryDto);
//
//	/**
//	 * 导出服务商企业信息
//	 *
//	 * @param queryDto
//	 * @return
//	 */
//	public byte[] exportSP(OrgUserQueryDto queryDto);

	/**
	 * 查询当前手机号是否存在企业
	 * 
	 * @param mobile
	 * @return
	 * @throws BusinessException
	 */
	public OrgDto findOrgByMobile(String mobile) throws BusinessException;

	/**
	 * 根据用户ID查询企业
	 * 
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public List<OrgDto> queryOrg(Long userId) throws BusinessException;

	/**
	 * 根据用户ID查询代账企业ID
	 * 
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Long findDZOrgByUserId(Long userId) throws BusinessException;

	/**
	 * 校验企业名称
	 * 
	 * @param sysOrg
	 * @return
	 * @throws BusinessException
	 */
	public Boolean findCountByName(OrgDto sysOrg) throws BusinessException;

	/**
	 * 根据用户ID和纳税人身份查询组织列表
	 * 
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public List<OrgDto> queryOrderOrgList(Map<String, Object> map) throws BusinessException;

	/**
	 * @api {POST} org/queryServiceProvider queryServiceProvider
	 * @apiName queryServiceProvider
	 * @apiGroup org
	 * @apiVersion 1.0.0
	 * @apiDescription 查询服务商信息
	 * @apiPermission anyone
	 *
	 * @apiSuccessExample {json} 返回结果
	 *                    {"result":true,"value":true}
	 *
	 */
	@RequestMapping(value = "/org/queryServiceProvider")
	public Map<String, Object> queryServiceProvider(@ApiContext("appId") OrgDto sysOrg) throws BusinessException;

	/**
	 * 根据spOrgId查询userId
	 * 
	 * @param spOrgIdList
	 * @return
	 * @throws BusinessException
	 */
	public List<Long> findUserIdBySpOrgId(List<Long> spOrgIdList) throws BusinessException;

	/**
	 * 根据userIdList和orgId删除组织关系
	 * 
	 * @param userIdList
	 * @param orgId
	 * @return
	 * @throws BusinessException
	 */
	public Boolean deleteSysOrgUserByUserIdListAndOrgId(List<Long> userIdList, Long orgId) throws BusinessException;

	/**
	 * 判断当前用户是不是当前组织的创建者
	 * 
	 * @param userId
	 * @param orgId
	 * @return
	 * @throws BusinessException
	 */
	public OrgDto findByOrgIdAndUserId(Long userId, Long orgId) throws BusinessException;

	/**
	 * 根据伙伴ID判断是否存在属于该伙伴的企业或服务商
	 * 
	 * @param userId
	 * @param orgId
	 * @return
	 * @throws BusinessException
	 */
	public Boolean IsExistsOrgByAppId(Long appId) throws BusinessException;

	/**
	 * 查询伙伴下的服务商
	 * 
	 * @param sysUser
	 * @return
	 */
	public List<OrgDto> queryServiceProvideByAppId(Long appId) throws BusinessException;
//
//	/**
//	 * @api {POST} org/getOrgUserById getOrgUserById
//	 * @apiName getOrgUserById
//	 * @apiGroup org
//	 * @apiVersion 1.0.0
//	 * @apiDescription 查询企业名称和手机号
//	 * @apiPermission anyone
//	 *
//	 * @apiSuccessExample {json} 返回结果
//	 *                    {"result":true,"value":true}
//	 *
//	 */
//	@RequestMapping(value = "/org/getOrgUserById")
//	public OrgQueryDto queryOrgNameAndCreatorMobile(@ApiContext("appId") OrgDto sysOrg) throws BusinessException;

	/**
	 * 根据userid查询是否为当前组织的客户
	 * 
	 * @param
	 * @return
	 * @throws BusinessException
	 */
	public OrgDto findSPByUserId(Long userId, Long orgId) throws BusinessException;

	/**
	 * 根据手机号和appId查询所属企业列表(对外接口－账无忌用)
	 * 
	 * @param mobile
	 * @param appId
	 * @return
	 * @throws BusinessException
	 */
	public List<OrgDto> findOrgListByMobile(String mobile, Long appId) throws BusinessException;

	/**
	 * 根据手机号、orgId和appId更新截止日期(对外接口－账无忌用)
	 * 
	 * @param mobile
	 * @param sysOrg
	 * @return
	 * @throws BusinessException
	 */
	public int updateExpireTimeById(String mobile, OrgDto sysOrg) throws BusinessException;
	
	/**
	 * 根据userId、orgId 查询当前用户账套新手引导任务完成的步骤(对外接口－账无忌用)
	 * 
	 * @param userId
	 * @param orgId
	 * @return
	 * @throws BusinessException
	 */
	public int getUserTaskStep(Long userId, Long orgId) throws BusinessException;
	
	/**
	 * 定时任务－企业到期(30天、10天、3天)定时发短信提醒
	 */
	public void taskOrgExpirationReminder();
	
	
}
