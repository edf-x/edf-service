package com.mk.eap.entity.itf;

import java.util.List;

import com.mk.eap.base.DTO;

public interface IEntityInjector<D extends DTO> {

	// 进行数据库新增后执行
	void afterCreate(D createDto);

	// 进行数据库新增后执行
	void afterCreateBatch(List<D> createDtos);

	// 进行数据库删除后执行
	void afterDelete(D deleteDto);

	// 进行数据库删除后执行
	void afterDeleteBatch(List<D> deleteDtos);
	
	// 按照主键查询实体后执行
	void afterQuery(List<D> resultList);

	// 按照主键查询实体后执行
	void afterQueryByPrimaryKey(D queryDto);

	// 进行数据库更新后操作
	void afterUpdate(D updateDto);
	
	// 进行数据库更新后操作
	void afterUpdateBatch(List<D> updateDto);

	// 进行数据库新增前执行
	void beforeCreate(D createDto);

	// 进行数据库新增前执行
	void beforeCreateBatch(List<D> createDtos);

	// 进行数据库删除前执行
	void beforeDelete(D deleteDto);
	
	// 进行数据库删除前执行
	void beforeDeleteBatch(List<D> deleteDto);

	// 进行数据库更新前操作
	void beforeUpdate(D updateDto);
	
	// 进行数据库更新前操作
	void beforeUpdateBatch(List<D> updateDto);

}
