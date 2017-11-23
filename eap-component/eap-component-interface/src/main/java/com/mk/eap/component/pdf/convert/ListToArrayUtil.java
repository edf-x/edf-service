package com.mk.eap.component.pdf.convert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 集合转数组公共类
 * @author thinkpad
 *
 */
public class ListToArrayUtil {
	
	/**
	 * List<Map<Object,Object>> to 二维数组
	 * @param list
	 * @param keyLength - Map中的key个数
	 * @return
	 */
	public static Object[][] list2array(List<Map<Object,Object>> list,int keyLength){
	    Object[][] array = new Object[list.size()][keyLength];
	    for(int i=0;i<list.size();i++){
	        array[i] = list.get(i).values().toArray();
	    }
	    return array;
//	    list2array(new ArrayList<Map<String,Object>>());
	}

	/**
	 * List<LinkedHashMap<String, Object>>  To  Object[][]
	 * @param list  Input Type
	 * @return
	 */
	public static Object[][] list2array(List<LinkedHashMap<String, Object>> list) {
		Object[][] array = new Object[list.size()][];
	    for(int i=0;i<list.size();i++){
	        array[i] =  list.get(i).values().toArray();
	    }
	    return array;
	}
	/**
	 * 泛型
	 * @param list
	 * @return
	 */
	public static  <K, V> Object[][] list2arrayF(List<Map<K,V>> list){
	    Object[][] array = new Object[list.size()][];
	    for(int i=0;i<list.size();i++){
	        array[i] = list.get(i).values().toArray();
	    }
	    return array;
	}
	

}
