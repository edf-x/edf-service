package com.mk.eap.component.pdf.adapter;

import java.util.List;
import java.util.Map;

/**
 * 适配的来源数据
 * @author thinkpad
 *
 */
public class SrcDataAdaptee {
	
	private List<List<Map<String, Object>>> srcObject;
	
	public List<List<Map<String, Object>>> getSrcObject() {
		return srcObject;
	}
	public void setSrcObject(List<List<Map<String, Object>>> srcObject) {
		this.srcObject = srcObject;
	}
	
	/**
	 * 構造方法
	 * @param srcObject，由凭证传入
	 */
	public SrcDataAdaptee(List<List<Map<String, Object>>> srcObject) {

		this.srcObject = srcObject;
	}
	public  List<List<Map<String, Object>>>  srcData(){
		return this.getSrcObject();
		
	}
	

}
