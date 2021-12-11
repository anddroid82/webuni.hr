package hu.webuni.hr.andro.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;

@Component
public class CompanyDtoRepository {

	private EmployeeDtoRepository employeeRepo;
	
	private List<CompanyDto> companies = new ArrayList<>();
	
	public void setDefaultValues() {
		CompanyDto c1=new CompanyDto("abc001", "Teszt Cég 01", "Teszt Cím 01");
		c1.addEmployee(employeeRepo.getEmployee(111L));
		c1.addEmployee(employeeRepo.getEmployee(222L));
		companies.add(c1);
		CompanyDto c2=new CompanyDto("abc002", "Teszt Cég 02", "Teszt Cím 02");
		c2.addEmployee(employeeRepo.getEmployee(333L));
		c2.addEmployee(employeeRepo.getEmployee(444L));
		companies.add(c2);
		CompanyDto c3=new CompanyDto("abc003", "Teszt Cég 03", "Teszt Cím 03");
		c3.addEmployee(employeeRepo.getEmployee(555L));
		c3.addEmployee(employeeRepo.getEmployee(666L));
		companies.add(c3);
	}
	
	
	public CompanyDtoRepository(@Autowired EmployeeDtoRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
		this.setDefaultValues();
	}

	public EmployeeDto addEmployeeToCompany(EmployeeDto employee, String companyId) {
		CompanyDto company = this.getCompany(companyId);
		if (company != null) {
			company.addEmployee(employee);
			return employee;
		}
		return null;
	}
	
	public EmployeeDto deleteEmployeeFromCompany(long employeeId,String companyId) {
		CompanyDto company = this.getCompany(companyId);
		if (company != null) {
			EmployeeDto employee = company.removeEmployee(employeeId);
			return employee;
		}
		return null;
	}
	
	public boolean changeEmployeeListOfCompany(List<EmployeeDto> employees, String companyId) {
		CompanyDto company = this.getCompany(companyId);
		if (company != null) {
			company.setEmployees(employees);
			return true;
		}
		return false;
	}
	
	public CompanyDto addCompany(CompanyDto company) {
		CompanyDto comp = this.getCompany(company.getId());
		if (comp == null) {
			companies.add(company);
			return company;
		}
		return null;
	}

	public CompanyDto getCompany(String id) {
		for (CompanyDto c : companies) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	public CompanyDto deleteCompany(CompanyDto company) {
		if (this.companies.contains(company)) {
			this.companies.remove(company);
			return company;
		}
		return null;
	}

	public CompanyDto deleteCompany(String id) {
		CompanyDto comp = this.getCompany(id);
		if (comp != null) {
			this.companies.remove(comp);
			return comp;
		}
		return null;
	}

	public List<CompanyDto> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyDto> companies) {
		this.companies = companies;
	}

	

}
