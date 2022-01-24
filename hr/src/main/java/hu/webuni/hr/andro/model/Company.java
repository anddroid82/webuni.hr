package hu.webuni.hr.andro.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

@Entity
@NamedEntityGraph(name = "Company.full", attributeNodes = {
		@NamedAttributeNode(value = "employees", subgraph = "Employee.full"),
		@NamedAttributeNode("companyType") }, subgraphs = {
				@NamedSubgraph(name = "Employee.full", attributeNodes = { @NamedAttributeNode("position") }) })
public class Company {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String address;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Employee> employees;

	@ManyToOne
	@JoinColumn(name = "companytype_id")
	private CompanyType companyType;

	public Company() {
	}

	public Company(Long id, String name, String address, CompanyType companyType) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.employees = new ArrayList<>();
		this.companyType = companyType;
	}

	public Employee addEmployee(Employee employee) {
		Employee emp = this.getEmployee(employee.getId());
		if (emp == null) {
			this.employees.add(employee);
			employee.setCompany(this);
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
			emp.setCompany(null);
			this.employees.remove(emp);
			return emp;
		}
		return null;
	}

	/*
	 * public void removeAllEmployee() { ListIterator<Employee> iterator =
	 * this.employees.listIterator(); while (iterator.hasNext()) { Employee emp =
	 * iterator.next(); emp.setCompany(null); iterator.remove(); } }
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Id: " + this.id + " - " + this.name + " (" + this.address + ") - " + this.companyType.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Company) {
			return this.id.equals(((Company) obj).id);
		}
		return false;
	}

}
