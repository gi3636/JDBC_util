package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//BaseDao的抽象类
public abstract class BaseDao {
    //要设置静态，在static里面才可以使用
    private static String url;
    private static String driver;
    private static String user;
    private static String password;

    //创建一个Connection的线程池，可以循环利用Connection
    private  ThreadLocal<Connection> conn= new ThreadLocal<Connection>();

    //让每次生成时自动启用
    static{
        //如果使用maven,jdbc.properties一定要放在resources的文件夹里
        //获取jdbc.properties的路径
        String propertiesPath=BaseDao.class.getClassLoader().getResource("jdbc.properties").getPath();
        Properties p=new Properties();
        try {
            //加载jdbc.properties文件的内容
            p.load(new FileInputStream(new File(propertiesPath)));
            System.out.println("路径:"+propertiesPath);
            //获取文件里的值
            url=p.getProperty("URL");
            driver=p.getProperty("DRIVER");
            user=p.getProperty("USER");
            password=p.getProperty("PASSWORD");
            //加载驱动，一次就好
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取连接
     * @return
     */
    protected  Connection getConnection() throws SQLException {
        if (conn.get()!=null){
            return conn.get();
        }
        else
            {
            //创建一个连接
            Connection c= DriverManager.getConnection(url,user,password);
            conn.set(c);
            return c;
        }
    }

    /**
     * 关闭连接
     */
    protected  void closeConnection(){
        if (conn.get()!=null){
            try {
                //关闭连接
                conn.get().close();
                //移除连接
                conn.remove();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


}
