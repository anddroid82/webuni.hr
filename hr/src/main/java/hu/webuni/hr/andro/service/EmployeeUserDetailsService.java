package hu.webuni.hr.andro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.EmployeeUserDetails;
import hu.webuni.hr.andro.repository.EmployeeRepository;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
		return new EmployeeUserDetails(employee);
	}
	
}
