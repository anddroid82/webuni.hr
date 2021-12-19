package hu.webuni.hr.andro.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;

@Service
public class CompanyService {
	
	@Autowired
	EmployeeService employeeService;
	
	private List<Company> companies;
	
	public void setDefaultValues() {
		Company c1=new Company("abc001", "Teszt Cég 01", "Teszt Cím 01");
		c1.addEmployee(employeeService.getEmployee(111L));
		c1.addEmployee(employeeService.getEmployee(222L));
		companies.add(c1);
		Company c2=new Company("abc002", "Teszt Cég 02", "Teszt Cím 02");
		c2.addEmployee(employeeService.getEmployee(333L));
		c2.addEmployee(employeeService.getEmployee(444L));
		companies.add(c2);
		Company c3=new Company("abc003", "Teszt Cég 03", "Teszt Cím 03");
		c3.addEmployee(employeeService.getEmployee(555L));
		c3.addEmployee(employeeService.getEmployee(666L));
		companies.add(c3);
	}
	
	
	public CompanyService(@Autowired EmployeeService employeeService) {
		this.employeeService = employeeService;
		this.companies = new ArrayList<>();
		this.setDefaultValues();
	}

	public Employee addEmployeeToCompany(Employee employee, String companyId) {
		Company company = this.getCompany(companyId);
		if (company != null) {
			company.addEmployee(employee);
			return employee;
		}
		return null;
	}
	
	public Employee deleteEmployeeFromCompany(long employeeId,String companyId) {
		Company company = this.getCompany(companyId);
		if (company != null) {
			Employee employee = company.removeEmployee(employeeId);
			return employee;
		}
		return null;
	}
	
	public boolean changeEmployeeListOfCompany(List<Employee> employees, String companyId) {
		Company company = this.getCompany(companyId);
		if (company != null) {
			company.setEmployees(employees);
			return true;
		}
		return false;
	}
	
	public Company addCompany(Company company) {
		Company comp = this.getCompany(company.getId());
		if (comp == null) {
			companies.add(company);
			return company;
		}
		return null;
	}

	public Company getCompany(String id) {
		for (Company c : companies) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	public Company deleteCompany(Company company) {
		if (this.companies.contains(company)) {
			this.companies.remove(company);
			return company;
		}
		return null;
	}

	public Company deleteCompany(String id) {
		Company comp = this.getCompany(id);
		if (comp != null) {
			this.companies.remove(comp);
			return comp;
		}
		return null;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
}
