package com.mk.eap.component.common.print;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

public class PdfPrintEngineUtil {
	private PDDocument pdDocumnet;
	/**
	 * 来源文件
	 */
	private String srcFile;

	public PdfPrintEngineUtil(PDDocument pdDocumnet) throws PrinterException {
		this.setPdDocumnet(pdDocumnet);

      printJob();
	}
	

	public PdfPrintEngineUtil(String srcFile) throws IOException, PrinterException {
		this.srcFile = srcFile;
		this.setPdDocumnet(PDDocument.load(new File(srcFile)));
		
		printJob();
	}


	private void printJob() throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		  job.setPageable(new PDFPageable(getPdDocumnet()));
		  if (job.printDialog()) {
		      job.print();
		  }
	}

	public PDDocument getPdDocumnet() {
		return pdDocumnet;
	}

	public void setPdDocumnet(PDDocument pdDocumnet) {
		this.pdDocumnet = pdDocumnet;
	}

	public String getSrcFile() {
		return srcFile;
	}

	public void setSrcFile(String srcFile) {
		this.srcFile = srcFile;
	}
	
	

}
