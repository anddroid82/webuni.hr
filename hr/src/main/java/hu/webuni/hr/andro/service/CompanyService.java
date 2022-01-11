package hu.webuni.hr.andro.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	CompanyRepository companyRepository;

	/*
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
	*/
	
	@Transactional
	public Company addCompany(Company company) {
		company.setId(null);
		return companyRepository.save(company);
	}
	
	@Transactional
	public Company modifyCompany(Company company) {
		if (companyRepository.existsById(company.getId())) {
			return companyRepository.save(company);
		}
		return null;
	}

	public Company getCompany(Long id) {
		if (companyRepository.existsById(id)) {
			return companyRepository.getById(id);
		}
		return null;
	}

	public Company deleteCompany(Company company) {
		return this.deleteCompany(company.getId());
	}

	@Transactional
	public Company deleteCompany(Long id) {
		if (companyRepository.existsById(id)) {
			Company c = companyRepository.getById(id);
			companyRepository.deleteById(id);
			return c;
		}
		return null;
	}

	public List<Company> getCompanies() {
		return companyRepository.findAll();
	}

	
}
