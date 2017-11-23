package com.mk.eap.portal.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.base.PageObject;
import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.entity.itf.IPageService;
import com.mk.eap.portal.dto.EnumDetailDto;
import com.mk.eap.portal.dto.EnumInitDto;

@RequestMapping("/enumDetail")
public interface IEnumDetailService
	extends IEntityService<EnumDetailDto>, IPageService<EnumDetailDto, PageQueryDto<EnumDetailDto>, PageResultDto<EnumDetailDto>> {

	@RequestMapping("/create")
	EnumDetailDto create(EnumDetailDto enumDetail) throws BusinessException;
	
	@RequestMapping("/update")
	EnumDetailDto update(EnumDetailDto enumDetail) throws BusinessException;
	
	@RequestMapping("/delete")
	EnumDetailDto delete(EnumDetailDto enumDetail) throws BusinessException;
	
	@RequestMapping("/init")
	EnumInitDto init(PageObject pagination,EnumDetailDto filter) throws BusinessException;
	
	@RequestMapping("/batchDelete")
	void batchDelete(List<Long> ids) throws BusinessException;
	
	@RequestMapping("/query")
	List<EnumDetailDto> query(EnumDetailDto enumDetail) throws BusinessException;
	
	@RequestMapping("/findById")
	EnumDetailDto queryByPrimaryKey(Long key) throws BusinessException;
	
	@RequestMapping("/queryPageList")
	PageResultDto<EnumDetailDto> queryPageList(PageQueryDto<EnumDetailDto> queryDto) throws BusinessException;
}
