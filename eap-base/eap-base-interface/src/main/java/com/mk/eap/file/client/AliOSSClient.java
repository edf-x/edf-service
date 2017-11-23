package com.mk.eap.file.client;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.mk.eap.utils.PropertyUtil;
import com.mk.eap.file.utils.BucketPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AliOSSClient
{
	
    public static final Logger logger = LoggerFactory.getLogger(AliOSSClient.class);
    private static volatile AliOSSClient aliOSSClient;
    private static OSSClient client;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String enablePostfix;

    
    private AliOSSClient()
    {
		endpoint = PropertyUtil.getPropertyByKey("endpoint");
		accessKeyId = PropertyUtil.getPropertyByKey("accessKeyId");
		accessKeySecret = PropertyUtil.getPropertyByKey("accessKeySecret");
		enablePostfix = PropertyUtil.getPropertyByKey("enablePostfix");
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public static AliOSSClient getInstance()
    {
        if(aliOSSClient == null)
            synchronized(AliOSSClient.class)
            {
                if(aliOSSClient == null)
                    aliOSSClient = new AliOSSClient();
            }
        return aliOSSClient;
    }



    public String upload(String bucketName, String fileName, byte fileContent[])
    {
        String result;
        try
        {
            if("false".equalsIgnoreCase(enablePostfix))
            {
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentType("image/jpeg");
                meta.setContentDisposition((new StringBuilder()).append("filename=\"").append(getShortFileName(fileName)).append("\"").toString());
                client.putObject(bucketName, rmPostfix(fileName), new ByteArrayInputStream(fileContent), meta);
            } else
            {
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentDisposition((new StringBuilder()).append("filename=\"").append(getShortFileName(fileName)).append("\"").toString());
                client.putObject(bucketName, fileName, new ByteArrayInputStream(fileContent), meta);
            }
            result = fileName;
        }
        catch(RuntimeException e)
        {
            result = null;
            logger.error("阿里云文件上传出错", e);
        }
        return result;
    }

    public byte[] download(String bucketName, String fileName)
    {
        byte fileContent[];
        try
        {
            OSSObject object;
            if("false".equalsIgnoreCase(enablePostfix))
                object = client.getObject(new GetObjectRequest(bucketName, rmPostfix(fileName)));
            else
                object = client.getObject(new GetObjectRequest(bucketName, fileName));
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte buff[] = new byte[100];
            for(int rc = 0; (rc = object.getObjectContent().read(buff, 0, 100)) > 0;)
                swapStream.write(buff, 0, rc);

            fileContent = swapStream.toByteArray();
        }
        catch(Exception e)
        {
            logger.error("阿里云文件下载异常", e);
            fileContent = null;
        }
        return fileContent;
    }

    public boolean delete(String bucketName, String fileName)
    {
        boolean flag = true;
        try
        {
            if("false".equalsIgnoreCase(enablePostfix))
                client.deleteObject(bucketName, rmPostfix(fileName));
            else
                client.deleteObject(bucketName, fileName);
        }
        catch(Exception e)
        {
            flag = false;
        }
        return flag;
    }

    public String getUrl(String bucketName, String fileName, int expired)
    {
        return getUrl(bucketName, BucketPermission.PRIVATE, fileName, expired);
    }

    public String getUrl(String bucketName, BucketPermission permission, String fileName, int expired)
    {
        Date expiration = new Date((new Date()).getTime() + (long)(expired * 1000));
        String result;
        if(permission == BucketPermission.PRIVATE)
        {
            URL fileUrl;
            if("false".equalsIgnoreCase(enablePostfix))
                fileUrl = client.generatePresignedUrl(bucketName, rmPostfix(fileName), expiration);
            else
                fileUrl = client.generatePresignedUrl(bucketName, fileName, expiration);
            result = fileUrl.toString();
            return result;
        }
        if(!((permission == BucketPermission.READ) || (permission == BucketPermission.FULL)))

        if(bucketName == null || "".equals(bucketName))
        {
            logger.error("阿里云获取url失败");
            return null;
        }
        if("false".equalsIgnoreCase(enablePostfix))
            result = (new StringBuilder()).append(bucketName).append('.').append(endpoint).append('/').append(rmPostfix(fileName)).toString();
        else
            result = (new StringBuilder()).append(bucketName).append('.').append(endpoint).append('/').append(fileName).toString();

        return result;
    }

    public List getFileList(String bucketName, String tenantFolder)
    {
        List fileList = new ArrayList();
        try
        {
            ObjectListing objectListing = client.listObjects((new ListObjectsRequest(bucketName)).withMaxKeys(Integer.valueOf(30)).withPrefix((new StringBuilder()).append(tenantFolder).append("/").toString()));
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            OSSObjectSummary s;
            for(Iterator<OSSObjectSummary> i$ = sums.iterator(); i$.hasNext(); fileList.add(s.getKey()))
                s = (OSSObjectSummary)i$.next();
        }
        catch(RuntimeException e)
        {
            logger.error("获取租户文件列表异常", e);
            fileList = null;
        }
        return fileList;
    }

    public BucketPermission getBucketAcl(String bucketName)
    {
        BucketPermission result = BucketPermission.PRIVATE;
        try
        {
            AccessControlList acl = client.getBucketAcl(bucketName);
            for(Iterator it = acl.getGrants().iterator(); it.hasNext();)
            {
                Grant gt = (Grant)it.next();
                if(gt.getPermission() == Permission.Read)
                    result = BucketPermission.READ;
                else
                if(gt.getPermission() == Permission.FullControl)
                    result = BucketPermission.FULL;
                else
                    result = BucketPermission.PRIVATE;
            }
        }
        catch(RuntimeException e)
        {
            logger.error("获取租户bucket权限异常，返回默认权限private", e);
        }
        return result;
    }
    
    private String rmPostfix(String fileName)
    {
        int stop = fileName.lastIndexOf(".");
        String name;
        if(stop != -1)
            name = fileName.substring(0, stop);
        else
            name = fileName;
        return name;
    }
    
    private String getShortFileName(String fileName)
    {
        int start = fileName.lastIndexOf("_") + 1;
        String shortName = fileName.substring(start);
        return shortName;
    }

}
