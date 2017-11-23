package com.mk.eap.portal.impl;

import java.util.ArrayList;
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
import com.mk.eap.portal.dao.ColumnDetailMapper;
import com.mk.eap.portal.dto.ColumnDetailDto;
import com.mk.eap.portal.dto.ColumnDto;
import com.mk.eap.portal.dto.ColumnInitDto;
import com.mk.eap.portal.dto.EnumDetailDto;
import com.mk.eap.portal.itf.IColumnDetailService;
import com.mk.eap.portal.itf.IColumnService;
import com.mk.eap.portal.itf.IEnumDetailService;
import com.mk.eap.portal.vo.ColumnDetailVo;

@Component
@Service
public class ColumnDetailServiceImpl extends EntityServiceImpl<ColumnDetailDto, ColumnDetailVo, ColumnDetailMapper> implements IColumnDetailService {

	@Autowired
	IEnumDetailService enumDetailService;
	
	@Autowired
	IColumnService columnService;
	
	public ColumnInitDto init(PageObject pagination,ColumnDetailDto filter) throws BusinessException {
		ColumnInitDto dto = new ColumnInitDto();
		
		List<ColumnDto> column = columnService.query(new ColumnDto());
		
		PageQueryDto<ColumnDetailDto> queryDto = new PageQueryDto<ColumnDetailDto>();
		/*
		PageObject page = new PageObject();
		page.setCurrentPage(0);
		page.setPageSize(20);
		page.setOrderBy("colIndex");
		queryDto.setPage(page);
		*/
		queryDto.setPage(pagination);
		queryDto.setEntity(filter);
		PageResultDto<ColumnDetailDto> columnDetails = this.queryPageList(queryDto);

		dto.setTree(column);
		dto.setList(columnDetails.getList());
		dto.setPagination(pagination);

		return dto;
	}
	
	@Override
	public ColumnDetailDto create(ColumnDetailDto ColumnDetail) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-column-detail"));
		ColumnDetail.setId(id);
		return super.create(ColumnDetail);
	}
	
	@Override
	public ColumnDetailDto queryByPrimaryKey(Long key) {
		ColumnDetailDto columnDetailDto = new ColumnDetailDto();
		
		columnDetailDto = super.queryByPrimaryKey(key);
		this.setAttribute(columnDetailDto);
		
		return columnDetailDto;
	}
	
	@Override
	public List<ColumnDetailDto> query(ColumnDetailDto columnDetail) {
		List<ColumnDetailDto> columnDetailDtos = new ArrayList<>();
		columnDetailDtos = super.query(columnDetail);
		for(ColumnDetailDto columnDetailDto : columnDetailDtos)
		{
			this.setAttribute(columnDetailDto);
		}
		return columnDetailDtos;			
	}
	
	public List<ColumnDetailDto> findByColumnCode(String code) {
		ColumnDto columnDto = new ColumnDto();
		ColumnDetailDto columnDetail = new ColumnDetailDto();
		columnDto.setCode(code);
		List<ColumnDto> columnDtos = columnService.query(columnDto);
		if (columnDtos.size() > 0)
		{
			columnDetail.setColumnId(columnDtos.get(0).getId());
			columnDto.setColumnDetails(this.query(columnDetail));
		}
		return columnDto.getColumnDetails();
	}
	
	@Override
	public PageResultDto<ColumnDetailDto> queryPageList(PageQueryDto<ColumnDetailDto> queryDto) {
		PageResultDto<ColumnDetailDto> columnDetails = super.queryPageList(queryDto);
		for(ColumnDetailDto columnDetailDto : columnDetails.getList())
		{
			this.setAttribute(columnDetailDto);
		}
		return columnDetails;
	}
	
	public void batchDelete(List<Long> ids)
	{
		for (Long id : ids)
		{
			ColumnDetailDto columnDetailDto = new ColumnDetailDto();
			columnDetailDto.setId(id);
			this.delete(columnDetailDto);
		}
	}
	
	public void batchUpdate(List<ColumnDetailDto> columnDetailDtos) {
		for (ColumnDetailDto dto : columnDetailDtos) {
			super.update(dto);
		}
	}
	
	private ColumnDetailDto setAttribute(ColumnDetailDto columnDetailDto) {
		EnumDetailDto fieldTypeDto = enumDetailService.queryByPrimaryKey(columnDetailDto.getIdFieldType());
		EnumDetailDto alignTypeDto = enumDetailService.queryByPrimaryKey(columnDetailDto.getIdAlignType());
		EnumDetailDto orderModeDto = enumDetailService.queryByPrimaryKey(columnDetailDto.getIdOrderMode());
		
		columnDetailDto.setFieldTypeDTO(fieldTypeDto);
		columnDetailDto.setAlignTypeDTO(alignTypeDto);
		columnDetailDto.setOrderModeDTO(orderModeDto);
		
		return columnDetailDto;
		
	}
}
