package com.valtech.poc.sms.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.valtech.poc.sms.entities.AttendanceTable;
import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.repo.AttendanceRepository;
import com.valtech.poc.sms.service.AdminService;
import com.valtech.poc.sms.service.MailContent;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private MailContent mailContent;
	
	private final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@ResponseBody
	@GetMapping("/foodCount/{ftDate}")
	public int getFoodCount(@PathVariable("ftDate") String ftDate) {
		logger.info("Fetching the food count");
		int count=adminService.getFoodCount(ftDate);
	    return count;
	}
	
	@ResponseBody
	@GetMapping("/foodCountWithJpa/{ftDate}")
		public int getCountByFtdate(@PathVariable("ftDate")String ftDate) {
		    return adminService.getCount(ftDate);
		}
	
    @ResponseBody
	  @GetMapping("/seatCount/{sbStartDate}")
    public int getCountBySbDate(@PathVariable("sbStartDate")String sbStartDate) {
    	logger.info("Fetching the seat booked count");
    	int count=adminService.getSeatBookedCount(sbStartDate);
        return count;
    	
    }
	
	    @ResponseBody
	    @PostMapping("/attendanceRegularization")
	    public String saveAttendance(@RequestBody AttendanceTable attendance) {
	    	Employee employee=adminService.getSpecificEmploye(attendance);
	     //   Manager manager = employee.getManagerDetails();
	        attendance.seteId(employee);
	        attendanceRepository.save(attendance);
	        mailContent.attendanceApprovalRequest(attendance);
	        return "saved";
	    }
	    
	    @ResponseBody
	    @PutMapping("/attendanceApproval/{atId}")
	    	public String approveAttendance(@PathVariable("atId") int atId) {
	    	    logger.info("Requesting approval");
	    	    adminService.updateAttendance(atId);
	    		return "approved";
	  
	    	}
	    
	    @ResponseBody
	    @GetMapping("/roleNames")
	    public List<String> findRoles() {
			logger.info("fetching all the roles");
			return adminService.findRoles();
		}
	    
	    @ResponseBody
	    @PostMapping("/automaticAttendance/{sbId}")
        public String AutomaticRegularization(@PathVariable("sbId") int sbId) {
	    	AttendanceTable attendance=new AttendanceTable();
	    	adminService.automaticRegularization(sbId,attendance);
	        attendanceRepository.save(attendance);
	        mailContent.attendanceApprovalRequest(attendance);
	        return "saved";
	    }
	
	    @ResponseBody
	    @GetMapping("/att/{atId}")
	    public AttendanceTable getList(@PathVariable("atId") int atId) {
	       return adminService.getList(atId);
	    }
	    
	    @ResponseBody
	    @GetMapping("/attendance")
	    public List<Map<String, Object>> getCompleteAttendanceList() {
	        return adminService.getCompleteAttendanceList();
	    	
	    }
	    
	    @ResponseBody
	    @GetMapping("/attendance/{atId}")
	    public Map<String, Object> getAttendanceListForEachEmployee(@PathVariable("atId") int atId) {    	
	    	  try {
	    	       return adminService.getAttendanceListForEachEmployee(atId);
	    	    } catch (EmptyResultDataAccessException ex) {
	    	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance details not found for id: " + atId);
	    	    }
	    	
	    }
	  
	 

	  
	
}