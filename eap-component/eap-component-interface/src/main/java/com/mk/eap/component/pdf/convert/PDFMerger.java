/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mk.eap.component.pdf.convert;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This is the main program that will take a list of pdf documents and merge them,
 * saving the result in a new document.
 *
 * @author 
 */
public final class PDFMerger
{
    private String[] args;
    
    private List<InputStream> listStream ;
    /**
     * 
     * @param args最後一個參數是要存儲的目標名稱,必須指定
     */
    public PDFMerger(String[] args) {

		this.args = args;
	}
    

    /**
     * 构造方法，合并流
     * @param listStream 输入流集合
     */
	public PDFMerger(List<InputStream> listStream) {
		this.listStream = listStream;
	}
	/**
	 * 合并流信息，返回byte数组
	 * @param inputStreams 输入流集合
	 * @return   byte[]
	 * @throws IOException 错误异常
	 */
    public byte[] merge( List<InputStream> inputStreams ) throws IOException
    {
        PDFMergerUtility merger = new PDFMergerUtility();
        merger.addSources(inputStreams);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        merger.setDestinationStream(byteArrayOutputStream);
        merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        ByteArrayOutputStream outStream =    (ByteArrayOutputStream) merger.getDestinationStream();
        merger = null;
        return outStream.toByteArray();
        
    }


	/**
	 * 合并方法
	 * @param args：最後一個參數是要存儲的目標名稱
	 * @throws IOException
	 */
    public byte[] merge( String[] args ) throws IOException
    {
        int firstFileArgPos = 0;

        if ( args.length - firstFileArgPos < 3 )
        {
            usage();
        }

        PDFMergerUtility merger = new PDFMergerUtility();
        for( int i=firstFileArgPos; i<args.length-1; i++ )
        {
            String sourceFileName = args[i];
            merger.addSource(sourceFileName);
        }

        String destinationFileName = args[args.length-1];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        merger.setDestinationFileName(destinationFileName);
        merger.setDestinationStream(byteArrayOutputStream);
        merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        ByteArrayOutputStream outStream =    (ByteArrayOutputStream) merger.getDestinationStream();
        
        return outStream.toByteArray();
    }

    /**
     * This will print the usage requirements and exit.
     */
    private static void usage()
    {
        String message = "Usage: java -jar pdfbox-app-x.y.z.jar PDFMerger "
                + "<inputfiles 2..n> <outputfile>\n"
                + "\nOptions:\n"
                + "  <inputfiles 2..n> : 2 or more source PDF documents to merge\n"
                + "  <outputfile>      : The PDF document to save the merged documents to\n";
        
        System.err.println(message);
        System.exit(1);
    }
}
