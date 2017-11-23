package com.mk.eap.entity.impl;

import java.util.ArrayList;
import java.util.List;

import com.mk.eap.base.BusinessException;
import com.mk.eap.base.DTO;
import com.mk.eap.entity.itf.IEntityInjector;
import com.mk.eap.entity.itf.IEntityService;
import com.mk.eap.oid.IDGenerator;

/**
 * 关联实体的持久化帮助类
 * 
 * @author lisga
 *
 * @param <F>
 *            主DTO类型
 * @param <T>
 *            关联的DTO类型
 */
public class EntityCompositionInjector<F extends DTO, T extends DTO> implements IEntityInjector<F> {

	IEntityService<T> entityService;
	String fromObjectField;
	String fromKeyField = "id";
	String toKeyField = "id";
	String toRelatedField;

	List<String> relatedKeys = new ArrayList<>();
	List<Object> relatedValues = new ArrayList<>();

	/** 泛型 D 的具体类型 */
	protected Class<T> toDtoClass;

	/**
	 * 
	 * @param fromObjectField 父对象上的子对象集合属性名
	 * @param toRelatedField  子对象上的关联字段
	 * @param entityService	  子对象持久化服务
	 * @param toDtoClassName  子对象类名
	 * @param relations		  其它关联条件，如："typeId",1
	 */
	@SuppressWarnings("unchecked")
	public EntityCompositionInjector(String fromObjectField, String toRelatedField, IEntityService<T> entityService,
			String toDtoClassName, Object... relations) {
		this.entityService = entityService;
		this.fromObjectField = fromObjectField;
		this.toRelatedField = toRelatedField;
		try {
			this.toDtoClass = (Class<T>) Class.forName(toDtoClassName);
		} catch (ClassNotFoundException e) {
		}
		if (relations != null && relations.length > 0) {
			for (int i = 0; i < relations.length; i += 2) {
				relatedKeys.add(relations[i].toString());
				relatedValues.add(relations[i + 1]);
			}
		}
	}

	//返回带关联条件值的子对象
	protected T getRelatedInstance(T dto, Object key) {
		if (dto == null) { 
			try {
				dto = toDtoClass.newInstance();
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new BusinessException("", "", null, ex);
			}
		}
		dto.setFieldValue(toRelatedField, key);
		for (int i = 0; i < relatedKeys.size(); i++) {
			dto.setFieldValue(relatedKeys.get(i), relatedValues.get(i));
		}
		return dto;
	}

	@Override
	public void afterCreate(F createDto) {
		List<F> dtos = new ArrayList<F>();
		dtos.add(createDto);
		this.afterCreateBatch(dtos);
	}

	@Override
	public void afterCreateBatch(List<F> createDtos) {
		createDtos.forEach(parent -> {
			List<T> children = getChildren(parent);
			if (children != null) {
				Object key = parent.getFieldValue(fromKeyField);
				children.forEach(child -> {
					getRelatedInstance(child, key);
					if (child.getFieldValue(toKeyField) == null) {
						Long newid = Long.parseLong(IDGenerator.generateObjectID("eap-entity-aggregation"));
						child.setFieldValue(toKeyField, newid);
						entityService.create(child);
					} else {
						entityService.update(child);
					}
				});
			}
		});
	}

	@Override
	public void afterDelete(F deleteDto) {
		List<F> dtos = new ArrayList<F>();
		dtos.add(deleteDto);
		this.afterDeleteBatch(dtos);
	}

	@Override
	public void afterDeleteBatch(List<F> deleteDtos) {
		List<T> toDtos = new ArrayList<T>();
		deleteDtos.forEach(parentDto -> {
			Object key = parentDto.getFieldValue(fromKeyField);
			toDtos.add(getRelatedInstance(null, key));
		});
		if (toDtos.size() > 0) {
			entityService.deleteBatch(toDtos);
		}
	}

	@Override
	public void afterQuery(List<F> resultList) {

	}

	@Override
	public void afterQueryByPrimaryKey(F queryDto) {

	}

	@Override
	public void afterUpdate(F updateDto) {
		List<F> dtos = new ArrayList<F>();
		dtos.add(updateDto);
		this.afterUpdateBatch(dtos);
	}

	@SuppressWarnings("unchecked")
	private List<T> getChildren(F fromDto) {
		if (fromObjectField == null) {
			return null;
		}
		return (List<T>) fromDto.getFieldValue(fromObjectField);
	}

	@Override
	public void afterUpdateBatch(List<F> updateDtos) {
		updateDtos.forEach(fromDto -> {
			List<T> children = getChildren(fromDto);
			Object parentKey = fromDto.getFieldValue(fromKeyField);
			if (children != null) {
				T dto = getRelatedInstance(null, parentKey);
				List<T> childrenInDB = entityService.query(dto);
				// 新增key值为空的子数据
				children.forEach(child -> {
					if (child.getFieldValue(toKeyField) == null) {
						Long newid = Long.parseLong(IDGenerator.generateObjectID("eap-entity-aggregation"));
						child.setFieldValue(toKeyField, newid);
						getRelatedInstance(child, parentKey);
						entityService.create(child);
					}
				});
				// 删除未提交的子数据
				childrenInDB.forEach(child -> {
					Object key = child.getFieldValue(toKeyField);
					children.forEach(c -> {
						if (c.getFieldValue(toKeyField).equals(key)) {
							child.setFieldValue(toKeyField, null);
						}
					});
					if (child.getFieldValue(toKeyField) != null) {
						entityService.delete(child);
					}
				});
			}
		});

	}

	@Override
	public void beforeCreate(F createDto) {
		List<F> dtos = new ArrayList<F>();
		dtos.add(createDto);
		this.beforeCreateBatch(dtos);
	}

	@Override
	public void beforeCreateBatch(List<F> createDtos) {

	}

	@Override
	public void beforeDelete(F deleteDto) {
		List<F> dtos = new ArrayList<F>();
		dtos.add(deleteDto);
		this.beforeDeleteBatch(dtos);
	}

	@Override
	public void beforeDeleteBatch(List<F> deleteDto) {

	}

	@Override
	public void beforeUpdate(F updateDto) {
		List<F> dtos = new ArrayList<F>();
		dtos.add(updateDto);
		this.beforeDeleteBatch(dtos);
	}

	@Override
	public void beforeUpdateBatch(List<F> updateDto) {

	}

}
