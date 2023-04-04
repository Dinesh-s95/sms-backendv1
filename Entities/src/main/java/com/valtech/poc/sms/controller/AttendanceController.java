package com.valtech.poc.sms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.valtech.poc.sms.entities.AttendanceTable;
import com.valtech.poc.sms.entities.DateUtil;
import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.Seat;
import com.valtech.poc.sms.entities.SeatsBooked;
import com.valtech.poc.sms.repo.AttendanceRepository;
import com.valtech.poc.sms.repo.EmployeeRepo;
import com.valtech.poc.sms.repo.SeatRepo;
import com.valtech.poc.sms.repo.SeatsBookedRepo;
import com.valtech.poc.sms.service.AdminService;
import com.valtech.poc.sms.service.AttendanceService;
import com.valtech.poc.sms.service.MailContent;
import com.valtech.poc.sms.service.SeatBookingService;

@Controller
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private SeatRepo seatRepo;
	
	@Autowired
	private SeatsBookedRepo seatsBookedRepo;

	@Autowired
	private MailContent mailContent;
	
	@Autowired
	private AdminService adminService;

	private final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@ResponseBody
	@PostMapping("/attRegularization")
	public String saveAttendance(@RequestBody AttendanceTable attendance) {
		Employee employee = attendanceService.getSpecificEmployee(attendance);
		attendance.seteId(employee);
		attendanceRepository.save(attendance);
		mailContent.attendanceApprovalRequest(attendance);
		return "saved";
	}

	@ResponseBody
	@PostMapping("/attendanceRegularization/{eId}")
	public void saveAttendance(@PathVariable("eId") int eId,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,@RequestParam("shiftStart")String shiftStart,@RequestParam("shiftEnd")String shiftEnd ) {
		logger.info("Request to save the attendance");
		if(startDate.equals(endDate)) {
			System.out.println("daily");
		attendanceService.saveAttendance(eId,startDate,endDate,shiftStart,shiftEnd);
		}
		else {
			System.out.println("weekly");
	    attendanceService.saveAttendanceForMultipleDays(eId,startDate,endDate,shiftStart,shiftEnd);
		}
	}

//	@ResponseBody
//	@PostMapping("/automaticAttendance/{sbId}")
//	public String AutomaticRegularization(@PathVariable("sbId") int sbId) {
//		AttendanceTable attendance = new AttendanceTable();
//		attendanceService.automaticRegularization(sbId, attendance);
//		attendanceRepository.save(attendance);
//		mailContent.attendanceApprovalRequest(attendance);
//		return "saved";
//	}

	@ResponseBody
	@PutMapping("/attendanceApproval/{atId}")
	public String approveAttendance(@PathVariable("atId") int atId) {
		logger.info("Requesting approval");
		String mail = attendanceService.getMailIdByAtId(atId);
		attendanceService.updateAttendance(atId,mail);
		return "approved";

	}

	@ResponseBody
	@DeleteMapping("/disapproveAttendance/{atId}")
	public String deleteAttendanceRequest(@PathVariable("atId") int atId) {
		String mail = attendanceService.getMailIdByAtId(atId);
		attendanceService.deleteAttendanceRequest(atId,mail);
		return "disapproved";
	}

	@ResponseBody
	@GetMapping("/attWithManagerDetails/{atId}")
	public AttendanceTable getListWithManagerDetails(@PathVariable("atId") int atId) {
		return attendanceService.getList(atId);
	}

	@ResponseBody
	@GetMapping("/attendanceList")
	public List<Map<String, Object>> getCompleteAttendanceList() {
		return attendanceService.getCompleteAttendanceList();

	}

	@ResponseBody
	@GetMapping("/attendanceByAtId/{atId}")
	public Map<String, Object> getAttendanceEachEmployeeBasedOnAttendanceId(@PathVariable("atId") int atId) {
		try {
			return attendanceService.getAttendanceListForEachEmployee(atId);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendance details not found for id: " + atId);
		}

	}

	@ResponseBody
	@GetMapping("/employeeAttendanceByEId/{eId}")
	public List<Map<String, Object>> getAttendanceForEmployeeBasedOnEmployeeId(@PathVariable("eId") int eId) {
		try {
			return attendanceService.getAttendanceForEmployeeBasedOnEmployeeId(eId);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Attendance details not found for employeeid: " + eId);
		}

	}

	@ResponseBody
	@GetMapping("/attendanceApproval/{eId}")
	public List<Map<String, Object>> getAttendanceListForApproval(@PathVariable("eId") int eId) {
		try {
			return attendanceService.getAttendanceListForApproval(eId);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Attendance details not found for employeeid: " + eId);
		}

	}

	@ResponseBody
	@PostMapping("/WeeklySeatBooking/{eId}")
	public synchronized ResponseEntity<String> HandlingWeeklySeatBooking(@PathVariable("eId") int eId,@RequestParam("sId") int sId,@RequestParam("from") String from,@RequestParam("to")String to) {
		Employee emp = employeeRepo.findById(eId).get();
		Seat seat = seatRepo.findById(sId).get();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
		LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
		LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
		 LocalDate fromDate = fromDateTime.toLocalDate();
	        LocalDate toDate = toDateTime.toLocalDate();
		  List<LocalDate> dates = DateUtil.getDatesBetween(fromDate, toDate);
		  System.out.println(dates);
		  for (LocalDate date : dates) {
			  System.out.println(date);
			  SeatsBooked sb= new SeatsBooked();
			  LocalDateTime localDateTime = date.atStartOfDay();
			  sb.seteId(emp);
			  sb.setSbDate(localDateTime);
			  sb.setsId(seat);
			  String code = adminService.generateQrCode(eId);
			  sb.setCode(code);
			  sb.setNotifStatus(false);
			  sb.setCurrent(true);
			  seatsBookedRepo.save(sb);
				}
		  return ResponseEntity.ok("Seats booked successfully " ); 
	}
}

