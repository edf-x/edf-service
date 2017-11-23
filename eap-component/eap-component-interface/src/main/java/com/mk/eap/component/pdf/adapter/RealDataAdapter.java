package com.mk.eap.component.pdf.adapter;

import com.mk.eap.component.pdf.convert.ListToArrayUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 适配中真正要适配的角色
 * @author thinkpad
 *
 */
public class RealDataAdapter implements Target {
	
	private List<List<Map<String, Object>>> srcObject;
	
	SrcDataAdaptee src =null;
	
	List<String> headInfos = new ArrayList<>();
	
	List<String> detailInfos = new ArrayList<>();
	

	
	

	
	private RealDataAdapter() {
		buildHeadInfo();
		buildDetailInfo();
	}


	private void buildDetailInfo() {

		detailInfos.add(SUMMARY);
		detailInfos.add(ACCOUNT);
		detailInfos.add(AMOUNTDR);
		detailInfos.add(AMOUNTCR);
		
		
		detailInfos.add(DOCTYPE);
		detailInfos.add(CODE);
		detailInfos.add(VOUCHERDATE);
		detailInfos.add(DOCNUM);
		detailInfos.add(UNIT);
		detailInfos.add(MARKER);
		detailInfos.add(AUDITOR);
		detailInfos.add(PRIMARY_ORGID);
		detailInfos.add(PRIMARY_DOCID);

	}


	private void buildHeadInfo() {
//		headInfos.add(GLHEAD_CODE);
//		headInfos.add(GLHEAD_DATE);
//		headInfos.add(GLHEAD_DOCNUM);
//		headInfos.add(GLHEAD_UNIT);
//		headInfos.add(MARKER);
		headInfos.add(PRIMARY_ORGID);
		headInfos.add(PRIMARY_DOCID);
	}
	
	public List<List<Map<String, Object>>> getSrcObject() {
		return srcObject;
	}


	public void setSrcObject(List<List<Map<String, Object>>> srcObject) {
		this.srcObject = srcObject;
	}

	/**
	 * 转换成pdf填充对应的类型
	 * key:凭证头信息：凭证号等等之间用##分隔
	 * value：二维数组存储凭证明细信息
	 */
	private LinkedHashMap<String, Object[][]> realData = null;
	
	
	public LinkedHashMap<String, Object[][]> getRealData() {
		return realData;
	}


	public void setRealData(LinkedHashMap<String, Object[][]> realData) {
		this.realData = realData;
	}




	public RealDataAdapter(List<List<Map<String, Object>>> srcObject) {
		this();
		this.srcObject = srcObject;
		 src = new SrcDataAdaptee(srcObject);
	}


	/**
	 * 转成需要的类型
	 */
	@Override
	public LinkedHashMap<String, Object[][]> dataTransfer() {
		// get srcdata
		SrcDataAdaptee srcDataAdaptee=	src;
		List<List<Map<String, Object>>> srcObject =	srcDataAdaptee.getSrcObject();
		//build realdata
		if(getRealData() == null)
			this.setRealData(new LinkedHashMap<String, Object[][]>());
		realData.clear();
		//convert
		for (List<Map<String, Object>> oneGL : srcObject) {
			 Map<String, Object> firstGLDetailOneGL = oneGL.get(0);//每张凭证的第一条明细数据
			 StringBuilder key = new StringBuilder(50);
			for (String firstGLDetailKey : firstGLDetailOneGL.keySet()) {
				if (headInfos.contains(firstGLDetailKey)) {
					// key.append(firstGLDetailKey+KEY_VALUE+firstGLDetailOneGL.get(firstGLDetailKey)+KEY_SPLIT);
					if (firstGLDetailKey.equals(PRIMARY_ORGID)) {
						key.insert(0, firstGLDetailOneGL.get(firstGLDetailKey) + KEY_SPLIT);
					} else {
						key.append(firstGLDetailOneGL.get(firstGLDetailKey) + KEY_SPLIT);

					}

				}
			}
				String headKey =key.toString().substring(0, key.toString().lastIndexOf(KEY_SPLIT));
				if(!realData.containsKey(headKey)){
					realData.put(headKey, ListToArrayUtil.list2array(filterKeyTransfer(oneGL)));
					
				}
 
		}
		return realData;
	 
		
	}

	
	private List<LinkedHashMap<String, Object>>  filterKeyTransfer(List<Map<String, Object>> srcList){
		
		List<LinkedHashMap<String, Object>> descList = new ArrayList<>();
		
		for (Map<String, Object> oneInfo : srcList) {
			LinkedHashMap<String, Object>  descinfo = new LinkedHashMap<>();
//			for (String key : oneInfo.keySet()) {
//				if(detailInfos.contains(key)){
//					descinfo.put(key, oneInfo.get(key));
//				}
//			}
			for (String detailInfo : detailInfos) {//这样的话就会按照我需要的排序
				for (String key : oneInfo.keySet()) {
					if( detailInfo.equals(key)){
						descinfo.put(key, oneInfo.get(key));
					}
				}
				
			}
			descList.add(descinfo);
		}
		
		return descList;
	}

}
