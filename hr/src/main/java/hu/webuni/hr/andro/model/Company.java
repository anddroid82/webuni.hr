package hu.webuni.hr.andro.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Company {

	private String id;
	private String name;
	private String address;
	
	private List<Employee> employees;

	public Company(String id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.employees = new ArrayList<>();
	}

	public Employee addEmployee(Employee employee) {
		Employee emp = this.getEmployee(employee.getId());
		if (emp == null) {
			//employee.setCompany(this);
			this.employees.add(employee);
			return employee;
		}
		return null;
	}

	public Employee getEmployee(long id) {
		for (Employee e : employees) {
			if (e.getId() == id)
				return e;
		}
		return null;
	}

	public Employee removeEmployee(long id) {
		Employee emp = this.getEmployee(id);
		if (emp != null) {
			//emp.setCompany(null);
			this.employees.remove(emp);
			return emp;
		}
		return null;
	}
	
	public void removeAllEmployee() {
		ListIterator<Employee> iterator = this.employees.listIterator();
		while (iterator.hasNext()) {
			//Employee emp = iterator.next();
			//emp.setCompany(null);
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

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Id: " + this.id + " - " + this.name + " (" + this.address + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Company) {
			return this.id.equals(((Company) obj).id);
		}
		return false;
	}

}
