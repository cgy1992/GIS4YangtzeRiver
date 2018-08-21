package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtil {
	final private static String driverClass="org.postgresql.Driver";// ������
    final private static String url="jdbc:postgresql://localhost:5432/postgis_24_sample";// ���ݿ��ַ
    final private static String username="postgres";// �û���
    final private static String password="970804";// ����
    public String getDriverClass() {
        return driverClass;
    }
    public String getUrl() {
        return url;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public static Connection getConn() {
         Connection conn = null ;
         try {
            Class.forName(driverClass);
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        return conn;
    }
    public static void main(String[] args) {    	
        Connection conn = ConnUtil.getConn();
        if(conn!=null){
            System.out.println("���ݿ����ӳɹ���");
        }
    }
}
