package com.mk.eap.component.common.pdfbox.template;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class FilePathUtil {
	
	public final static String SAVEFILE="temp";
	
	public final static String TEMPLATEPATH="/template/fi";
	
	public final static String FIXCODE1="00001000";
	
	public final static String FIXCODE2="00";
	
	public final static String SPLIT ="/";
	
	
	
	public static String getCurrentDate(){
		Calendar calendar =	Calendar.getInstance();
		StringBuilder currentDate = new StringBuilder(8);
		currentDate.append(calendar.get(Calendar.YEAR));
		currentDate.append(calendar.get(Calendar.MONTH+1));
		currentDate.append(calendar.get(Calendar.DATE));
		return currentDate.toString();
	}
	
	public static String getOnlyKey(){
		
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());  
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        StringBuilder onlyKey = new StringBuilder(30);
        onlyKey.append(date);
        onlyKey.append(FIXCODE1);
        onlyKey.append(getTwo());
        onlyKey.append(FIXCODE2);
        onlyKey.append(seconds);
        onlyKey.append(getTwo());
        return onlyKey.toString();	
	}
	public static String createFile(String childFileName){
		File file = new File(SAVEFILE, childFileName);
		if(!file.exists()){
			file.mkdirs();
		}
		return file.getName();
	}
	public static void deletePreFile(String currentFile){
		File file = new File(SAVEFILE);
		for (String fileName : file.list()) {
			if(Integer.valueOf(fileName)<Integer.valueOf(currentFile)){
				file = new File(SAVEFILE,fileName);
				deleteAllFile(file);
			}
		}
		
		
	}
	private static void deleteAllFile(File file){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteAllFile(files[i]);
			}
		}
		file.delete();
	}
	/** 
     * 产生随机的2位数 
     * @return 
     */  
    public static String getTwo(){  
        Random rad=new Random();  
        String result  = rad.nextInt(100) +"";  
        if(result.length()==1){  
            result = "0" + result;  
        }  
        return result;  
    }
    
	public static String getRealPath(FillPdfTemplateUtil templateUtil){
		if(templateUtil.getTemplateType() == TemplateTypeEnum.A4B2){
			return TEMPLATEPATH+"/"+"2editioncertificates-2.pdf";
		} else if(templateUtil.getTemplateType() == TemplateTypeEnum.A4B3){
			return TEMPLATEPATH+"/"+"3editioncertificates-3.pdf";
		}  else if(templateUtil.getTemplateType() == TemplateTypeEnum.AS2007){
			templateUtil.setFontSize(8f);
			return TEMPLATEPATH+"/"+"balancesheet2007.pdf";
		} else if(templateUtil.getTemplateType() == TemplateTypeEnum.AS2013){
			templateUtil.setFontSize(8f);
			return TEMPLATEPATH+"/"+"balancesheet2013.pdf";
		}else if(templateUtil.getTemplateType() == TemplateTypeEnum.CF2007){
			return TEMPLATEPATH+"/"+"CashFlowSheet2007.pdf";
		}else if(templateUtil.getTemplateType() == TemplateTypeEnum.CF2013){
			return TEMPLATEPATH+"/"+"CashFlowSheet2013.pdf";
		}else if(templateUtil.getTemplateType() == TemplateTypeEnum.IS2007GX){
			return TEMPLATEPATH+"/"+"incomestatements2007gs.pdf";
		}else if(templateUtil.getTemplateType() == TemplateTypeEnum.IS2013GX){
			return TEMPLATEPATH+"/"+"incomestatements2013gs.pdf";
		}else if(templateUtil.getTemplateType() == TemplateTypeEnum.IS2007ZY){
			return TEMPLATEPATH+"/"+"incomestatements2007zy.pdf";
		}else if(templateUtil.getTemplateType() == TemplateTypeEnum.IS2013ZY){
			return TEMPLATEPATH+"/"+"incomestatements2013zy.pdf";
		}
		return "";
	}


}
