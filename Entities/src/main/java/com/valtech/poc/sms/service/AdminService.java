package com.valtech.poc.sms.service;

import java.util.List;
import java.util.Map;

import com.valtech.poc.sms.entities.AttendanceTable;
import com.valtech.poc.sms.entities.Employee;

public interface AdminService {

	int getFoodCount(String ftDate);

	int getSeatBookedCount(String sbStartDate);

	int getCount(String ftDate);
	
	List<String> findRoles();

	String generateQrCode(int empId);

	List<Map<String, Object>> getRegistrationListForApproval();

	List<String> findShiftStartTimings();

	List<String> findShiftEndTimings();




}