package com.mk.eap.portal.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.portal.dao.MenuOperationMapper;
import com.mk.eap.portal.dto.MenuOperationDto;
import com.mk.eap.portal.itf.IMenuOperationService;
import com.mk.eap.portal.vo.MenuOperationVo;

@Component
@Service
public class MenuOperationServiceImpl extends EntityServiceImpl<MenuOperationDto, MenuOperationVo, MenuOperationMapper> implements IMenuOperationService {

    long CONST_ROLE_ID = 0;
	
	@Autowired
    MenuOperationMapper menuOperationMapper;
	
	@Override
	public MenuOperationDto create(MenuOperationDto menuOperation) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-menu-operation"));
		menuOperation.setId(id);
		return super.create(menuOperation);
	}
	
	@Override
	public List<MenuOperationDto> save(List<MenuOperationDto> menuOperationList){
		List<MenuOperationDto> newOperationList = new ArrayList<>();
		
		for(MenuOperationDto menuOperationDto: menuOperationList) {
			MenuOperationDto newMenuOperation = new MenuOperationDto();
			newMenuOperation.setRoleId(CONST_ROLE_ID); //新增的菜单默认角色为0
			if (menuOperationDto.getId() == null || !this.isExists(menuOperationDto.getMenuId(), menuOperationDto.getOperationId())) {
				newMenuOperation = this.create(menuOperationDto);
			}
			else {
				newMenuOperation = this.update(menuOperationDto);
			}
			newOperationList.add(newMenuOperation);
		}
		return newOperationList;
	}
	
	@Override
	public List<MenuOperationDto> findByUserId(Long userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		return menuOperationMapper.findByUserId(map);
	}
	
	private boolean isExists(long menuId, long operationId) {
		Map<String,Object> map = new HashMap<>();
		map.put("menuId", menuId);	
		map.put("operationId", operationId);
		List<MenuOperationDto> list = menuOperationMapper.selectById(map);
		if (list.isEmpty())
			return false;
		return true;
	}
}
