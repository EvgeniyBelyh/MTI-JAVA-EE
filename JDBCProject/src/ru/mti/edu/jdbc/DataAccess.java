package ru.mti.edu.jdbc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.mti.edu.logic.Employee;

/**
 * Класс обеспечивает возврат данных из базы данных
 * и передачу данных в базу данных
 * @author Белых Евгений
 *
 */
public class DataAccess {
	/**
	 * Импорт списка работников из файла с разделителями
	 * @param file - имя файла
	 * @param delimiter - разделитель
	 */
	public void importEmployeeData(String file, String delimiter) {
		
		FileReader fileReader = null;
		BufferedReader bufReader = null;

		try {
			//открываем файл
			fileReader = new FileReader(file);
			bufReader = new BufferedReader(fileReader);
			String line  = " ";
			//построчно читаем файл
			while((line = bufReader.readLine()) != null) {
				//разбираем строку по разделителю
				String[] row = line.split(delimiter);
				//создаем объект работника
				Employee employee = new Employee(Integer.parseInt(row[0]), row[1], row[2], Double.parseDouble(row[3]), row[4], row[5]);
				//сохраняем работника в базе данных
				saveEmployeeToDB(employee);
				
			}			
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			//закрываем файл если он остался открытым
			if(fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}	
	
	/**
	 * Сохраняет объект работника в базу данных
	 * Если работник с таким же id, то обновляет данные в базе данных
	 * @param employee - объект работника
	 */
	public void saveEmployeeToDB(Employee employee) {
		//получаем соединение с базой данных
		Connection connection = DBConnection.getConnection(); 
		
		PreparedStatement statement;
		try {
			//если работник с id, который уже есть в базе, то делаем запрос на изменение данных
			if(getEmployeeFromDB(employee.getEmployeeId()) != null) {
				statement = connection.prepareStatement("UPDATE employee SET employee_id = ?, name = ?, position = ?, salary = ?, login = ?, password = ? WHERE employee_id = ?");
				statement.setInt(7, employee.getEmployeeId());
			}
			else {
				//иначе запрос на добавление данных
				statement = connection.prepareStatement("INSERT INTO employee(employee_id, name, position, salary, login, password) VALUES(?,?,?,?,?,?)");
				
			}
			//подставляем параметры в запрос
			statement.setInt(1, employee.getEmployeeId());
			statement.setString(2, employee.getName());
			statement.setString(3, employee.getPosition());
			statement.setDouble(4, employee.getSalary());
			statement.setString(5, employee.getLogin());
			statement.setString(6, employee.getPassword());
			statement.execute();	
			
			//закрываем соединение с базой данных
			if(statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Выбор данных о работнике из базы данных
	 * @param id - id работника
	 * @return - объект работника
	 */
	public Employee getEmployeeFromDB(int id) {
		//получаем соединение с базой данных
		Connection connection = DBConnection.getConnection(); 
		
		Employee employee = null;
		PreparedStatement statement;
		ResultSet resultSet;
		
		try {
			//пытаемся получить данные из базы
			statement = connection.prepareStatement("SELECT * FROM employee WHERE employee_id = ?");
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			//создаем объект работника
			while(resultSet.next()) {
				employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getString(6));
			}
			
			//закрываем соединение с базой данных
			if(resultSet != null) {
				resultSet.close();
			}			
			if(statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
		return employee;
	}
	
	/**
	 * Выбирает информацию по всем работникам из базы данных
	 * @return - список объектов работников
	 */
	public List<Employee> getListOfEmployeesFromDB() {
		//получаем соединение с базой данных
		Connection connection = DBConnection.getConnection(); 
		
		List<Employee> employees = new ArrayList<Employee>();
		PreparedStatement statement;
		ResultSet resultSet;
		
		try {
			//пытаемся получить данные из базы
			statement = connection.prepareStatement("SELECT * FROM employee");
			resultSet = statement.executeQuery();
			
			//создаем объект работника
			while(resultSet.next()) {
				Employee employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getString(6));
				//добавляем объект работника в коллекцию
				employees.add(employee);
			}
			
			//закрываем соединение с базой данных
			if(resultSet != null) {
				resultSet.close();
			}			
			if(statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
		return employees;
	}
	
	/**
	 * Удаляет запись о работнике из базы данных
	 * @param id - id работника
	 */
	public void deleteEmployeeFromDB(int id) {
		//получаем соединение с базой данных
		Connection connection = DBConnection.getConnection(); 		
		PreparedStatement statement;
		
		try {
			//пытаемся удалить данные из базы
			statement = connection.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
			statement.setInt(1, id);
			statement.execute();
			
			//закрываем соединение с базой данных
			if(statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Выводит в консоль работника с самой большой зарплатой
	 * вызывая хранимую процедуру
	 */
	public void printMaxSalary() {
		//получаем соединение с базой данных
		Connection connection = DBConnection.getConnection(); 		
		CallableStatement statement;
		ResultSet resultSet;
		
		try {
			//вызываем хранимую процедуру
			statement = connection.prepareCall("{call max_salary}");
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				System.out.println("Максимальная зарплата у работника: " + resultSet.getString(1) + ", сумма выплаты: " + resultSet.getDouble(2));
			}
			
			//закрываем соединение с базой данных
			if(resultSet != null) {
				resultSet.close();
			}
			if(statement != null) {
				statement.close();
			}
			if(connection != null) {
				connection.close();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
	}
	
}
