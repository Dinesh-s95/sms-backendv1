package com.valtech.poc.sms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.valtech.poc.sms.entities.AttendanceTable;
import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.Manager;

@Component
public class AdminDaoImpl implements AdminDao {
    
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int getFoodCount(LocalDateTime dateTime) {
		String query="select count from Food where ft_date=?";
		return jdbcTemplate.queryForObject(query, Integer.class, dateTime);
	}

	@Override
	public int getSeatBookedCount(LocalDateTime dateTime) {
		String query="select count(sb_start_date) from seats_booked where sb_start_date=?"; 
	    return jdbcTemplate.queryForObject(query, Integer.class, dateTime);
	}
	
	@Override
	public void approveAttendance(int atId) {
		String sql="UPDATE attendance_table SET approval=? WHERE at_id=?";
		jdbcTemplate.update(sql, 1 ,atId);
		
	}

	@Override
	public List<AttendanceTable> listAttendance() {
		String SQL = "select * from attendance_table";
    	List <AttendanceTable> att = 
    			jdbcTemplate.query( SQL, new ResultSetExtractor<List<AttendanceTable>>()
    	{
    		public List<AttendanceTable> extractData(ResultSet rs) throws SQLException, DataAccessException { 
    			List<AttendanceTable> list = new ArrayList<AttendanceTable>();
    			while(rs.next()){ AttendanceTable attendanceTable = new AttendanceTable();
    			attendanceTable.setAtId(rs.getInt("at_id"));
    			attendanceTable.setStartDate(rs.getString("start_date")); 
    			attendanceTable.setEndDate(rs.getString("end_date")); 
    			attendanceTable.setShiftStart(rs.getString("shift_start"));
    			attendanceTable.setShiftEnd(rs.getString("shift_end")); 
    			attendanceTable.setApproval(rs.getBoolean("approval"));
    			list.add(attendanceTable); 
    			} 
    			return list; 
    			} 
    		} 
    			);
    	return att; 
		
		
	}

	@Override
	public List<String> findRoles() {
		String query="select role from roles";
		return jdbcTemplate.queryForList(query, String.class);

	}

	@Override
	public AttendanceTable getList(int atId) {
		String query="select *from attendance_table a JOIN employee e ON a.e_id = e.e_id JOIN manager m ON e.m_id = m.m_id JOIN employee e1 ON m.e_id = e1.e_id 	WHERE a.at_id=?";
		Map<String, Object> result = jdbcTemplate.queryForMap(query, atId);
		 AttendanceTable attendance = new AttendanceTable();
		    attendance.setAtId((int) result.get("at_id"));
		    attendance.setStartDate((String) result.get("start_date"));
		    attendance.setEndDate((String) result.get("end_date"));
		    attendance.setShiftStart((String) result.get("shift_start"));
		    attendance.setShiftEnd((String) result.get("shift_end"));
		    attendance.setApproval((Boolean) result.get("approval"));
		    
		    Employee employee = new Employee();
		    employee.seteId((int) result.get("e_id"));
		    employee.setEmpName((String) result.get("emp_name"));
		    employee.setMailId((String) result.get("mail_id"));
		    employee.setPhNum((String) result.get("Ph_num"));
		    attendance.seteId(employee);
		    
		    
		    Manager manager = new Manager();
		    manager.setmId((int) result.get("m_id"));
		   int m=(int) result.get("m_id");
		   Employee employee1 = new Employee();
		   String query1="select * from employee e JOIN Manager m ON e.e_id = m.e_id where m.m_id=?";
		    		Map<String, Object> result1 = jdbcTemplate.queryForMap(query1, m);
		    	
		    employee1.seteId((int) result1.get("e_id"));
		    employee1.setEmpName((String) result1.get("emp_name"));
		    employee1.setMailId((String) result1.get("mail_id"));
		    employee1.setPhNum((String) result1.get("Ph_num"));
		    manager.setManagerDetails(employee1);
		    employee.setManagerDetails(manager);
		    
		    return attendance;
	}
	
}
