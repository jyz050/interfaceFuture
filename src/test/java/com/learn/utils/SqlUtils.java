package com.learn.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.apache.commons.lang3.StringUtils;
import pojo.Member;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SqlUtils {

    public static Object getSingerResult(String sql){
        if (StringUtils.isBlank(sql)) {
            return null;
        }
        //创建dbutils sql语句操作类
        QueryRunner runner = new QueryRunner();
        //获取数据库连接
        Connection conn = JDBCUtils.getConnection();
        //定义返回值
        Object result = null;
        try {
            //针对单行单列数据，创建ScalarHandler类
            ScalarHandler handler = new ScalarHandler();
            //执行sql语句
            result = runner.query(conn, sql, handler);
            //关闭数据库连接
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn);
        }
        return result;
    }
    /**
     * 发起数据库一行数据的查询
     *
     * @return 查询结果
     * @author 作者姓名
     * @param$ sql语句
     * @date 2021/1/24 10:32
     */
    public static Object scarlarHandler(String sql) {
//        QueryRunner runner = new QueryRunner();
//        String sql = "select count(*) from  member where id = 991 ;";
//        Connection conn = JDBCUtils.getConnection();
//        ScalarHandler<Long> handler = new ScalarHandler();
//        Long count = runner.query(conn, sql, handler);
//        System.out.println(count);
//        JDBCUtils.close(conn);
        Object result = null;
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        try {
            ScalarHandler<Object> handler = new ScalarHandler();
            result = runner.query(conn, sql, handler);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn);
        }
        return result;
    }
    /**
     * 发起数据库单行的查询
     *
     * @return 查询单条数据结果
     * @author 作者姓名
     * @param$ sql语句
     * @date 2021/1/24 10:32
     */
    public static Map<String, Object> quarryOne(String sql) {
        Map<String, Object> result = null;
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        try {
            MapHandler handler = new MapHandler();
            result = runner.query(conn, sql, handler);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(conn);
            return result;
        }
    }
        //查询多条结果
        public static List<Map<String, Object>> quarryAll(String sql){
            Connection conn = JDBCUtils.getConnection();
            QueryRunner runner = new QueryRunner();
            List<Map<String, Object>> result = null;
            try {
                MapListHandler handler = new MapListHandler();
                result = runner.query(conn, sql, handler);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                JDBCUtils.close(conn);
            }
            return result;
        }
        /**
         * 发起数据库一行数据的插入
         * @author 作者姓名

         * @param$ sql语句

         * @return 查询结果

         * @date 2021/1/24 10:32

         */
        public static void insert (String sql){
            QueryRunner runner = new QueryRunner();
            try {
                runner.update(JDBCUtils.getConnection(), sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                JDBCUtils.close(JDBCUtils.getConnection());
            }
        }
        /**
         * 发起数据库一行数据的修改
         * @author 作者姓名

         * @param$ sql语句

         * @return 查询结果

         * @date 2021/1/24 10:32

         */
        public static void update (String sql){
            try {
                QueryRunner runner = new QueryRunner();
                int count = runner.update(JDBCUtils.getConnection(), sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                JDBCUtils.close(JDBCUtils.getConnection());
            }
        }
}
