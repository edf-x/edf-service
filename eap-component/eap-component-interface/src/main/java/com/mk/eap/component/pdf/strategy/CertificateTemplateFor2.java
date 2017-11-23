package com.mk.eap.component.pdf.strategy;

import com.mk.eap.component.common.pdfbox.template.FillPdfTemplateUtil;
import com.mk.eap.component.common.pdfbox.template.TemplateTypeEnum;
import com.mk.eap.component.pdf.convert.NumberToChineseBig;
import com.mk.eap.component.pdf.convert.PDFMerger;
import com.mk.eap.component.pdfboxtable.table.Table;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.PrinterException;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 两版凭证
 * @author thinkpad
 *
 */
public class CertificateTemplateFor2 implements TemplateStrategy {
	public static final Logger logger = LoggerFactory.getLogger(CertificateTemplateFor2.class);
	private final static Integer PAGE_SUM=10;//每页最多10条数据
	
	private final static Integer PAGE_TABLE_ROWS=5; //每个table 行数
	
	private final static Integer PAGE_TABLE_COUNT=2;//一页2个Table
	
	public CertificateTemplateFor2() {
		// TODO Auto-generated constructor stub
	}
	private String returnPdfPath;
	
	private byte[] returnByteArray;
	
	private List<Table> returnTables;
	
	public static void main(String[] args) {

		
	}
	
	public List<Table> getReturnTables() {
		return returnTables;
	}

	public void setReturnTables(List<Table> returnTables) {
		this.returnTables = returnTables;
	}

	public String getOnlyKey(){
		
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
    
	private String getRealPath(TemplateTypeEnum templateType, int count){
		if(templateType == TemplateTypeEnum.A4B2){
			if(count <=PAGE_TABLE_ROWS){
				return TEMPLATEPATH+"/"+"2editioncertificates-1.pdf";
			} else if(count <=PAGE_TABLE_ROWS*2){
				return TEMPLATEPATH+"/"+"2editioncertificates-2.pdf";
			}
		} else if(templateType == TemplateTypeEnum.A4B3){
			return TEMPLATEPATH+"/"+"3editioncertificates-3.pdf";
		}  else if(templateType == TemplateTypeEnum.AS2007){
			return TEMPLATEPATH+"/"+"balancesheet2007.pdf";
		} 
		return "";
	}
	@SuppressWarnings("unused")
	private String getSaveRealPath(TemplateTypeEnum templateType){
		if(templateType == TemplateTypeEnum.A4B2){
			return SAVEFILE+"/";
		} else if(templateType == TemplateTypeEnum.A4B3){
			return SAVEFILE+"/";
		}  else if(templateType == TemplateTypeEnum.A4B3){
			return SAVEFILE+"/";
		} 
		return "";
	}
	


	@Override
	public void operate(Map<String, Object[][]> dataMap, String srcFileName,TemplateTypeEnum templateType,Integer ... maxLineNum) throws IOException, PrinterException {
		Boolean isNotReturn = dataMap.keySet().size() == 1;
		if (!isNotReturn)
			return;
		FillPdfTemplateUtil templateUtil = new FillPdfTemplateUtil();
		List<String> keys = new ArrayList<>();
		for (String doc : dataMap.keySet()) {// 几张单据
			Object[][] perDocInfo = dataMap.get(doc);// 每单的明细信息
			keys.add(doc);
			int perRowCount = perDocInfo.length;// 每单行数
			int pageCount = (int) Math.ceil((double) perRowCount / PAGE_SUM);// 多少页

			buildPerContentAndSavePdf(srcFileName, templateUtil, perDocInfo, perRowCount, pageCount, dataMap,templateType);

		}
	}
	/**
	 * 
	 * @param perDocInfo
	 * @param perRowCount
	 * @param pageCount
	 */
	private void buildPerContentAndSavePdf( Object[][] perDocInfo,Map<String, Object[][]> dataMap,
			int perRowCount, int pageCount){	
//		List<Object[][]> listInfo = new ArrayList<>();
//		for (int i = 0; i < pageCount; i++) {
//			int startIndex = i * PAGE_SUM;
//			Object[][] newPageContent = null;
//			if (pageCount == 1)
//				newPageContent = perDocInfo;
//			else {
//				if (i + 1 == pageCount) {// 最后分隔的数据lastPage
//					newPageContent = new Object[perRowCount - i * PAGE_SUM][];
//				} else {
//					newPageContent = new Object[PAGE_SUM][];
//				}
//			}
//			listInfo.add(newPageContent);
//			int j = 0;
//			while (j < PAGE_SUM && startIndex < perRowCount) {
//				newPageContent[j++] = perDocInfo[startIndex++];
//			}
//		}
	     String perDocKey = null;
	     int rowCount = 0;
	     double perTableSumDr = 0, perTableSumCr = 0;// 初始化借方和贷方的合计
	     int afterRowCount = perDocInfo.length+perDocInfo.length/PAGE_TABLE_ROWS;
	     Object[][] afterDocInfo = new Object[afterRowCount][];
		for (Object[] columns : perDocInfo) {
			rowCount++;
			
			StringBuilder perRowKey = new StringBuilder(25);

			if (columns != null) {
				for (int j = columns.length - 2; j < columns.length; j++) {
					if (j == columns.length - 1) {
						perRowKey.append(columns[j]);
					} else {
						perRowKey.append(columns[j] + KEY_SPLIT);
					}
				}
				perTableSumDr += Double.valueOf(columns[2] == null ? "0.00" : columns[2].toString())
						.doubleValue();
				perTableSumCr += Double.valueOf(columns[3] == null ? "0.00" : columns[3].toString())
						.doubleValue(); 
				afterDocInfo[rowCount-1] = new Object[columns.length];
				System.arraycopy(columns, rowCount-1, afterDocInfo[rowCount-1], rowCount-1, 4);
				if (rowCount%PAGE_TABLE_ROWS ==0) {
					columns = new Object[4];
					columns[0]= SUM;
					columns[2] = perTableSumDr;
					columns[3] = perTableSumCr;
					
					
				    	
				}

				perDocKey = perRowKey.toString();
			}else{
					if (rowCount%PAGE_TABLE_ROWS ==0) {
					columns = new Object[4];
					columns[0]= SUM+NumberToChineseBig.number2CNMontrayUnit(new BigDecimal(perTableSumDr));
					columns[2] = perTableSumDr;
					columns[3] = perTableSumCr;
					perDocKey = null;
					perTableSumDr = 0;
					perTableSumCr = 0;// 初始化借方和贷方的合计
				}
				
			}

		}
		
//		for (Entry<String, Object[][]> key2Values : dataMap.entrySet()) {
//			String key = key2Values.getKey();
//		     Object[][] content =	key2Values.getValue();
//		     int length = content.length;
//		     double perTableSumDr = 0, perTableSumCr = 0;// 初始化借方和贷方的合计
//		     for (Object[] row : content) {
//					perTableSumDr += Double.valueOf(row[2] == null ? "0.00" : row[2].toString())
//							.doubleValue();
//					perTableSumCr += Double.valueOf(row[3] == null ? "0.00" : row[3].toString())
//							.doubleValue(); 
//				
//			}

//		     Arrays.copyOf(original, newLength)
		     
			
		
		
		
	}
		/**
		 * 填充没有的行数及其保存为pdf
		 * @param srcFileName 来源模板名称
		 * @param templateUtil 模板公共类
		 * @param perDocInfo /所有单据的明细信息
		 * @param perRowCount 每单行数
		 * @param pageCount 生成的页数
		 * @throws IOException
		 * @throws PrinterException 
		 */
	private void buildPerContentAndSavePdf(String srcFileName, FillPdfTemplateUtil templateUtil, Object[][] perDocInfo,
			int perRowCount, int pageCount, Map<String, Object[][]> dataMap, TemplateTypeEnum templateType)
			throws IOException, PrinterException {
		List<Object[][]> listInfo = new ArrayList<>();

		for (int i = 0; i < pageCount; i++) {
			int startIndex = i * PAGE_SUM;
			Object[][] newPageContent = null;
			if (pageCount == 1)
				newPageContent = perDocInfo;
			else {
				if (i + 1 == pageCount) {// 最后分隔的数据lastPage
					newPageContent = new Object[perRowCount - i * PAGE_SUM][];
				} else {
					newPageContent = new Object[PAGE_SUM][];
				}
			}
			listInfo.add(newPageContent);
			int j = 0;
			while (j < PAGE_SUM && startIndex < perRowCount) {
				newPageContent[j++] = perDocInfo[startIndex++];
			}

		}
		List<InputStream> listSteam = new ArrayList<>();
		int i = 0;
//		String[] deleteList = new String[listInfo.size()];

		LinkedHashMap<String, String> lastDocKeyAndValue = new LinkedHashMap<>();// 每次存储最后一条的数据明细
		double perTableSumDr = 0, perTableSumCr = 0;// 初始化借方和贷方的合计
		int perDocCount = 0;// 每单的行数，用于每单的行数多于一页且为5的倍数的时候合计大写使用
		int perDocAllCount = 0;// 包含所有空行的数据，用于处理一张单据多于两个table时使用
		Map<String, PDDocument> cachePDDocument = new HashMap<>();
		for (Object[][] objects : listInfo) {// objects 每页的行数
//			if(!cachePDDocument.containsKey(String.valueOf(objects.length))){
//				cachePDDocument.put(String.valueOf(objects.length), PDDocument
//						.load(this.getClass().getResourceAsStream(getRealPath(templateType, objects.length))));
//			}
			PDDocument pdfDocument = PDDocument
					.load(this.getClass().getResourceAsStream(getRealPath(templateType, objects.length)));
			
//			PDDocument pdfDocument = cachePDDocument.get(String.valueOf(objects.length));

			LinkedHashMap<String, List<Object[]>> perDocKeyAndValue = new LinkedHashMap<>();
			int iCount = 0;

			for (Object[] perRowColumns : objects) {
				StringBuilder perRowKey = new StringBuilder(50);
				iCount += 1;
				perDocCount += 1;
				perDocAllCount += 1;
				// 这段代码是为了处理如果第一单如果
				if (iCount % PAGE_TABLE_ROWS == 0 && perRowColumns == null) {
					if (iCount == PAGE_TABLE_ROWS) {// 说明是当前页第一个table
						templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(), 1);
						perDocCount = 0;
					} else if (iCount == 2 * PAGE_TABLE_ROWS) {// 说明是当前页第二个Table
						templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(), 2);
						perDocCount = 0;

					} else if (iCount == 3 * PAGE_TABLE_ROWS) {// 说明是当前页第三个Table
						templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(), 3);
						perDocCount = 0;

					}
				}
				if (perRowColumns != null) {
					for (int j = perRowColumns.length - 2; j < perRowColumns.length; j++) {
						if (j == perRowColumns.length - 1) {
							perRowKey.append(perRowColumns[j]);
						} else {
							perRowKey.append(perRowColumns[j] + KEY_SPLIT);
						}
					}

					if (!lastDocKeyAndValue.containsKey(perRowKey.toString())) {
						if (lastDocKeyAndValue.keySet().size() > 0) {

							if (iCount == PAGE_TABLE_ROWS + 1) {// 说明是当前页第一个table
								templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(),
										1);
							} else if (iCount == 2 * PAGE_TABLE_ROWS + 1) {// 说明是当前页第二个Table
								templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(),
										2);

							} else if (iCount == 3 * PAGE_TABLE_ROWS + 1) {// 说明是当前页第三个Table
								templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(),
										3);

							}
							lastDocKeyAndValue.clear();
							perTableSumDr = 0;
							perTableSumCr = 0;

						}
						lastDocKeyAndValue.put(perRowKey.toString(), new String());
					}
					if (!perDocKeyAndValue.containsKey(perRowKey.toString())) {
						perDocKeyAndValue.put(perRowKey.toString(), new ArrayList<Object[]>());
					}

					perDocKeyAndValue.get(perRowKey.toString()).add(perRowColumns);

					perTableSumDr += Double.valueOf(perRowColumns[2] == null ? "0.00" : perRowColumns[2].toString())
							.doubleValue();
					perTableSumCr += Double.valueOf(perRowColumns[3] == null ? "0.00" : perRowColumns[3].toString())
							.doubleValue();
					lastDocKeyAndValue.put(perRowKey.toString(), Double.toString(perTableSumDr));
				}

				if (iCount % PAGE_TABLE_ROWS == 0) {
					int firstFewTable = (int) Math.ceil((double) perDocAllCount / PAGE_TABLE_ROWS);
					int tableCount = (int) Math
							.ceil((double) dataMap.get(lastDocKeyAndValue.keySet().iterator().next()).length
									/ PAGE_TABLE_ROWS);
					String tablePercentAllTable = firstFewTable + "/" + tableCount;
					if (iCount == PAGE_TABLE_ROWS) {// 说明是当前页第一个table
						templateUtil.setSum(pdfDocument,
								new BigDecimal(perTableSumDr).setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
								new BigDecimal(perTableSumCr).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), 1,
								objects[0], tableCount == 1 ? "" : tablePercentAllTable);
						if (dataMap.keySet().contains(lastDocKeyAndValue.keySet().iterator().next())) {
							if (tableCount * PAGE_TABLE_ROWS == perDocAllCount) {
								perDocAllCount = 0;
							}
						}
						if (dataMap.keySet().contains(perRowKey.toString())) {
							if (dataMap.get(perRowKey.toString()).length == perDocCount) {
								perDocCount = 0;
								templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(),
										1);
							}
						}

					} else if (iCount == 2 * PAGE_TABLE_ROWS) {// 说明是当前页第二个Table
						templateUtil.setSum(pdfDocument,
								new BigDecimal(perTableSumDr).setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
								new BigDecimal(perTableSumCr).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), 2,
								objects[PAGE_TABLE_ROWS], tableCount == 1 ? "" : tablePercentAllTable);
						if (dataMap.keySet().contains(lastDocKeyAndValue.keySet().iterator().next())) {
							if (tableCount * PAGE_TABLE_ROWS == perDocAllCount) {
								perDocAllCount = 0;
							}
						}
						if (dataMap.keySet().contains(perRowKey.toString())) {
							if (dataMap.get(perRowKey.toString()).length == perDocCount) {
								perDocCount = 0;
								templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(),
										2);
							}
						}

					} else if (iCount == 3 * PAGE_TABLE_ROWS) {// 说明是当前页第三个Table
						templateUtil.setSum(pdfDocument,
								new BigDecimal(perTableSumDr).setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
								new BigDecimal(perTableSumCr).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), 3,
								objects[PAGE_SUM], tableCount == 1 ? "" : tablePercentAllTable);
						if (dataMap.keySet().contains(lastDocKeyAndValue.keySet().iterator().next())) {
							if (tableCount * PAGE_TABLE_ROWS == perDocAllCount) {
								perDocAllCount = 0;
							}
						}
						if (dataMap.keySet().contains(perRowKey.toString())) {
							if (dataMap.get(perRowKey.toString()).length == perDocCount) {
								perDocCount = 0;
								templateUtil.setChineseSum(pdfDocument, lastDocKeyAndValue.values().iterator().next(),
										3);
							}
						}

					}

				}

			}
			templateUtil.setField(pdfDocument, objects, true);
			if (listInfo.size() == 1) {
//				String path = FilePathUtil.SAVEFILE + FilePathUtil.SPLIT
//						+ FilePathUtil.createFile(FilePathUtil.getCurrentDate()) + FilePathUtil.SPLIT + getOnlyKey()
//						+ ".pdf";
//				setReturnPdfPath(path);
				// pdfDocument.save(path);
				ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
				pdfDocument.save(outPutStream);
				setReturnByteArray(outPutStream.toByteArray());
				pdfDocument.close();

			} else {
				// 修改成流的方式
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				pdfDocument.save(stream);
				InputStream is = new BufferedInputStream(new ByteArrayInputStream(stream.toByteArray()));
				listSteam.add(is);
				// 另一种方式，生成临时pdf，上传到阿里云或者专门文件服务器
				// String path =
				// FilePathUtil.SAVEFILE+FilePathUtil.SPLIT+FilePathUtil.createFile(FilePathUtil.getCurrentDate())+FilePathUtil.SPLIT+getOnlyKey()
				// + i + ".pdf";
				// pdfDocument.save(path);
				// deleteList[i] = path;
//				pdfDocument.close();
			}
			i++;
		}
		lastDocKeyAndValue = null;
		cachePDDocument = null;
		if (listInfo.size() > 1) {
			/*
			 * 注释掉，修改成流的方式 String[] newList = new String[listInfo.size() + 1];
			 * System.arraycopy(deleteList, 0, newList, 0, listInfo.size());
			 * String path = FilePathUtil.SAVEFILE + FilePathUtil.SPLIT +
			 * FilePathUtil.createFile(FilePathUtil.getCurrentDate()) +
			 * FilePathUtil.SPLIT + getOnlyKey() + ".pdf";
			 * setReturnPdfPath(path); newList[listInfo.size()] = path; if
			 * (newList.length >= 3) { PDFMerger merger = new
			 * PDFMerger(newList); byte[] returnByteArray =
			 * merger.merge(newList); setReturnByteArray(returnByteArray); for
			 * (String string : deleteList) { File file = new File(string);
			 * file.deleteOnExit(); } }
			 */
//			logger.error("合并开始"+new Date());
			PDFMerger merger = new PDFMerger(listSteam);
			byte[] returnByteArray = merger.merge(listSteam);
			setReturnByteArray(returnByteArray);
			listSteam = null;
//			logger.error("合并结束"+new Date());
		}
		listInfo = null;
		System.gc();
		// 有可能影响效率？？ 怎么做 定时器
		// FilePathUtil.deletePreFile(FilePathUtil.getCurrentDate());

		// @SuppressWarnings("unused")
		// PdfPrintEngineUtil printUtil = new
		// PdfPrintEngineUtil(newList[listInfo.size()].toString());
	}
	
	@Override
	public void batchOperate(Map<String, Object[][]> dataMap, String srcFileName,TemplateTypeEnum templateType,Integer ... maxLineNum) throws IOException, PrinterException {

		
		setReturnTables(CertificatePubHelper.buildTables(dataMap,maxLineNum)) ;

		
		/* 重构代码 以下代码是模板实现方式 2017年2月14日
		List<Object[][]> docInfos = new ArrayList<>();
		
		int docCount =0;
		int startIndex = 0;//每单开始的索引
		
		int allCount = 0;
		Object[][] totalContent = null;
		Object[][] tempContent = null;
		List<String> keys = new ArrayList<>();
		for (String perDocKey : dataMap.keySet()) {
//			int startIndex = docCount*PAGE_TABLE_ROWS;
			//每单肯定要占用一个table,不能在同一个table中有多个单据
			//每单的明细行数
			keys.add(perDocKey);
			
			Object[][] perDocinfo = null;
			int perDocDetailCount = dataMap.get(perDocKey).length;
			
			  int pageCount = (int) Math.ceil((double)perDocDetailCount/PAGE_SUM);//多少页
			if (pageCount == 1) {// 只有一页
				if (perDocDetailCount % PAGE_SUM >0 && perDocDetailCount % PAGE_SUM <= 5) {
					perDocinfo = new Object[PAGE_TABLE_ROWS][];
				} else if (perDocDetailCount % PAGE_SUM > 5 && perDocDetailCount % PAGE_SUM <= 10) {
					perDocinfo = new Object[PAGE_TABLE_ROWS * 2][];
				} else if (perDocDetailCount % PAGE_SUM > 10 && perDocDetailCount % PAGE_SUM <= 15) {
					perDocinfo = new Object[PAGE_SUM][];
				} else if(perDocDetailCount % PAGE_SUM ==0){
					perDocinfo = new Object[PAGE_SUM][];
				}

				allCount += perDocinfo.length;
				
			} else {
				for (int i = 0; i < pageCount; i++) {
					if (i + 1 == pageCount) {
						if (0 <(perDocDetailCount-i*PAGE_SUM) % PAGE_SUM &&(perDocDetailCount-i*PAGE_SUM) % PAGE_SUM <= 5) {
							perDocinfo = new Object[PAGE_TABLE_ROWS][];
						} else if ((perDocDetailCount-i*PAGE_SUM) % PAGE_SUM > 5 && (perDocDetailCount-i*PAGE_SUM) % PAGE_SUM <= 10) {
							perDocinfo = new Object[PAGE_TABLE_ROWS * 2][];
						} else if ((perDocDetailCount-i*PAGE_SUM) % PAGE_SUM ==0){
							perDocinfo = new Object[PAGE_SUM][];
						}
							
						allCount += perDocinfo.length;

					} else {
						perDocinfo = new Object[PAGE_SUM][];
						allCount += perDocinfo.length;
					}
				}
			}
			
			totalContent = new Object[allCount][];
			if(tempContent != null){
				for (int i = 0; i < tempContent.length; i++) {
					totalContent[i] = tempContent[i];
				}
			}
			for (Object[] rowCount : dataMap.get(perDocKey)) {
				
				totalContent[startIndex++] = rowCount;
			}
			startIndex =allCount;
			tempContent = totalContent;

			
		}
		FillPdfTemplateUtil templateUtil = new FillPdfTemplateUtil();
		
		Object[][] allDocInfo = totalContent;//所有的明细信息
		
		  int allRowCount= allDocInfo.length;//所有行数
		  int pageCount = (int) Math.ceil((double)allRowCount/PAGE_SUM);//多少页
		  
//		  calcSum(allDocInfo,templateUtil,keys);
		  

//		  buildPerContentAndSavePdf(srcFileName, templateUtil, allDocInfo, allRowCount, pageCount,dataMap,templateType);
		  
//		  buildPerContentAndSavePdf(allDocInfo,dataMap,allRowCount,pageCount);
		*/
		
	}
	
	public void calcSum(Object[][] perPageInfo,FillPdfTemplateUtil templateUtil,List<String> keys){

		for (String key :keys) {
			List<Object[]> perDocDetailInfo = new ArrayList<>();
			for (Object[] perRowColumns : perPageInfo) {
				StringBuilder perRowKey = new StringBuilder(50);
				if(perRowColumns == null) continue;
				for (int i = perRowColumns.length - 2; i < perRowColumns.length; i++) {
					if (i == perRowColumns.length - 1) {
						perRowKey.append(perRowColumns[i]);
					} else {
						perRowKey.append(perRowColumns[i] + KEY_SPLIT);
					}
				}
				
				if(key.equals(perRowKey.toString())){
					perDocDetailInfo.add(perRowColumns);
				}

			} 
		}
		
	}
	public String getReturnPdfPath() {
		return returnPdfPath;
	}
	public byte[] getReturnByteArray() {
		return returnByteArray;
	}

	public void setReturnByteArray(byte[] returnByteArray) {
		this.returnByteArray = returnByteArray;
	}

	public void setReturnPdfPath(String returnPdfPath) {
		this.returnPdfPath = returnPdfPath;
	}
	@Override
	public String getPdfPath() {
		// TODO Auto-generated method stub
		return getReturnPdfPath();
	}
	@Override
	public byte[] getByteArray() {
		// TODO Auto-generated method stub
		return getReturnByteArray();
	}
	
	
}
