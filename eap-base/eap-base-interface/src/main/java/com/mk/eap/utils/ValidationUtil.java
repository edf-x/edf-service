package com.mk.eap.utils;

import com.mk.eap.base.BusinessException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
	
	private final static String VALIDCODE = "9000";
	
	public static final boolean isId(Long obj,String msg){
		if(obj >= 0L && obj <= 99999999999999999L){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	public static final boolean isNotNullId(Long obj,String msg){
		return obj == null ? true : isId(obj, msg);
	}
	
	public static final boolean isNull(String obj,String msg){
		if(obj == null ){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	
	public static final boolean isNotNull(String obj,String msg){
		if(obj == null || obj.isEmpty()){
			throw new BusinessException(VALIDCODE,msg);
		}else{
			return true;
		}
	}
	public static final boolean isNotNull(Long obj,String msg){
		if(obj == null){
			throw new BusinessException(VALIDCODE,msg);
		}else{
			return true;
		}
	}
	public static final boolean isNotNull(Object obj,String msg){
		if(obj == null){
			throw new BusinessException(VALIDCODE,msg);
		}else{
			return true;
		}
	}
	public static final boolean isNotNull(int obj,String msg){
		if(obj > 0){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	public static final boolean regex(String obj,String regex,String msg){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(obj);
		if(matcher.matches()){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	/**
	 * 校验日期真实性
	 * @param str
	 * @return boolean
	 */
	public static final boolean validDateFacticity(String str,String msg){
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		try{
			java.util.Date date=sdf.parse(str);
			if(str.equals(sdf.format(date))){
				return true;
			}
			else{
				throw new BusinessException(VALIDCODE,msg);
			}
		}catch(ParseException e){
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	
	public static void main(String[] args) {

		String a = "2017-02-24 15:46:09";
		boolean equalsIgnoreCase = a.contentEquals("2017-02-24");
		System.out.println(equalsIgnoreCase);
//		validDateFacticity(a,"asdasd");
		
//		sy
	}

	/**
	 * 是否在数字区间内
	 * @param obj
	 * @param i
	 * @param j
	 * @param msg
	 * @return
	 */
	public static boolean range(Long obj, long i, long j, String msg) {
		if(obj >= i && obj <=j){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	public static boolean range(Byte obj, long i, long j, String msg) {
		if(obj >= i && obj <=j){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	public static boolean range(Integer obj, int i, int j, String msg) {
		if(obj >= i && obj <=j){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	public static boolean range(Double obj, long i, long j, String msg) {
		if(obj >= i && obj <=j){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	public static boolean range(Double obj, Double i, Double j, String msg) {
		if(obj >= i && obj <=j){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	/**
	 * 不是空的范围
	 * @param obj
	 * @param i
	 * @param j
	 * @param msg
	 * @return
	 */
	public static boolean notNullRange(Double obj, long i, long j, String msg) {
		return obj == null ? true : range(obj, i, j, msg);
	}
	public static boolean notNullRange(Double obj, Double i, Double j, String msg) {
		return obj == null ? true : range(obj, i, j, msg);
	}
	public static boolean notNullRange(Long obj, long i, long j, String msg) {
		return obj == null ? true : range(obj, i, j, msg);
	}
	public static boolean notNullRange(Byte obj, long i, long j, String msg) {
		return obj == null ? true : range(obj, i, j, msg);
	}
	public static boolean notNullRange(Integer obj, int i, int j, String msg) {
		return obj == null ? true : range(obj, i, j, msg);
	}
	
	
	/**
	 * 字符串范围
	 * @param obj
	 * @param i
	 * @param msg
	 * @return
	 */
	
	public static boolean length(String obj, int i, String msg) {
		if(obj.length() <= i){
			return true;
		}else{
			throw new BusinessException(VALIDCODE,msg);
		}
	}
	
	public static boolean notNullLength(String obj, int i, String msg) {
		return obj == null ? true : length(obj, i, msg);
	}
	

		
}
