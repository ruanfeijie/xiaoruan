package oracleUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.driver.OracleTypes;

public class JdbcTest {
	public static void main(String[] args) {
		Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preState = null;
        CallableStatement call = null;
        try {
        	Driver driver = new OracleDriver();
			DriverManager.deregisterDriver(driver);
			Properties pro = new Properties();
            pro.put("user", "scott");
            pro.put("password", "password");
            connect = driver.connect("jdbc:oracle:thin:@localhost:1521:orcl", pro);
            // 测试是否正确
            System.out.println(connect);  
            call  = connect.prepareCall("call autoproc(?)");
            call.registerOutParameter(1, OracleTypes.NUMBER);
            call.setInt(1, 1);
            call.execute();
            connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            //第六步：关闭资源
            try {
                if (resultSet!=null) resultSet.close();
                if (statement!=null) statement.close();
                if (preState!=null) statement.close();
                if (connect!=null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
	}
}
