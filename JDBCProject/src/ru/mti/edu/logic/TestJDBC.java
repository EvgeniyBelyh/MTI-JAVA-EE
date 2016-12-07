package ru.mti.edu.logic;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ru.mti.edu.jdbc.*;

/**
 * Класс тестирования функционала работы с базой данных
 * @author Белых Евгений
 *
 */
public class TestJDBC {
	
	public static void main(String[] args) {
		//создаем объект доступа к данным
		DataAccess dataAccess = new DataAccess();
		System.out.println("Импортируем данные из файла");
		dataAccess.importEmployeeData("employee.csv",";");
		
		//берем объекты работников из базы данных
		List<Employee> employees = dataAccess.getListOfEmployeesFromDB();
		//сортируем работников в порядке убывания id
		employees.sort(new Comparator<Employee>() {
			public int compare(Employee e1, Employee e2) {
				return e2.getEmployeeId() - e1.getEmployeeId();
			}
		});
		
		//выводим список работников в консоль
		System.out.println("Список работников");
		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println(employee);
		}
		
		//вносим изменения в данные по работникам
		System.out.println("Назначаем на должность Главного инженера Титова Владимира Алексеевича");
		Employee updateEmployee = dataAccess.getEmployeeFromDB(4);
		updateEmployee.setName("Титов Владимир Алексеевич");
		dataAccess.saveEmployeeToDB(updateEmployee);
		
		System.out.println("Назначаем на должность Помошника инженера Сысоева Геннадия Петровича");
		updateEmployee = dataAccess.getEmployeeFromDB(10);
		updateEmployee.setName("Сысоев Геннадий Петрович");
		dataAccess.saveEmployeeToDB(updateEmployee);
		
		System.out.println("Заново выбираем данные из базы данных");
		//берем объекты работников из базы данных
		employees = dataAccess.getListOfEmployeesFromDB();
		//выводим список работников в консоль
		System.out.println("Список работников");
		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println(employee);
		}
		
		System.out.println("Увольняем работников с id 3,6,7");
		dataAccess.deleteEmployeeFromDB(3);
		dataAccess.deleteEmployeeFromDB(6);
		dataAccess.deleteEmployeeFromDB(7);
		
		System.out.println("Заново выбираем данные из базы данных");
		//берем объекты работников из базы данных
		employees = dataAccess.getListOfEmployeesFromDB();
		//выводим список работников в консоль
		System.out.println("Список работников");
		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println(employee);
		}
		
		//создаем хранимую процедуру
		System.out.println("Создаем хранимую процедуру");
		DBUtils dbUtils = new DBUtils();
		dbUtils.createStoredProcMaxSalary();
		//вызываем хранимую процедуру
		System.out.println("Вызываем хранимую процедуру");
		dataAccess.printMaxSalary();
	}
}
