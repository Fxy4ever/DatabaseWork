package com.ccz.votesystem.dao;

import java.sql.*;

public class DBOperator {
    protected Connection conn = null;
    protected PreparedStatement ps = null;
    protected ResultSet rs = null;

    public Connection getConn(String server, String dbName, String dbUser, String dbPwd) {
        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String URL = "jdbc:mysql://" + server + ":3306/" + dbName + "?user=" + dbUser + "&password=" + dbPwd + "&useUnicode=true&characterEncoding=utf8&useSSL=false";
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关闭连接
    public void closeAll() {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public ResultSet executeQuery(String prepareSql,String[] params){
        try {
            ps = conn.prepareStatement(prepareSql);
            if(params!=null){
                for (int i = 0; i < params.length; i++) {
                    ps.setString(i + 1,params[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String preparedSql,String[] params){
        int num = 0;
        try {
            ps = conn.prepareStatement(preparedSql);
            if(ps != null){
                for (int i = 0; i < params.length; i++) {
                    ps.setString(i + 1,params[i]);
                }
            }
            if (ps != null) {
                num = ps.executeUpdate();
                return num;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean executeSql(String sql){
        try {
            ps = conn.prepareStatement(sql);
            if(ps!=null){
                return ps.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
