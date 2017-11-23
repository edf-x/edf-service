package com.mk.eap.portal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.BusinessException;
import com.mk.eap.base.PageObject;
import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.portal.dao.EnumDetailMapper;
import com.mk.eap.portal.dto.EnumDetailDto;
import com.mk.eap.portal.dto.EnumDto;
import com.mk.eap.portal.dto.EnumInitDto;
import com.mk.eap.portal.itf.IEnumDetailService;
import com.mk.eap.portal.itf.IEnumService;
import com.mk.eap.portal.vo.EnumDetailVo;

@Component
@Service
public class EnumDetailServiceImpl extends EntityServiceImpl<EnumDetailDto, EnumDetailVo, EnumDetailMapper> implements IEnumDetailService {

	@Autowired
	IEnumService enumService;
	
	public EnumInitDto init(PageObject pagination,EnumDetailDto filter) throws BusinessException {
		EnumInitDto dto = new EnumInitDto();
		
		List<EnumDto> column = enumService.query(new EnumDto());
		
		PageQueryDto<EnumDetailDto> queryDto = new PageQueryDto<EnumDetailDto>();
		queryDto.setPage(pagination);
		queryDto.setEntity(filter);
		PageResultDto<EnumDetailDto> columnDetails = this.queryPageList(queryDto);

		dto.setTree(column);
		dto.setList(columnDetails.getList());
		dto.setPagination(pagination);

		return dto;
	}
	
	@Override
	public EnumDetailDto create(EnumDetailDto enumDetail) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-enum-detail"));
		enumDetail.setId(id);
		return super.create(enumDetail);
	}
	
	public void batchDelete(List<Long> ids)
	{
		for (Long id : ids)
		{
			EnumDetailDto enumDetailDto = new EnumDetailDto();
			enumDetailDto.setId(id);
			this.delete(enumDetailDto);
		}
	}
}
