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
 * ����� ������������ ������� ������ �� ���� ������
 * � �������� ������ � ���� ������
 * @author ����� �������
 *
 */
public class DataAccess {
	/**
	 * ������ ������ ���������� �� ����� � �������������
	 * @param file - ��� �����
	 * @param delimiter - �����������
	 */
	public void importEmployeeData(String file, String delimiter) {
		
		FileReader fileReader = null;
		BufferedReader bufReader = null;

		try {
			//��������� ����
			fileReader = new FileReader(file);
			bufReader = new BufferedReader(fileReader);
			String line  = " ";
			//��������� ������ ����
			while((line = bufReader.readLine()) != null) {
				//��������� ������ �� �����������
				String[] row = line.split(delimiter);
				//������� ������ ���������
				Employee employee = new Employee(Integer.parseInt(row[0]), row[1], row[2], Double.parseDouble(row[3]), row[4], row[5]);
				//��������� ��������� � ���� ������
				saveEmployeeToDB(employee);
				
			}			
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			//��������� ���� ���� �� ������� ��������
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
	 * ��������� ������ ��������� � ���� ������
	 * ���� �������� � ����� �� id, �� ��������� ������ � ���� ������
	 * @param employee - ������ ���������
	 */
	public void saveEmployeeToDB(Employee employee) {
		//�������� ���������� � ����� ������
		Connection connection = DBConnection.getConnection(); 
		
		PreparedStatement statement;
		try {
			//���� �������� � id, ������� ��� ���� � ����, �� ������ ������ �� ��������� ������
			if(getEmployeeFromDB(employee.getEmployeeId()) != null) {
				statement = connection.prepareStatement("UPDATE employee SET employee_id = ?, name = ?, position = ?, salary = ?, login = ?, password = ? WHERE employee_id = ?");
				statement.setInt(7, employee.getEmployeeId());
			}
			else {
				//����� ������ �� ���������� ������
				statement = connection.prepareStatement("INSERT INTO employee(employee_id, name, position, salary, login, password) VALUES(?,?,?,?,?,?)");
				
			}
			//����������� ��������� � ������
			statement.setInt(1, employee.getEmployeeId());
			statement.setString(2, employee.getName());
			statement.setString(3, employee.getPosition());
			statement.setDouble(4, employee.getSalary());
			statement.setString(5, employee.getLogin());
			statement.setString(6, employee.getPassword());
			statement.execute();	
			
			//��������� ���������� � ����� ������
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
	 * ����� ������ � ��������� �� ���� ������
	 * @param id - id ���������
	 * @return - ������ ���������
	 */
	public Employee getEmployeeFromDB(int id) {
		//�������� ���������� � ����� ������
		Connection connection = DBConnection.getConnection(); 
		
		Employee employee = null;
		PreparedStatement statement;
		ResultSet resultSet;
		
		try {
			//�������� �������� ������ �� ����
			statement = connection.prepareStatement("SELECT * FROM employee WHERE employee_id = ?");
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			//������� ������ ���������
			while(resultSet.next()) {
				employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getString(6));
			}
			
			//��������� ���������� � ����� ������
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
	 * �������� ���������� �� ���� ���������� �� ���� ������
	 * @return - ������ �������� ����������
	 */
	public List<Employee> getListOfEmployeesFromDB() {
		//�������� ���������� � ����� ������
		Connection connection = DBConnection.getConnection(); 
		
		List<Employee> employees = new ArrayList<Employee>();
		PreparedStatement statement;
		ResultSet resultSet;
		
		try {
			//�������� �������� ������ �� ����
			statement = connection.prepareStatement("SELECT * FROM employee");
			resultSet = statement.executeQuery();
			
			//������� ������ ���������
			while(resultSet.next()) {
				Employee employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getString(6));
				//��������� ������ ��������� � ���������
				employees.add(employee);
			}
			
			//��������� ���������� � ����� ������
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
	 * ������� ������ � ��������� �� ���� ������
	 * @param id - id ���������
	 */
	public void deleteEmployeeFromDB(int id) {
		//�������� ���������� � ����� ������
		Connection connection = DBConnection.getConnection(); 		
		PreparedStatement statement;
		
		try {
			//�������� ������� ������ �� ����
			statement = connection.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
			statement.setInt(1, id);
			statement.execute();
			
			//��������� ���������� � ����� ������
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
	 * ������� � ������� ��������� � ����� ������� ���������
	 * ������� �������� ���������
	 */
	public void printMaxSalary() {
		//�������� ���������� � ����� ������
		Connection connection = DBConnection.getConnection(); 		
		CallableStatement statement;
		ResultSet resultSet;
		
		try {
			//�������� �������� ���������
			statement = connection.prepareCall("{call max_salary}");
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				System.out.println("������������ �������� � ���������: " + resultSet.getString(1) + ", ����� �������: " + resultSet.getDouble(2));
			}
			
			//��������� ���������� � ����� ������
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
