package com.mk.eap.portal.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.impl.EntityCompositionInjector;
import com.mk.eap.entity.impl.EntityServiceImpl;
import com.mk.eap.entity.itf.IEntityInjector;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.portal.dao.MenuMapper;
import com.mk.eap.portal.dao.MenuOperationMapper;
import com.mk.eap.portal.dto.MenuDto;
import com.mk.eap.portal.dto.MenuOperationDto;
import com.mk.eap.portal.itf.IMenuOperationService;
import com.mk.eap.portal.itf.IMenuService;
import com.mk.eap.portal.vo.MenuVo;

@Component
@Service
public class MenuServiceImpl extends EntityServiceImpl<MenuDto, MenuVo, MenuMapper> implements IMenuService {

	@Autowired
	IMenuOperationService menuOperationService;
	
	@Autowired
    MenuMapper menuMapper;
	@Autowired
	MenuOperationMapper menuOperationMapper;


	
	List<IEntityInjector<MenuDto>> injectors;
	protected List<IEntityInjector<MenuDto>> getInjectors() {
		if (injectors == null) {
			injectors = new ArrayList<IEntityInjector<MenuDto>>();
			String className = MenuOperationDto.class.getName();
			IEntityInjector<MenuDto> menuOperationInjector = new EntityCompositionInjector<MenuDto, MenuOperationDto>(
					"operations", "menuId", menuOperationService, className);   //, "roleId", 0L
			injectors.add(menuOperationInjector);
		}
		return injectors;
	}
	
	@Override
	public MenuDto create(MenuDto menu) {
		Long id = Long.parseLong(IDGenerator.generateObjectID("eap-menu"));
		menu.setId(id);
		menu.getMenuOperationList().forEach(o -> o.setMenuId(id));
		menuOperationService.save(menu.getMenuOperationList());
		return super.create(menu);
	}
	
	@Override
	public MenuDto update(MenuDto menu) {
		menuOperationService.save(menu.getMenuOperationList());
		return super.update(menu);
	}

	@Override
	public List<MenuDto> queryFull(MenuDto menu) throws BusinessException {
		List<MenuDto> menus = query(menu);
		MenuOperationDto menuOperation = new MenuOperationDto();
		menuOperation.setRoleId(0L);
		List<MenuOperationDto> menuOperations = menuOperationService.query(menuOperation);

		Map<Long, List<MenuOperationDto>> groupedByMenuId = menuOperations.stream()
				.collect(Collectors.groupingBy(MenuOperationDto::getMenuId));

		menus.forEach(m -> m.setMenuOperationList(groupedByMenuId.get(m.getId())));

		return menus;
	}

	@Override
	public MenuDto findFullById(Long id) throws BusinessException {
		MenuDto menu = super.queryByPrimaryKey(id);
		if (menu == null)
			return menu;
		MenuOperationDto menuOperation = new MenuOperationDto();
		menuOperation.setMenuId(menu.getId());
		menuOperation.setRoleId(0L);
		List<MenuOperationDto> menuOperationList = menuOperationService.query(menuOperation);
		menu.setMenuOperationList(menuOperationList);
		return menu;
	}

	@SuppressWarnings("null")
	@Override
	public List<MenuDto> findByUserId(Long userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		
		List<MenuDto> menuList = menuMapper.findByUserId(map);
		List<MenuOperationDto> menuOperationList = menuOperationMapper.findByUserId(map);
		
		for(MenuDto menuDto : menuList) {
			List<MenuOperationDto> newMenuOperationList = new ArrayList<>();
			for(MenuOperationDto menuOperationDto : menuOperationList){
				if (menuOperationDto.getMenuId().equals(menuDto.getId())) {
					newMenuOperationList.add(menuOperationDto);
				}
			}
			menuDto.setMenuOperationList(newMenuOperationList);
		}
		
		return menuList;
	}
}
