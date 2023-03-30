package com.valtech.poc.sms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.valtech.poc.sms.entities.Employee;
import com.valtech.poc.sms.entities.EmployeeDto;
import com.valtech.poc.sms.entities.EmployeeRequest;
import com.valtech.poc.sms.entities.Manager;
import com.valtech.poc.sms.entities.User;
import com.valtech.poc.sms.security.JwtUtil;
import com.valtech.poc.sms.service.EmployeeService;
import com.valtech.poc.sms.service.ManagerService;
import com.valtech.poc.sms.service.RolesService;
import com.valtech.poc.sms.service.UserService;
import com.valtech.poc.sms.entities.Roles;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private RolesService rolesService;
	


	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//	@ResponseBody
//	@PostMapping("/save")
//	public String SaveEmployee(@RequestBody EmployeeRequest employeeRequest) {
//		Employee employee = employeeRequest.getEmployee();
//		User user = employeeRequest.getUser();
//		String managerName = employeeRequest.getManagerName();
//		String role = employeeRequest.getRole();
//		
//		Employee emp = new Employee(employee.geteId(), employee.getEmpName(), employee.getPhNum(), employee.getMailId(),
//				employee.getManagerDetails());
//		userService.saveEmployee(emp, null);
//		Manager manager = userService.getManagerByMname(managerName,emp);
//		emp.setManagerDetails(manager);
//		userService.saveEmployee(emp, manager);
//		User newUser = new User(user.getuId(), user.getEmpId(), user.getPass(), emp, false, null, null);
//		User u = userService.saveUser(newUser, emp);
//		if (u != null) {
//			if (role.equals("Manager")) {
//				Manager mng = new Manager(emp);
//				userService.saveManager(mng);
//			}
//			int rId = userService.getRidByRoleName(role);
//			int uId = newUser.getuId();
//			userService.saveUserRoles(uId, rId);
//			return "saved all data";
//		}
//		else {
//			userService.deleteEmployee(emp);
//			logger.info("User is not created");
//			return "user is not created";
//		}
//		
//
//	}

	@ResponseBody
	@GetMapping("/gettingAllManagernames")
	public List<String> getAllManagerNames(){
		return userService.getManagerNames();
	}
@Autowired
private JwtUtil jwtUtil;
    
    

	@PostMapping("/api/login")
	public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, String> request) {
		String empId = request.get("empId");
		String pass = request.get("pass");

		String token = userService.login(Integer.parseInt(empId), pass);
		User user=userService.findByEmpId(Integer.parseInt(empId));
		String role = user.getRoles().iterator().next().getRole();
		System.out.println(user.getEmpDetails().getMailId());
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		response.put("EId", String.valueOf(user.getEmpDetails().geteId()));
		response.put("role", role);

		return ResponseEntity.ok(response);
	}



	@GetMapping("/user")
	public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token) {
		int username = jwtUtil.extractEmpId(token);
		User user = userService.findByEmpId(username);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/my-protected-endpoint")
	public String myProtectedEndpoint() {
		// logic for your protected API endpoint here
		return "Hello, this is a protected endpoint!";
	}
	@RequestMapping("/welcome")
	public String welcome() {
		String text = "this is private page";
		text+="this page is not allowed to unauthenticated users";
		return text;
	}
//	@PutMapping("/{empId}")
//    public String updateUserApproval(@PathVariable("empId") int empId) {
//        User user = userService.findByEmpId(empId);
//        boolean approval=true;
//        if (user == null) {
//            return "User not found";
//        }
//        user.setApproval(approval);
//        userService.save(user);
//        return  "User approved successfully";
//    }
	
	@PostMapping("/saveuser")
    public ResponseEntity<String> saveUserEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
        	
        	if(userService.findByEmpId(employeeDto.getEmpId()) != null) {
                return ResponseEntity.badRequest().body("Employee with the given empId already exists");
            }
            // Create the Employee object from the DTO
            Employee employee = new Employee();
            employee.setEmpName(employeeDto.getEmpName());
            employee.setPhNum(employeeDto.getPhNum());
            employee.setMailId(employeeDto.getMailId());

            // Saving the employee to the database
            employee = employeeService.saveEmployee(employee);

         // Creating the Manager object from the DTO
            Manager manager = new Manager();
            manager.setManagerDetails(employee);

            // Saving the manager to the database
            manager = managerService.saveManager(manager);


            // Associate the manager with the employee
            employee.setManagerDetails(manager);

            // Saving the updated employee to the database
            employee = employeeService.saveEmployee(employee);

            // Create the User object from the DTO
            User user = new User();
            user.setEmpId(employeeDto.getEmpId());
            String encodedPassword = new BCryptPasswordEncoder().encode(employeeDto.getPass());
            user.setPass(encodedPassword);
            user.setEmpDetails(employee);

            // Save the user to the database
            user = userService.save(user);

            // Fetch the role from the database based on the role name
            Roles role = rolesService.findByRole(employeeDto.getRole());

            // Add the role to the user's roles set
            user.getRoles().add(role);

            // Save the updated user to the database
            user = userService.save(user);

            // Return the success response
            return ResponseEntity.ok("Employee saved successfully");
        }catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving employee: " + e.getMessage());
		}
	}

	


}
