package com.mk.eap.portal.itf;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.dto.PageQueryDto;
import com.mk.eap.entity.dto.PageResultDto;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.entity.itf.IPageService;
import com.mk.eap.portal.dto.MenuDto;

@RequestMapping("/menu")
public interface IMenuService
		extends IEntityService<MenuDto>, IPageService<MenuDto, PageQueryDto<MenuDto>, PageResultDto<MenuDto>> {

	@RequestMapping("/create")
	MenuDto create(MenuDto menu) throws BusinessException;

	@RequestMapping("/update")
	MenuDto update(MenuDto menu) throws BusinessException;

	@RequestMapping("/delete")
	MenuDto delete(MenuDto menu) throws BusinessException;

	@RequestMapping("/query")
	List<MenuDto> query(MenuDto menu) throws BusinessException;

	@RequestMapping("/deleteBatch")
	List<MenuDto> deleteBatch(List<MenuDto> dtos) throws BusinessException;

	@RequestMapping("/queryPageList")
	PageResultDto<MenuDto> queryPageList(PageQueryDto<MenuDto> queryDto) throws BusinessException;
	 
	@RequestMapping("/findById")
	MenuDto queryByPrimaryKey(Long key) throws BusinessException;

	@RequestMapping("/findByUserId")
	List<MenuDto> findByUserId(Long userId) throws BusinessException;
	
	@RequestMapping("/findFullById")
	MenuDto findFullById(Long id) throws BusinessException;

	@RequestMapping("/queryFull")
	List<MenuDto> queryFull(MenuDto menu) throws BusinessException;
}
