package cn.sharehe.handle.utils;

import cn.sharehe.handle.configure.JdbcConfigure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

/**
 * 数据连接池工具类
 * 线程安全
 */
public class DataConnectPool {
    private static int poolSize;  //线程池大小
    private static Connection[] connet;
    private static boolean[] status;
    private static Semaphore sem;
    private DataConnectPool(){}
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        poolSize= JdbcConfigure.getInstance().getConcurrencyPool();
        connet=new Connection[poolSize];
        status=new boolean[poolSize];
        sem=new Semaphore(poolSize);
    }
    public static Connection getConnection(int i){
        return connet[i];
    }
    /**
     * 获得一个连接
     * @return
     */
    public static int getConnection(){
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (DataConnectPool.class) {
            int i = 0;
            for (i = 0; i < poolSize; i++) {
                if (status[i])
                    ;
                else {
                    if (connet[i] != null) {
                        status[i] = true;
                        return i;
                    }
                    break;
                }
            }
            try {
                Connection con = DriverManager.getConnection(JdbcConfigure.getInstance().getUrl(), JdbcConfigure.getInstance().getUser(), JdbcConfigure.getInstance().getPassword());
                connet[i] = con;
                status[i] = true;
                return i;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 放弃一个连接
     * @param i
     */
    public static void closeConnect(int i){
        status[i]=false;
        sem.release();
    }

}
