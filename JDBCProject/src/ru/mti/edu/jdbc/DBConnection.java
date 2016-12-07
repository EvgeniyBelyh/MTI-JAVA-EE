package ru.mti.edu.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс обеспечивает подключение к базе данных
 * @author Белых Евгений
 *
 */
public class DBConnection {
	
	/**
	 * создает подключение к базе данных
	 */
	public static Connection getConnection() {
		
		Connection connection = null;
		//берем параметры доступа к базе данных из файла свойств
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/ru/mti/edu/jdbc/dbinfo.properties"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {   
			//определяем драйвер
			Class.forName("com.mysql.jdbc.Driver");		   
			//создаем объект подключения к базе данных
			connection = DriverManager.getConnection(prop.getProperty("db.url"),prop.getProperty("db.login"),prop.getProperty("db.password"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
		
	}
	

	
	
	
}
