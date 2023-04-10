package com.valtech.poc.sms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valtech.poc.sms.component.ScheduledTask;
import com.valtech.poc.sms.dao.SeatBookingDao;
import com.valtech.poc.sms.entities.CalendarUtil;
import com.valtech.poc.sms.entities.DateUtil;
import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.Seat;
import com.valtech.poc.sms.entities.SeatsBooked;
import com.valtech.poc.sms.entities.ShiftTimings;
import com.valtech.poc.sms.repo.EmployeeRepo;
import com.valtech.poc.sms.repo.SeatRepo;
import com.valtech.poc.sms.repo.SeatsBookedRepo;
import com.valtech.poc.sms.repo.ShiftTimingsRepo;

@Service
public class SeatBookingServiceImpl implements SeatBookingService {

	@Autowired
	private SeatBookingDao seatBookingDao;

	@Autowired
	SeatsBookedRepo seatsBookedRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	ShiftTimingsRepo shiftTimingsRepo;

	@Autowired
	AdminService adminService;

	@Autowired
	SeatRepo seatRepo;

	@Autowired
	ScheduledTask scheduledTask;

	@Autowired
	private HolidayService holidayService;

	@Autowired
	MailContent mailContent;

//	@Override
//	public String getQrCodeKeyForEmpId(int empId)

	@Override
	public List<SeatsBooked> getSBBySTAndDate(int start, String date) {
		List<SeatsBooked> sbtemp = seatBookingDao.findSBIdByShiftTimingsAndDate(start, date);
//		List<SeatsBooked> sb = new ArrayList<>();
//		for (SeatsBooked seatsBooked : sbtemp) {
//			SeatsBooked seatsBookedi =  seatsBookedRepo.findById(seatsBooked.getSbId()).get();
//			sb.add(seatsBookedi);
//		}
		return sbtemp;
	}

	/* Fetching the total seats */
	@Override
	public List<Integer> getAllSeats() {
		return seatBookingDao.getAllSeats();
	}

	/* Fetching the available seats */
	@Override
	public List<String> availableSeats() {
		return seatBookingDao.availableSeats();
	}

	@Override
	public List<Seat> findAvailableSeats() {
		// TODO Auto-generated method stub
		return null;
	}

	/* Get the total count of seats */
	@Override
	public List<Integer> countTotalSeats() {
		return seatBookingDao.countTotalSeats();
	}

//	@Override
//	public List<SeatsBooked> findEmployeeWiseSeatsBooked(Employee emp)	{
//		return seatBookingDao.findAllByEId(emp);
//	}

	/* Find the current seat booking details */
	@Override
	public SeatsBooked findCurrentSeatBookingDetails(Employee emp) {
		// Get the list of all seat bookings of the employee.
		List<SeatsBooked> sb = seatBookingDao.findCurrentSeat(emp);

		// Sort the seat bookings in ascending order of the booking date.
		Collections.sort(sb, new Comparator<SeatsBooked>() {
			@Override
			public int compare(SeatsBooked o1, SeatsBooked o2) {
				return o1.getSbDate().compareTo(o2.getSbDate());
			}
		});

		// Get the latest seat booking of the employee.
		SeatsBooked latestSb = sb.get(0);

		// Return the latest seat booking.
		return latestSb;

	}

	@Override
	public List<SeatsBooked> findAllByEId(Employee emp) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Fetching the available seats by date */
	@Override
	public List<Seat> findAvailableSeatsByDate(LocalDate date) {
		return seatBookingDao.findAvailableSeatsByDate(date);
	}
	
	@Override
	public List<Seat> findBookedSeatsByDate(LocalDate date) {
		return seatBookingDao.findBookedSeatsByDate(date);
	}

	/* Save the booked seat details */
	@Override
	public SeatsBooked saveSeatsBookedDetails(SeatsBooked seatsBooked) {
		return seatsBookedRepo.save(seatsBooked);
	}

	/* notify the status */
	@Override
	public void notifStatus(int sbId) {
		seatBookingDao.notifStatus(sbId);
	}

	@Override
	public void bookSeat(SeatsBooked seatsBooked) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean CheckIfTheSameSeatBookingRecurring(int eId) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean canEmployeeBookSeat(int eId,int sId, LocalDateTime fromDateTime) throws ServiceException {
//	    try {
//	        return seatBookingDao.canEmployeeBookSeat(eId, sId,fromDateTime);
//	    } catch (DataAccessException e) {
//	        throw new ServiceException("Error checking if employee can book seat", e);
//	    }
//	}

	@Override
	public Seat getSeatById(int sId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Fetching the booked seat info using emplyeeid and date */
	@Override
	public List<SeatsBooked> getSeatsBookedByEmployeeAndDate(int empId, LocalDateTime startDate,
			LocalDateTime endDate) {
		return seatBookingDao.getSeatsBookedByEmployeeAndDate(empId, startDate, endDate);
	}

	/* Fetching the booked seat info using start date and end date */
	@Override
	public List<SeatsBooked> getSeatsBookedByDate(LocalDateTime startDate, LocalDateTime endDate) {
		return seatBookingDao.getSeatsBookedByDate(startDate, endDate);
	}

	/* Fetching the booked seat info using shift timings */
	@Override
	public List<SeatsBooked> getSeatsBookedByShiftTimingBetweenDates(int stId, LocalDateTime startDate,
			LocalDateTime endDate) {
		return seatBookingDao.getSeatsBookedByShiftTimingBetweenDates(stId, startDate, endDate);
	}

	/* Verify the seat is currently booked */
	@Override
	public boolean checkIftheSeatIsCurrentlyBooked(int eId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		// TODO Auto-generated method stub
		return seatBookingDao.checkIfEmployeeAlreadyBookTheSeat(eId, fromDateTime, toDateTime);
	}

	/* Verify whether the seat is currently booked */
	@Override
	public boolean checkIftheSeatIsCurrentlyBookedDaily(int eId, LocalDateTime fromDateTime) {
		// TODO Auto-generated method stub
		return seatBookingDao.checkIfEmployeeAlreadyBookTheSeatDaily(eId, fromDateTime);
	}

	

	/*Saving the booked seats details daily*/
	@Override
	public String createSeatsBookedDaily(int eId, int sId, int stId, String from, String to,Boolean food) {
		Employee emp = employeeRepo.findById(eId).get();
		Seat seat = seatRepo.findById(sId).get();
		ShiftTimings st = shiftTimingsRepo.findById(stId).get();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
		LocalDate fromDate = fromDateTime.toLocalDate();

		// check if the seat is already booked
		if (checkIftheSeatIsCurrentlyBookedDaily(eId, fromDateTime)) {
			return "This employee has already booked the seat.";
		} else {
			if (CalendarUtil.isDateDisabled(fromDate)) {
				System.out.println("The date falls on sunday or saturday");
			} else if (holidayService.isHoliday(fromDate)) {
				System.out.println("Seat Booking not allowed on holidays");
			} else {
				String code = adminService.generateQrCode(eId);
				LocalDateTime localDateTime = fromDate.atStartOfDay();
				// LocalDateTime dateTime = LocalDateTime.parse(formatter.format(localDateTime),
				// formatter);
//				long limit = 60000 * 240;
				// check for recurring seats
//			if (CheckIfTheSameSeatBookingRecurring(eId)) {
//				Seat recSeat = getSeatById(sId);
//				SeatsBooked sb = new SeatsBooked(localDateTime, null, null, true, code, recSeat, emp, false, false,false,st);
//				SeatsBooked savedSeatsBooked = saveSeatsBookedDetails(sb);
//				scheduledTask.scheduleTask(limit, savedSeatsBooked);
//				//mailContent.dailyNotification(emp);
//				return "The Same Seat is booked successfully because you are selecting this seat more than 3 times with ID: "
//						+ savedSeatsBooked.getSbId();
//			} else {
				SeatsBooked sb = new SeatsBooked(localDateTime, null, null, true, code, seat, emp, false, false, food,
						st);
				SeatsBooked savedSeatsBooked = saveSeatsBookedDetails(sb);
				if(sb.isFood()==true) {
					seatBookingDao.updatFoodCount(sb.getSbDate());
				}
//				scheduledTask.scheduleTask(limit, savedSeatsBooked);
//				mailContent.dailyNotification(emp);
				// check if employee is booking a seat again on the same day
//				if (canEmployeeBookSeat(eId, sId,fromDateTime)) {
//			        return ResponseEntity.ok("This employee has already booked a seat today. Please try again tomorrow.";
//			    }
				return "Seats booked created successfully with ID: " + savedSeatsBooked.getSbId();
				// }
			}
			return "test";

		}

	}
	
	/*Saving the booked seats details for custom dates*/
	@Override
	public String createSeatsBookedWeekly(int eId, int sId, int stId, String from, String to,Boolean food) {
	    Employee emp = employeeRepo.findById(eId).get();
	    Seat seat = seatRepo.findById(sId).get();
	    ShiftTimings st = shiftTimingsRepo.findById(stId).get();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
	    LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
	    if (checkIftheSeatIsCurrentlyBooked(eId,  fromDateTime, toDateTime)) {
	        return "This employee has already booked.";
	    } else {
	        LocalDate fromDate = fromDateTime.toLocalDate();
	        LocalDate toDate = toDateTime.toLocalDate();
	        List<LocalDate> dates = DateUtil.getDatesBetween(fromDate, toDate);
	        
	        if (dates.size() > 7) {
	            return "Cannot book seats for more than 7 days";
	        }
	        else {
	        
	        for (LocalDate date : dates) {
	            if (CalendarUtil.isDateDisabled(date)) {
	                System.out.println("The date falls on sunday or saturday");
	            } else if (holidayService.isHoliday(date)) {
	                System.out.println("Seat Booking not allowed on holidays");
	            } else {
	                LocalDateTime localDateTime = date.atStartOfDay();
	                String code = adminService.generateQrCode(eId);
	                SeatsBooked sb = new SeatsBooked(localDateTime, null, null, true, code, seat, emp, false, false,
	                        food, st);
	                seatsBookedRepo.save(sb);
	                if (sb.isFood()) {
	                    seatBookingDao.updatFoodCount(sb.getSbDate());
	                }
	            }
	        }
	        return "test ";
	    }
	    }
	}


	/* Generating the seatsbooked report in the pdf format */
	@Override
	public byte[] generateSeatsBookedReportPDF(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
		List<SeatsBooked> seatsBooked = getSeatsBookedByDate(startDate, endDate);

		byte[] pdf = seatBookingDao.generateSeatsBookedPDF(seatsBooked);
		return pdf;
	}


	
	 @Override
	   public List<Seat> getTopFivePopularSeats() {
	        return seatBookingDao.getTopFivePopularSeats();
	    }
	 
	  @Override
	 public List<Seat> findBookedSeatsByWeek(LocalDate fromDate, LocalDate toDate) {
	     return seatBookingDao.findBookedSeatsByWeek(fromDate, toDate);
	 }

	@Override
	 public List<Seat> findAvailableSeatsByWeek(LocalDate fromDate, LocalDate toDate) {
	     return seatBookingDao.findAvailableSeatsByWeek(fromDate, toDate);
	 }

	/* Generating the employee report in the pdf format */
	@Override
	public byte[] generateSeatsBookedByEmployeeReportPDF(int empId, LocalDateTime startDate, LocalDateTime endDate)
			throws Exception {
		List<SeatsBooked> seatsBooked = getSeatsBookedByEmployeeAndDate(empId, startDate, endDate);

		byte[] pdf = seatBookingDao.generateSeatsBookedPDF(seatsBooked);
		return pdf;
	}

	/* Generating the seatsbooked by shift report in the pdf format */
	@Override
	public byte[] generateSeatsBookedByShiftReportPDF(int stId, LocalDateTime startDate, LocalDateTime endDate)
			throws Exception {
		List<SeatsBooked> seatsBooked = getSeatsBookedByShiftTimingBetweenDates(stId, startDate, endDate);

		byte[] pdf = seatBookingDao.generateSeatsBookedPDF(seatsBooked);
		return pdf;
	}

	/* Fetching the details of view pass */
	@Override
	public List<Map<String, Object>> GettingDetailsOfViwPass(int eid) {
		// TODO Auto-generated method stub
		return seatBookingDao.GettingDetailsOfViwPass(eid);
	}

	/* Listing the top 5 popular seats */

	/* Providing seat details to the employee */
	@Override
	public String notificationAboutSeat(int eId) {
		Employee emp = employeeRepo.findById(eId).get();
		SeatsBooked sb = findCurrentSeatBookingDetails(emp);
		String notification = "Hello, you have a booked seat for " + sb.getSbDate().toLocalDate()
				+ " and the seat number is " + sb.getsId().getsName() + " for " + sb.getSt().getStStart() + " - "
				+ sb.getSt().getStEnd() + " shift";
		return notification;
	}

	@Override
	public int getSidBySname(String sname) {
		// TODO Auto-generated method stub
		return seatBookingDao.findIdBysName(sname);
	}


//    @Override
//    public List<Object[]> getTopFivePopularSeats() {
//        return seatRepo.findTopFivePopularSeats();
//
//}

}
