package com.valtech.poc.sms.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.valtech.poc.sms.dao.AdminDao;
import com.valtech.poc.sms.dao.UserDAO;
import com.valtech.poc.sms.entities.Food;
import com.valtech.poc.sms.entities.User;
import com.valtech.poc.sms.repo.AdminRepository;
import com.valtech.poc.sms.repo.SeatsBookedRepo;
import com.valtech.poc.sms.repo.UserRepo;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AdminServiceImpl implements AdminService{
	
	@Autowired 
	private AdminDao adminDao;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired 
	SeatsBookedRepo seatsBookedRepo;
	
	@Autowired
	ResetPassword resetPassword;
	
	@Override
	public String generateQrCode(int empId) {
		String code ="" + empId + resetPassword.getRandomNumberString();
		return code;
	}
	
	@Override
	public int getFoodCount(String ftDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
		LocalDateTime dateTime = LocalDateTime.parse(ftDate, formatter);
		System.out.println(ftDate);
		return adminDao.getFoodCount(dateTime);
		
	}

	@Override
	public int getSeatBookedCount(String sbStartDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
		LocalDateTime dateTime = LocalDateTime.parse(sbStartDate, formatter);
		System.out.println(sbStartDate);
		return adminDao.getSeatBookedCount(dateTime);
	}

	@Override
	public int getCount(String ftDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
		LocalDateTime dateTime = LocalDateTime.parse(ftDate, formatter);
		Food f= adminRepository.getFoodByFtDate(dateTime);
		return f.getCount();
	}
	
	@Override
	public List<String> findShiftStartTimings() {
		return adminDao.findShiftStartTimings();
	}

	@Override
	public List<String> findShiftEndTimings() {
		return adminDao.findShiftEndTimings();
	}
	

	@Override
	public List<String> findRoles() {
		return adminDao.findRoles();
	}

	@Override
	public void ApproveRegistration(int uId) {
		// TODO Auto-generated method stub
		
		adminDao.approroveRegistration(uId);
		
	}

	@Override
	public void deleteUser(int empId) {
		// TODO Auto-generated method stub
		User u=userRepo.findByEmpId(empId);
		
		userDao.deleteUserRoles(u.getuId());
		adminDao.deleteUser(u.getuId());
//		employeeRepo.deleteById(u.getEmpDetails().geteId());
		userDao.deleteEmployee(u.getEmpDetails());
	
	}
	public List<Map<String, Object>> getRegistrationListForApproval() {
		return adminDao.getRegistrationListForApproval();
	}

}