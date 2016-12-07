package ru.mti.edu.logic;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ru.mti.edu.jdbc.*;

/**
 * ����� ������������ ����������� ������ � ����� ������
 * @author ����� �������
 *
 */
public class TestJDBC {
	
	public static void main(String[] args) {
		//������� ������ ������� � ������
		DataAccess dataAccess = new DataAccess();
		System.out.println("����������� ������ �� �����");
		dataAccess.importEmployeeData("employee.csv",";");
		
		//����� ������� ���������� �� ���� ������
		List<Employee> employees = dataAccess.getListOfEmployeesFromDB();
		//��������� ���������� � ������� �������� id
		employees.sort(new Comparator<Employee>() {
			public int compare(Employee e1, Employee e2) {
				return e2.getEmployeeId() - e1.getEmployeeId();
			}
		});
		
		//������� ������ ���������� � �������
		System.out.println("������ ����������");
		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println(employee);
		}
		
		//������ ��������� � ������ �� ����������
		System.out.println("��������� �� ��������� �������� �������� ������ ��������� �����������");
		Employee updateEmployee = dataAccess.getEmployeeFromDB(4);
		updateEmployee.setName("����� �������� ����������");
		dataAccess.saveEmployeeToDB(updateEmployee);
		
		System.out.println("��������� �� ��������� ��������� �������� ������� �������� ���������");
		updateEmployee = dataAccess.getEmployeeFromDB(10);
		updateEmployee.setName("������ �������� ��������");
		dataAccess.saveEmployeeToDB(updateEmployee);
		
		System.out.println("������ �������� ������ �� ���� ������");
		//����� ������� ���������� �� ���� ������
		employees = dataAccess.getListOfEmployeesFromDB();
		//������� ������ ���������� � �������
		System.out.println("������ ����������");
		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println(employee);
		}
		
		System.out.println("��������� ���������� � id 3,6,7");
		dataAccess.deleteEmployeeFromDB(3);
		dataAccess.deleteEmployeeFromDB(6);
		dataAccess.deleteEmployeeFromDB(7);
		
		System.out.println("������ �������� ������ �� ���� ������");
		//����� ������� ���������� �� ���� ������
		employees = dataAccess.getListOfEmployeesFromDB();
		//������� ������ ���������� � �������
		System.out.println("������ ����������");
		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println(employee);
		}
		
		//������� �������� ���������
		System.out.println("������� �������� ���������");
		DBUtils dbUtils = new DBUtils();
		dbUtils.createStoredProcMaxSalary();
		//�������� �������� ���������
		System.out.println("�������� �������� ���������");
		dataAccess.printMaxSalary();
	}
}
