package hu.webuni.hr.andro.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.AvgPaymentOfCompany;
import hu.webuni.hr.andro.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Transactional
	public Employee addEmployeeToCompany(Employee employee, long companyId) {
		Company company = this.getCompany(companyId);
		if (company != null) {
			Employee emp = employeeService.getEmployee(employee.getId());
			if (emp == null) {
				emp = employeeService.addEmployee(employee);
			}
			//TODO: ha már benne volt az Employee másik cég dolgozói között, akkor onnan a listából törölni kell ? 
			emp = company.addEmployee(emp);
			return employeeService.modifyEmployee(emp);
		}
		return null;
	}
	
	@Transactional
	public Employee deleteEmployeeFromCompany(long employeeId,long companyId) {
		Company company = this.getCompany(companyId);
		if (company != null) {
			Employee employee = company.removeEmployee(employeeId);
			if (employee != null) {
				employeeService.modifyEmployee(employee);
			}
			return employee;
		}
		return null;
	}
	
	public List<Company> findByEmployeePaymentGreaterThan(int payment) {
		return companyRepository.findByEmployeePaymentGreaterThan(payment);
	}
	
	public List<Company> findByEmployeeCountGreaterThan(long count) {
		return companyRepository.findByEmployeeCountGreaterThan(count);
	}
	
	public List<AvgPaymentOfCompany> getAvgPaymentByRankOfCompany(long companyId) {
		return companyRepository.getAvgPaymentByRankOfCompany(getCompany(companyId));
	}
	
	/*public boolean changeEmployeeListOfCompany(List<Employee> employees, long companyId) {
		Company company = this.getCompany(companyId);
		if (company != null) {
			company.setEmployees(employees);
			return true;
		}
		return false;
	}*/
	
	
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
