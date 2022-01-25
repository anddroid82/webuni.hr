package hu.webuni.hr.andro.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.dto.EmployeeDto;
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
		Company company = this.getCompany(companyId,true);
		if (company != null) {
			employee.setId(employee.getId() == null?0:employee.getId());
			Employee emp = employeeService.getEmployee(employee.getId());
			if (emp == null) {
				emp = employeeService.addEmployee(employee);
			}
//			TODO: ha már benne volt az Employee másik cég dolgozói között, akkor onnan a listából törölni kell ?
//		    ezzel egyáltalán kell foglalkozni, vagy nem, mert a repository mindig az aktuális állapotot adja vissza
//			if (emp.getCompany() != null) {
//				deleteEmployeeFromCompany(emp.getId(), emp.getCompany().getId());
//			}
			return company.addEmployee(emp);
		}
		return null;
	}
	
	@Transactional
	public Employee deleteEmployeeFromCompany(long employeeId,long companyId) {
		Company company = this.getCompany(companyId,true);
		if (company != null) {
			Employee employee = company.removeEmployee(employeeId);
//			Transactional miatt nem kell a modifyEmployee 
//			if (employee != null) {
//				employeeService.modifyEmployee(employee);
//			}
			return employee;
		}
		return null;
	}
	
	@Transactional
	public boolean changeEmployeeListOfCompany(List<Employee> employees, long companyId) {
		Company company = this.getCompany(companyId,true);
		if (company != null) {
			// összes alkalmazott törlése
			company.removeAllEmployee();
			for (Employee emp : employees) {
				emp.setId(emp.getId() == null?0:emp.getId());
				Employee temp = employeeService.getEmployee(emp.getId());
				if (temp == null) {
					temp = employeeService.addEmployee(emp);
				}
				company.addEmployee(temp);
			}
			return true;
		}
		return false;
	}
	
//	public boolean changeEmployeeListOfCompany(List<Employee> employees, long companyId) {
//		Company company = this.getCompany(companyId,false);
//		if (company != null) {
//			company.setEmployees(employees);
//			return true;
//		}
//		return false;
//	}
	
	public List<Company> findByEmployeePaymentGreaterThan(int payment) {
		return companyRepository.findByEmployeePaymentGreaterThan(payment);
	}
	
	public List<Company> findByEmployeeCountGreaterThan(long count) {
		return companyRepository.findByEmployeeCountGreaterThan(count);
	}
	
	public List<AvgPaymentOfCompany> getAvgPaymentByRankOfCompany(long companyId) {
		return companyRepository.getAvgPaymentByRankOfCompany(getCompany(companyId,false));
	}
	
	public Company addCompany(Company company) {
		company.setId(null);
		return companyRepository.save(company);
	}
	
	public Company modifyCompany(Company company) {
		if (companyRepository.existsById(company.getId())) {
			return companyRepository.save(company);
		}
		return null;
	}

	public Company getCompany(Long id,boolean full) {
		Optional<Company> comOpt = null;
		if (full) {
			comOpt = companyRepository.findCompanyByIdWithEmployees(id);
		}else {
			comOpt = companyRepository.findById(id);
		}
		if (comOpt.isPresent()) {
			return comOpt.get();
		}
		return null;
	}

	public Company deleteCompany(Company company) {
		return this.deleteCompany(company.getId());
	}

	public Company deleteCompany(Long id) {
		Optional<Company> comOpt = companyRepository.findById(id);
		if (comOpt.isPresent()) {
			companyRepository.deleteById(id);
			return comOpt.get();
		}
		return null;
	}

	public List<Company> getCompanies(boolean full) {
		if (full) {
			return companyRepository.findAllWithEmployees();
		}else {
			return companyRepository.findAll();
		}
	}

	
	
	
}
