package hu.webuni.hr.andro.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.Position;
import hu.webuni.hr.andro.repository.CompanyRepository;
import hu.webuni.hr.andro.repository.PositionRepository;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyServiceIT {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	InitDbService initDbService;
	
	@Test
	@Transactional
	void testAddEmployeeByIdToCompany() throws Exception {
		//9-es employee-t a 21-es céghez rakjuk
		long empId = 9;
		long compId = 21;
		Employee employee = employeeService.getEmployee(empId);
		companyService.addEmployeeToCompany(employee, compId);
		Company company = companyRepository.findCompanyByIdWithEmployees(compId).get();
		assertThat(company.getEmployees()).contains(employee);
		assertThat(employee.getCompany()).isEqualTo(company);
	}
	
	@Test
	@Transactional
	void testAddEmployeeToCompany() throws Exception {
		//új employee-t a 21-es céghez rakjuk
		Position p = positionRepository.findById(3L).get();
		Employee employee = new Employee(0L, "Kiss János", p, 400000, LocalDateTime.now(), null);
		employee = employeeService.addEmployee(employee);
		long compId = 21;
		companyService.addEmployeeToCompany(employee, compId);
		Company company = companyRepository.findCompanyByIdWithEmployees(compId).get();
		assertThat(company.getEmployees()).contains(employee);
		assertThat(employee.getCompany()).isEqualTo(company);
	}
	
	@Test
	@Transactional
	void testDeleteEmployeeByIdFromCompany() throws Exception {
		//9-es employee-t a 21-es cégből töröljük
		long empId = 9;
		long compId = 21;
		Employee employee = employeeService.getEmployee(empId);
		companyService.deleteEmployeeFromCompany(empId, compId);
		Company company = companyRepository.findCompanyByIdWithEmployees(compId).get();
		assertThat(company.getEmployees()).doesNotContain(employee);
		assertThat(employee.getCompany()).isEqualTo(null);
	}
	
	@Test
	@Transactional
	void testChangeEmployeeListOfCompany() throws Exception {
		//a 19-es és a 21-es cég dolgozóit felcseréljük
		long compIdFrom = 19;
		long compIdTo = 21;
		List<Employee> emp19 = companyService.getCompany(compIdFrom, true).getEmployees();
		List<Employee> emp21 = companyService.getCompany(compIdFrom, true).getEmployees();
		
		companyService.changeEmployeeListOfCompany(emp19, compIdTo);
		List<Employee> emp21New = companyRepository.findCompanyByIdWithEmployees(compIdTo).get().getEmployees();
		assertThat(emp19).containsAll(emp21New);
		
		companyService.changeEmployeeListOfCompany(emp21, compIdFrom);
		List<Employee> emp19New = companyRepository.findCompanyByIdWithEmployees(compIdFrom).get().getEmployees();
		assertThat(emp21).containsAll(emp19New);
	}
	
	
	
}
