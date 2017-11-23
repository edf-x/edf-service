package com.mk.eap.portal.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.base.PageObject;
import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.entity.itf.IPageService;
import com.mk.eap.portal.dto.ColumnDetailDto;
import com.mk.eap.portal.dto.ColumnInitDto;

@RequestMapping("/columnDetail")
public interface IColumnDetailService
	extends IEntityService<ColumnDetailDto>, IPageService<ColumnDetailDto, PageQueryDto<ColumnDetailDto>, PageResultDto<ColumnDetailDto>> {

	@RequestMapping("/create")
	ColumnDetailDto create(ColumnDetailDto columnDetail) throws BusinessException;
	
	@RequestMapping("/update")
	ColumnDetailDto update(ColumnDetailDto columnDetail) throws BusinessException;
	
	@RequestMapping("/delete")
	ColumnDetailDto delete(ColumnDetailDto columnDetail) throws BusinessException;
	
	@RequestMapping("/init")
	ColumnInitDto init(PageObject pagination,ColumnDetailDto filter) throws BusinessException;
	
	@RequestMapping("/batchDelete")
	void batchDelete(List<Long> ids) throws BusinessException;
	
	@RequestMapping("/batchUpdate")
	void batchUpdate(List<ColumnDetailDto> columnDetailDtos) throws BusinessException;
	
	@RequestMapping("/query")
	List<ColumnDetailDto> query(ColumnDetailDto columnDetail) throws BusinessException;
	
	@RequestMapping("/findById")
	ColumnDetailDto queryByPrimaryKey(Long key) throws BusinessException;
	
	@RequestMapping("/findByColumnCode")
	List<ColumnDetailDto> findByColumnCode(String code) throws BusinessException;
	
	@RequestMapping("/queryPageList")
	PageResultDto<ColumnDetailDto> queryPageList(PageQueryDto<ColumnDetailDto> queryDto) throws BusinessException;
}
