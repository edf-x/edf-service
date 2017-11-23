package com.mk.eap.file;

import com.mk.eap.file.client.LocalClient;
import com.mk.eap.oid.IDGenerator;
import com.mk.eap.utils.PropertyUtil;
import com.mk.eap.file.client.AliOSSClient;
import com.mk.eap.file.utils.BucketPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileManager
{
    public static final Logger logger = LoggerFactory.getLogger(FileManager.class);
    private static String fileDir = "/storeDir";
    public static final String STORETYPE = "storeType";
    public static final String LOCAL = "Local";
    public static final String ALIOSS = "AliOss";

    static 
    {
        fileDir = (new StringBuilder()).append(PropertyUtil.getPropertyByKey("storeDir")).append(File.separator).toString();
    }

    public FileManager()
    {
    }
    
    
	public static String uploadFile(String fileName, InputStream is) {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int count = 0;
		try {
			while (count == 0) {
				count = is.available();
				byte[] b = new byte[count];
				is.read(b);
				bytestream.write(b);
			}
		} catch (IOException ex) {
			logger.error("FileManager/uploadFile 调用中发生异常：", ex);
			return null;
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					logger.error("本地文件系统关闭io异常", e);
					return null;
				}
		}
		byte imgdata[] = bytestream.toByteArray();
		return uploadFile(fileName, imgdata);
	}

    public static String uploadFile(String fileName, byte fileContent[])
    {
        String saveName = (new StringBuilder()).append("f").append(IDGenerator.generateObjectID(null)).append(fileName).toString();

        if("Local".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType")))
        {
            String basePath = fileDir;
            File fileDir = new File(basePath);
            if(!fileDir.exists())
                fileDir.mkdirs();
            String savePath = (new StringBuilder()).append(fileDir).append(File.separator).append(saveName).toString();
            try
            {
                LocalClient client = LocalClient.getInstance();
                saveName = client.upload(savePath, saveName, fileContent);
            }
            catch(Exception e)
            {
                logger.error("文件系统上传出错", e);
            }
		} else if ("AliOss".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType"))) {
            AliOSSClient client = AliOSSClient.getInstance();
            try
            {
            	String bucketName = PropertyUtil.getPropertyByKey("bucketName");
                saveName = client.upload(bucketName, saveName, fileContent);
            }
            catch(Exception e)
            {
                logger.error("阿里云上传出错", e);
            }
		}
        return saveName;
    }


    public static byte[] downLoadFile( String fileName)
    {
        byte contents[] = null;

        if("Local".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType")))
        {
            String downLoadPath = (new StringBuilder()).append(fileDir).append(fileName).toString();
            LocalClient client = LocalClient.getInstance();
            try
            {
                contents = client.download(downLoadPath);
                if(contents == null)
                    logger.error("没有找到文件内容！");
            }
            catch(Exception e)
            {
				logger.error("文件系统下载失败！", e);
            }
		} else if ("AliOss".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType"))) {
            AliOSSClient client = AliOSSClient.getInstance();
            try
            {
            	String bucketName = PropertyUtil.getPropertyByKey("bucketName");
                contents = client.download(bucketName, fileName);
                if(contents == null)
                    logger.error("没有找到文件内容！");
            }
            catch(Exception e)
            {
                logger.error("阿里云下载出错", e);
            }
		}
        return contents;
    }


    public static boolean deleteFile(String fileName)
    {
        boolean flag = false;

        if("Local".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType")))
        {
            String deletePath = (new StringBuilder()).append(fileDir).append(fileName).toString();
            LocalClient client = LocalClient.getInstance();
            try
            {
                flag = client.deleteFile(deletePath);
            }
            catch(Exception e)
            {
				logger.error("文件系统删除失败！", e);
            }
		} else if ("AliOss".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType"))) {
			AliOSSClient client = AliOSSClient.getInstance();
			try {
				String bucketName = PropertyUtil.getPropertyByKey("bucketName");
				flag = client.delete(bucketName, fileName);
			} catch (Exception e) {
				logger.error("阿里云删除失败！", e);
			}
		}
        return flag;
    }
    
    public static boolean deleteFileList(List<String> fileNameList)
    {
        boolean flag = false;

        for (int i = 0; i < fileNameList.size(); i++) {
        	if("Local".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType")))
            {
                String deletePath = (new StringBuilder()).append(fileDir).append(fileNameList.get(i)).toString();
                LocalClient client = LocalClient.getInstance();
                try
                {
                    flag = client.deleteFile(deletePath);
                }
                catch(Exception e)
                {
    				logger.error("文件系统删除失败！", e);
                }
    		} else if ("AliOss".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType"))) {
    			AliOSSClient client = AliOSSClient.getInstance();
    			try {
    				String bucketName = PropertyUtil.getPropertyByKey("bucketName");
    				flag = client.delete(bucketName, fileNameList.get(i));
    			} catch (Exception e) {
    				logger.error("阿里云删除失败！", e);
    			}
    		}
		}
        return flag;
    }
    
    
    public static String getUrl(String fileName, int expired)
    {
        return getUrl(BucketPermission.PRIVATE, fileName, expired);
    }

    private static String getUrl(BucketPermission permission, String fileName, int expired)
    {
		if (!"AliOss".equalsIgnoreCase(PropertyUtil.getPropertyByKey("storeType"))) {
            logger.error("only support alioos");
            return null;
        } else
        {
    		String bucketName = PropertyUtil.getPropertyByKey("bucketName");
            AliOSSClient client = AliOSSClient.getInstance();
            String url = client.getUrl(bucketName, permission, fileName, expired);
            return url;
        }
    }

    public static void main(String[] args) {
    	
    }
}
