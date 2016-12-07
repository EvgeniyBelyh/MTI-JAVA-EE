package ru.mti.edu.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ����� ���������
 * @author ����� �������
 *
 */
public class Employee {
	
	private int employeeId; // id ���������
	private String name; // ��� ���������
	private String position; // ��������� ���������
	private double salary; // �������� ���������
	private String login; // ����� ���������
	private String password; // ��� ������
	
	//������������
	public Employee() {
	}
	
	public Employee(int employeeId, String name, String position, double salary, String login, String password) {
		this.employeeId = employeeId;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.login = login;
		this.password = encryptPassword(password); // ������� ��� ������
	}
	
	//������� � �������
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
		this.password = encryptPassword(password); // ������� ��� ������
	}
	
	//�������������� ����� ������ � ��������
	@Override
	public String toString() {
		return "id: " + this.employeeId + ", ���: " + this.name + ", ���������: " + this.position +
				", �����: " + this.salary + ", �����: " + this.login + ", ������: " + this.password;
	}
	
	/**
	 * ������� MD5 ��� ������ ��� �������� � ���� ������
	 * @param pass - ������
	 * @return - ��� ������
	 */
	private String encryptPassword(String pass) {
		
        String encryptedPassword = null;
        try {
            // ������� ������ MessageDigest ��� �������� MD5 �����
            MessageDigest md = MessageDigest.getInstance("MD5");
            //��������� ���� ������
            md.update(pass.getBytes());
            //�������� ���� ����
            byte[] bytes = md.digest();
            //������������ � ������������������ ������
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //�������� ������ ����
            encryptedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        
        return encryptedPassword;
    }
}
