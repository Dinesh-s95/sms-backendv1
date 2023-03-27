package com.valtech.poc.sms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.valtech.poc.sms.entities.Employee;

@Component
public class EmployeeDAOImpl implements EmployeeDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Employee> getAllEmployees() {
		String sql="select * from employee";
	    List<Employee> employees = jdbcTemplate.query(
	    		sql,new ResultSetExtractor<List<Employee>>() {
	    			public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException{
	    				List<Employee> list= new ArrayList<Employee>();
	    				while(rs.next()) {
	    					Employee employee=new Employee();
	    					employee.setEmpName(rs.getString("emp_name"));
			                employee.seteId(rs.getInt("e_id"));
			                employee.setPhNum(rs.getString("ph_num"));
			                employee.setMailId(rs.getString("mail_id"));
			                list.add(employee);
	    				}
	    				return list;
	    			}
	    			
	    		});
		return employees;
	}

	
	
	

}
