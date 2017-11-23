package com.mk.eap.portal.dao;

import java.util.List;

import com.mk.eap.entity.dao.EntityMapper;
import com.mk.eap.portal.dto.OperationDto;
import com.mk.eap.portal.vo.OperationVo;

public interface OperationMapper extends EntityMapper<OperationVo>  {

	List<OperationDto> selectById(long id);

	List<OperationDto> queryAll();
	
}
