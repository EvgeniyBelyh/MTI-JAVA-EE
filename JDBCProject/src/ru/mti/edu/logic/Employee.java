package ru.mti.edu.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Класс работника
 * @author Белых Евгений
 *
 */
public class Employee {
	
	private int employeeId; // id работника
	private String name; // ФИО работника
	private String position; // должность работника
	private double salary; // зарплата работника
	private String login; // логин работника
	private String password; // хэш пароля
	
	//конструкторы
	public Employee() {
	}
	
	public Employee(int employeeId, String name, String position, double salary, String login, String password) {
		this.employeeId = employeeId;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.login = login;
		this.password = encryptPassword(password); // создаем хэш пароля
	}
	
	//геттеры и сеттеры
	public int getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encryptPassword(password); // создаем хэш пароля
	}
	
	//переопределяем метод вывода в консокль
	@Override
	public String toString() {
		return "id: " + this.employeeId + ", ФИО: " + this.name + ", Должность: " + this.position +
				", Оклад: " + this.salary + ", Логин: " + this.login + ", Пароль: " + this.password;
	}
	
	/**
	 * Создает MD5 хэш пароля для хранения в базе данных
	 * @param pass - пароль
	 * @return - хэш пароля
	 */
	private String encryptPassword(String pass) {
		
        String encryptedPassword = null;
        try {
            // создаем объект MessageDigest для создания MD5 хэшей
            MessageDigest md = MessageDigest.getInstance("MD5");
            //добавляем биты пароля
            md.update(pass.getBytes());
            //получаем биты хэша
            byte[] bytes = md.digest();
            //конвертируем в шестьнадцатиричный формат
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //получаем строку хэша
            encryptedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        
        return encryptedPassword;
    }
}
