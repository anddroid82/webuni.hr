package hu.webuni.hr.andro.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hu.webuni.hr.andro.model.Employee;

public class EmployeeUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Employee employee;
	private JwtDecodedUserData jwtDecodedUserData;
//	private String username;
//	private String password;
//	private Set<String> roles;
	
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
	
	
	
}
