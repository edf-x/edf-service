package com.mk.eap.component.pdf.strategy;

import com.mk.eap.component.common.pdfbox.template.TemplateTypeEnum;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Map;

/**
 * 模板上下文
 * @author thinkpad
 *
 */
public class TemplateContext {
	
	private TemplateStrategy strategy;

	public TemplateContext(TemplateStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void operate(Map<String, Object[][]> dataMap,String srcFileName,TemplateTypeEnum templateType) throws IOException, PrinterException{
		strategy.operate(dataMap, srcFileName,templateType);
	}
	public void batchOperate(Map<String, Object[][]> dataMap,String srcFileName,TemplateTypeEnum templateType) throws IOException, PrinterException{
		strategy.operate(dataMap, srcFileName,templateType);
	}
	

}
