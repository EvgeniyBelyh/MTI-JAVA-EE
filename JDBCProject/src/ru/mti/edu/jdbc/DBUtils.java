package ru.mti.edu.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ����� ������ ������ � ����� ������
 * @author ����� �������
 *
 */
public class DBUtils {
	
	/**
	 * ������� �������� ��������� �� ������� ���� ������
	 */
	public void createStoredProcMaxSalary() {
		//������� ���������� � ����� ������
		Connection connection = DBConnection.getConnection();
		try {
			Statement statement = connection.createStatement();
			//������� ���������, ���� ��� ���� �� �������
			statement.execute("DROP PROCEDURE IF EXISTS max_salary");
			
			//������� ���������
			statement.executeUpdate("create procedure max_salary() " +
			"begin " +
			"SELECT name, salary FROM employee " +
			"ORDER BY salary DESC LIMIT 1; " +
			"end");
			
			//��������� ���������� � ����� ������
			if(statement != null) {
				statement.close();
			}
			if(connection !=null) {
				connection.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
