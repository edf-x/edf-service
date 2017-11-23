package com.mk.eap.upgrade;
  
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
  
  
public class DbUtil {  
  
    private static Logger LOG =Logger.getLogger(DbUtil.class.getName());  
    
    public static void execSqlFiles(List<String> sqlFilePathList) throws Exception{  
          
        Exception error = null;  
        Connection conn = null;  
        try {  
        	System.out.println("连接数据库..."); 
        	conn = GetConnection();
        	System.out.println("参数设置..."); 
            ScriptRunner runner = new ScriptRunner(conn);  
            
            //下面配置不要随意更改，否则会出现各种问题  
            runner.setAutoCommit(true);//自动提交  
            runner.setFullLineDelimiter(false);  
            runner.setDelimiter(";");////每条命令间的分隔符  
            runner.setSendFullScript(false);  
            runner.setStopOnError(false);  
            runner.setLogWriter(null);//设置是否输出日志
            
        	System.out.println("执行脚本..."); 
            for (String sqlFilePath : sqlFilePathList)
            {
            	System.out.println("正在执行" + sqlFilePath + "..."); 
            	runner.runScript(new InputStreamReader(new FileInputStream(sqlFilePath),"utf-8")); 
            }
            runner.closeConnection();
            close(conn);  
            System.out.println("执行完成!"); 
        } catch (Exception e) {  
            LOG.error("执行sql文件进行数据库创建失败....",e);  
            error = e;  
        }finally{  
            close(conn);  
        }  
        if(error != null){  
            throw error;  
        }  
    }
    
    private static Connection GetConnection() {
        Connection conn = null; 
        
    	try {
	        Configuration configuration = new PropertiesConfiguration("config/db.properties");
	        String url = configuration.getString("jdbc.url");
	        String driver = configuration.getString("jdbc.driver");
	        String username = configuration.getString("jdbc.username");
	        String password = configuration.getString("jdbc.password");
	       
	        Class.forName(driver);  
	        conn = DriverManager.getConnection(url, username, password);  
	        return conn;
		} catch (Exception e) {
			LOG.error("数据库连接失败....",e);  
			return null;
	    } 
    }
      
    private static void close(Connection conn){  
        try {  
            if(conn != null){  
                conn.close();  
            }  
        } catch (Exception e) {  
            if(conn != null){  
                conn = null;  
            }  
        }  
    }  
}  