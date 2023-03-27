package com.valtech.poc.sms.dao;

import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.User;

public interface UserDAO {

	int getMidByMname(String managerName);

	void saveEmployee(Employee employee, int mId);

	void saveUser(User user, Employee eId);

	int getRidByRoleName(String role);

	void saveUserRole(int uId, int rId);

	void saveManager(int mId, int eId);

}