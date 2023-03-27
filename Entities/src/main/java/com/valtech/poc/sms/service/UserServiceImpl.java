package com.valtech.poc.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valtech.poc.sms.dao.UserDAO;
import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.User;
import com.valtech.poc.sms.repo.EmployeeRepo;
import com.valtech.poc.sms.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userepo;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
//	EmployeeRepo employeeRepo;
	EmployeeRepo empRepo;

	private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);
	
	@Override
	public User findByEmail(String email) {
		Employee emp = empRepo.findByMailId(email);
		User usr = userepo.findByEmpDetails(emp);
		System.out.println("usr= " + usr);
		return usr;
	}

	@Override
	public int getMidByMname(String managerName) {
		logger.info("fetching manager ID");
		int mId=userDAO.getMidByMname(managerName);
		logger.info("manager id is "+ mId);
		return mId;
	}

	@Override
	public void saveEmployee(Employee employee, int mId) {
		logger.info("Saving Employee info");
		userDAO.saveEmployee(employee,mId);
		
	}

	@Override
	public void saveUser(User user, Employee eId) {
		logger.info("Saving User Info");
		userDAO.saveUser(user,eId);
	}

	@Override
	public int getRidByRoleName(String role) {
		logger.info("Getting role_id by using name of the role");
		return userDAO.getRidByRoleName(role);
	}

	@Override
	public void saveUserRoles(int uId, int rId) {
		logger.info("Saving the User_roles");
		userDAO.saveUserRole(uId,rId);
	}

	@Override
	public void saveManager(int mId, int eId) {
		logger.info("Saving Manager");
		userDAO.saveManager(mId,eId);
	}

}