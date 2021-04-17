package com.learn.utils;

import com.learn.datas.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    /**

     * 获取数据库连接

     * @author      作者姓名

     * @param$

     * @return

     * @exception

     * @date        2021/1/24 10:30

     */
    public static Connection getConnection(){
        //定义数据库连接
        String url = Constants.MYSQL_URL;
        String user = Constants.USERNAME;
        String password = Constants.PASSWORD;
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    /**

     * 关闭数据库连接

     * @author      作者姓名

     * @param$  数据库连接对象

     * @return

     * @exception

     * @date        2021/1/24 10:30

     */
    public static void close(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}