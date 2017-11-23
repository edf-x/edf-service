package com.mk.eap.component.pdf.convert;

/**
 * 
 * @author thinkpad
 *
 */
public class ObjectArrayToStringArray {
	/**
	 * Object[][] 转换为 String[][]
	 * @param srcContent
	 * @return
	 */
	public static String[][] Object2StringArray(Object[][] srcContent){
		String[][] realContent = new String[srcContent.length][];
		for (int i = 0; i < srcContent.length; i++) {
			Object[] columns = srcContent[i];
			String[] realColumns = new String[columns.length];
			int j=0;
			for (Object column : columns) {
				realColumns[j++] = String.valueOf(column);
			}
			realContent[i] = realColumns;
		}
		return realContent;
	}

}
