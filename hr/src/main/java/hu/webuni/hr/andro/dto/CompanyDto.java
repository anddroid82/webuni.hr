package hu.webuni.hr.andro.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonFilter("CompanyFilterExcludeEmployees")
public class CompanyDto {

	private String id;
	private String name;
	private String address;
	
	//@JsonManagedReference
	private List<EmployeeDto> employees;

	public CompanyDto(String id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.employees = new ArrayList<>();
	}

	public EmployeeDto addEmployee(EmployeeDto employee) {
		EmployeeDto emp = this.getEmployee(employee.getId());
		if (emp == null) {
			employee.setCompany(this);
			this.employees.add(employee);
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
			emp.setCompany(null);
			this.employees.remove(emp);
			return emp;
		}
		return null;
	}
	
	public void removeAllEmployee() {
		ListIterator<EmployeeDto> iterator = this.employees.listIterator();
		while (iterator.hasNext()) {
			EmployeeDto emp = iterator.next();
			emp.setCompany(null);
			iterator.remove();
		}
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
