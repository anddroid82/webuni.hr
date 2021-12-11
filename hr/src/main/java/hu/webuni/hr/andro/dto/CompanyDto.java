package hu.webuni.hr.andro.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonFilter("CompanyFilterExcludeEmployees")
public class CompanyDto {

	private String id;
	private String name;
	private String address;
	
	@JsonManagedReference
	private List<EmployeeDto> employees = new ArrayList<>();

	public CompanyDto(String id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public EmployeeDto addEmployee(EmployeeDto employee) {
		EmployeeDto emp = this.getEmployee(employee.getId());
		if (emp == null) {
			this.employees.add(employee);
			employee.setCompany(this);
			return employee;
		}
		return null;
	}

	public EmployeeDto getEmployee(long id) {
		for (EmployeeDto e : employees) {
			if (e.getId() == id)
				return e;
		}
		return null;
	}

	public EmployeeDto removeEmployee(long id) {
		EmployeeDto emp = this.getEmployee(id);
		if (emp != null) {
			this.employees.remove(emp);
			emp.setCompany(null);
			return emp;
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Id: " + this.id + " - " + this.name + " (" + this.address + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CompanyDto) {
			return this.id.equals(((CompanyDto) obj).id);
		}
		return false;
	}

}
