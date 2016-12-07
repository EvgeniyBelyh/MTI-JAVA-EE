package ru.mti.edu.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс утилит работы с базой данных
 * @author Белых Евгений
 *
 */
public class DBUtils {
	
	/**
	 * Создает хранимую процедуру на сервере базы данных
	 */
	public void createStoredProcMaxSalary() {
		//создаем соединение с базой данных
		Connection connection = DBConnection.getConnection();
		try {
			Statement statement = connection.createStatement();
			//удаляем процедуру, если она есть на сервере
			statement.execute("DROP PROCEDURE IF EXISTS max_salary");
			
			//создаем процедуру
			statement.executeUpdate("create procedure max_salary() " +
			"begin " +
			"SELECT name, salary FROM employee " +
			"ORDER BY salary DESC LIMIT 1; " +
			"end");
			
			//закрываем соединение с базой данных
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
