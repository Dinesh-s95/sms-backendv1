package com.valtech.poc.sms.controller;

import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.User;

public class EmployeeRequest {
	private Employee employee;
    private User user;
    private String managerName;
    private String role;
    
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public EmployeeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeRequest(Employee employee, User user, String managerName, String role) {
		super();
		this.employee = employee;
		this.user = user;
		this.managerName = managerName;
		this.role = role;
	}
	@Override
	public String toString() {
		return "EmployeeRequest [employee=" + employee + ", user=" + user + ", managerName=" + managerName + ", role="
				+ role + "]";
	}
    
    

}
