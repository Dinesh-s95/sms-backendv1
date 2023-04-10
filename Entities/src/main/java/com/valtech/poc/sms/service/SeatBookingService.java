package com.valtech.poc.sms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.Seat;
import com.valtech.poc.sms.entities.SeatsBooked;

public interface SeatBookingService {

	List<Integer> getAllSeats();

	// List<Seat> findAvailableSeats();

	List<String> availableSeats();
//
//	int totalSeats();

	List<Integer> countTotalSeats();

	// List<Integer> getSeatById();

//	List<SeatsBooked> findEmployeeWiseSeatsBooked(Employee emp);

	SeatsBooked findCurrentSeatBookingDetails(Employee emp);

	List<Seat> findAvailableSeats();

	List<SeatsBooked> findAllByEId(Employee emp);

	List<Seat> findAvailableSeatsByDate(LocalDate date);

	void bookSeat(SeatsBooked seatsBooked);

	SeatsBooked saveSeatsBookedDetails(SeatsBooked seatsBooked);

	// void updateNotifStatus(int sbId);

	void notifStatus(int sbId);

	// List<SeatsBooked> getSeatBookingsByEId(int eId);

	boolean CheckIfTheSameSeatBookingRecurring(int eId);

	Seat getSeatById(int sId);

	// boolean canEmployeeBookSeat(int eId, int sId,LocalDate sbDate) throws
	// ServiceException;

	String createSeatsBookedDaily(int eId, int sId, int stId, String from, String to,Boolean food);

	String createSeatsBookedWeekly(int eId, int sId, int stId, String from, String to,Boolean food);

	List<SeatsBooked> getSeatsBookedByEmployeeAndDate(int empId, LocalDateTime startDate, LocalDateTime endDate);

	List<SeatsBooked> getSeatsBookedByDate(LocalDateTime startDate, LocalDateTime endDate);

	byte[] generateSeatsBookedReportPDF(LocalDateTime startDate, LocalDateTime endDate) throws Exception;

	boolean checkIftheSeatIsCurrentlyBooked(int eId, LocalDateTime fromDateTime, LocalDateTime toDateTime);

	boolean checkIftheSeatIsCurrentlyBookedDaily(int eId, LocalDateTime fromDateTime);

	List<Map<String, Object>> GettingDetailsOfViwPass(int eid);

	byte[] generateSeatsBookedByEmployeeReportPDF(int empId, LocalDateTime startDate, LocalDateTime endDate)
			throws Exception;

	List<SeatsBooked> getSeatsBookedByShiftTimingBetweenDates(int stId, LocalDateTime startDate, LocalDateTime endDate);

	byte[] generateSeatsBookedByShiftReportPDF(int stId, LocalDateTime startDate, LocalDateTime endDate)
			throws Exception;

	List<Seat> getTopFivePopularSeats();

	String notificationAboutSeat(int eId);

	// List<Object[]> getTopFivePopularSeats();

//	boolean checkIftheSeatIsCurrentlyBooked(int eId, int sId, LocalDateTime fromDateTime, LocalDateTime toDateTime);
//
//
//	boolean checkIftheSeatIsCurrentlyBookedDaily(int eId, int sId, LocalDateTime fromDateTime);

	List<SeatsBooked> getSBBySTAndDate(int start, String date);

	List<Seat> findBookedSeatsByWeek(LocalDate fromDate, LocalDate toDate);

	List<Seat> findAvailableSeatsByWeek(LocalDate fromDate, LocalDate toDate);

	List<Seat> findBookedSeatsByDate(LocalDate date);

	int getSidBySname(String sname);

	// void updateNotifStatus(int sbId, Connection connection);

//	void bookSeat();

//	void bookSeat(SeatsBooked seatsBooked);

}