package com.mk.eap.component.export.itf;

import com.mk.eap.base.BusinessException;
import com.mk.eap.base.DTO;

import java.util.List;
import java.util.Map;

/** 
 * @Description: 导出Excel公共接口
 * @author : gaoen
 * @date ：2017年4月20日 下午6:04:49
 * @FileName : IExportExcelService.java
 * @PackageName : com.mk.common.export
 */
public interface IExportExcelService {
	/**
	 * 导出Excel文件
	 * @param headMap Excel头数据
	 * @param dataList 数据列表 com.mk.rap.vo.DTO 的子类
	 * @param fieldName 全名，包含路径及其文件扩展名.xls(例如/template/fi/balanceSumAuxRpt.xls),请按不同的领域创建例如/template/xx/xxx.xls
	 * @param sheetIndex Excel中某个页签（0..）
	 * @return byte数组
	 * @throws BusinessException
	 */
	public <TDataDto extends DTO> byte[] export(Map<String, String> headMap, List<TDataDto> dataList, String fieldName, int sheetIndex)
			throws BusinessException;

	/**
	 * 导出Excel文件
	 * @param headMap Excel头数据
	 * @param dataList 数据列表 com.mk.rap.vo.DTO 的子类
	 * @param fieldName 全名，包含路径及其文件扩展名.xls(例如/template/fi/balanceSumAuxRpt.xls),请按不同的领域创建例如/template/xx/xxx.xls
	 * @param sheetIndex Excel中某个页签（0..）
	 * @param unFixCollist Excel变动列数据（变动列只支持在模板最前面）
	 * @return byte数组
	 * @throws BusinessException
	 */
	public <TDataDto extends DTO> byte[] export(Map<String, String> headMap, List<TDataDto> dataList, String fieldName, int sheetIndex, List<String> unFixCollist)
			throws BusinessException;

	/**
	 * 导出多页签
	 * @param headMapList Excel头数据 
	 * @param pageTitleList 页签名称列表
	 * @param dataList 页签数据列表
	 * @param fieldName 报表Excel模板文件名（不带扩展名）
	 * @param sheetIndex Excel中某个页签（0..）
	 * @param unFixCollist Excel变动列数据（变动列只支持在模板最前面）
	 * @return byte数组
	 * @throws BusinessException
	 */
	public <TDataDto extends DTO> byte[] exportAll(List<Map<String, String>> headMapList, List<String> pageTitleList, List<List<TDataDto>> dataList,
												   String fieldName, int sheetIndex, List<String> unFixCollist) throws BusinessException;
}
