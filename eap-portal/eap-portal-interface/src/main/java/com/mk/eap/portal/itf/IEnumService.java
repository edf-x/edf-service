package com.mk.eap.portal.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.portal.dto.EnumDto;

@RequestMapping("/enum")
public interface IEnumService extends IEntityService<EnumDto> {
	
	@RequestMapping("/create")
	EnumDto create(EnumDto enu) throws BusinessException;
	
	@RequestMapping("/update")
	EnumDto update(EnumDto enu) throws BusinessException;
	
	@RequestMapping("/delete")
	EnumDto delete(EnumDto enu) throws BusinessException;
	
	@RequestMapping("/query")
	List<EnumDto> query(EnumDto enu) throws BusinessException;
	
	@RequestMapping("/findById")
	EnumDto queryByPrimaryKey(Long key) throws BusinessException;
}
