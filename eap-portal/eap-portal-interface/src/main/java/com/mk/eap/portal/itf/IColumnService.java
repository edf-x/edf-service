package com.mk.eap.portal.itf;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mk.eap.base.BusinessException;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.portal.dto.ColumnDto;

@RequestMapping("/column")
public interface IColumnService extends IEntityService<ColumnDto> {
	
	@RequestMapping("/create")
	ColumnDto create(ColumnDto enu) throws BusinessException;
	
	@RequestMapping("/update")
	ColumnDto update(ColumnDto enu) throws BusinessException;
	
	@RequestMapping("/delete")
	ColumnDto delete(ColumnDto enu) throws BusinessException;
	
	@RequestMapping("/query")
	List<ColumnDto> query(ColumnDto enu) throws BusinessException;
	
	@RequestMapping("/findById")
	ColumnDto queryByPrimaryKey(Long key) throws BusinessException;
}
