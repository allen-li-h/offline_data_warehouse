package tools;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：DruidTools
 * @Date：2024年01月19日 0019 12:37:39
 */
public class DruidTools {
    public static DataSource dataSource_dim;
    public static DataSource dataSource_policy;
    /**
     * 初始化数据库连接池
     */
    static {
        try {
            InputStream inputStream_dim = DruidTools.class.getClassLoader().getResourceAsStream("druid_dim.properties");
            Properties properties_dim = new Properties();
            properties_dim.load(inputStream_dim);
            dataSource_dim = DruidDataSourceFactory.createDataSource(properties_dim);

            InputStream inputStream_policy = DruidTools.class.getClassLoader().getResourceAsStream("druid_policy.properties");
            Properties properties_policy = new Properties();
            properties_policy.load(inputStream_policy);
            dataSource_policy = DruidDataSourceFactory.createDataSource(properties_policy);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库连接对象
     * @return
     */
    public static Connection getConnection(String database) {
        Connection conn_dim = null;
        Connection conn_policy = null;
        try {
            conn_dim = dataSource_dim.getConnection();
            conn_policy = dataSource_policy.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return database == "dim" ? conn_dim:conn_policy;
    }


    /**
     * 释放数据库资源
     * @param conn
     * @param statement
     */
    public static void close(Connection conn, Statement statement) {
        if (conn != null && statement != null) {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 释放数据库资源
     * @param conn
     * @param statement
     * @param resultSet
     */
    public static void close(Connection conn, Statement statement,ResultSet resultSet) {
        if (conn != null && statement != null && resultSet!= null) {
            try {
                resultSet.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
