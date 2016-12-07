package ru.mti.edu.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ����� ������������ ����������� � ���� ������
 * @author ����� �������
 *
 */
public class DBConnection {
	
	/**
	 * ������� ����������� � ���� ������
	 */
	public static Connection getConnection() {
		
		Connection connection = null;
		//����� ��������� ������� � ���� ������ �� ����� �������
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/ru/mti/edu/jdbc/dbinfo.properties"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {   
			//���������� �������
			Class.forName("com.mysql.jdbc.Driver");		   
			//������� ������ ����������� � ���� ������
			connection = DriverManager.getConnection(prop.getProperty("db.url"),prop.getProperty("db.login"),prop.getProperty("db.password"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
		
	}
	

	
	
	
}
