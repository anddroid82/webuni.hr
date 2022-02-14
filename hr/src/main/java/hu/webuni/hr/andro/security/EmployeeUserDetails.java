package hu.webuni.hr.andro.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.EmployeeRepository;

public class EmployeeUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Employee employee;
	private JwtDecodedUserData jwtDecodedUserData;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public EmployeeUserDetails(Employee employee) {
		this.employee = employee;
//		this.username = employee.getUsername();
//		this.password = employee.getPassword();
//		this.roles = employee.getRoles();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.employee.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.employee.getPassword(); 
	}

	@Override
	public String getUsername() {
		return this.employee.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public JwtDecodedUserData getJwtDecodedUserData() {
		return jwtDecodedUserData;
	}

	public void setJwtDecodedUserData(JwtDecodedUserData jwtDecodedUserData) {
		this.jwtDecodedUserData = jwtDecodedUserData;
	}
	
	
	
}
