package hu.webuni.hr.andro.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.mapper.CompanyMapper;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.service.CompanyService;
import hu.webuni.hr.andro.service.EmployeeService;

@RestController
@RequestMapping("/api/companies")
public class HrCompanyRestController {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	CompanyMapper companyMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;

	/*
	private MappingJacksonValue getMappingJSON(Object object, boolean full) {
		List<String> fields = new ArrayList<>(Arrays.asList("id", "name", "address"));
		if (full) {
			fields.add("employees");
		}
		HashSet<String> properties = new HashSet<>(fields);
		SimpleBeanPropertyFilter propFilter = SimpleBeanPropertyFilter.filterOutAllExcept(properties);
		FilterProvider filter = new SimpleFilterProvider().addFilter("CompanyFilterExcludeEmployees", propFilter);
		MappingJacksonValue mapping = new MappingJacksonValue(object);
		mapping.setFilters(filter);
		return mapping;
	}*/
	
	@GetMapping
	public ResponseEntity<List<CompanyDto>> getAll(@RequestParam(required = false) boolean full) {
		List<CompanyDto> companies = null;
		if (full) {
			companies = new ArrayList<>(companyMapper.companiesToCompanyDtos(companyService.getCompanies()));
		}else {
			companies = new ArrayList<>(companyMapper.companiesToCompanyDtosWithoutEmployees(companyService.getCompanies()));
		}
		return ResponseEntity.ok(companies);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable String id, @RequestParam(required = false) boolean full) {
		Company c = companyService.getCompany(id);
		if (c == null) {
			return ResponseEntity.notFound().build();
		}else {
			if (full) {
				return ResponseEntity.ok(companyMapper.companyToCompanyDto(companyService.getCompany(id)));
			}else {
				return ResponseEntity.ok(companyMapper.companyToCompanyDtoWithoutEmployees(companyService.getCompany(id))); 
			}
		}
	}

	@PostMapping
	public ResponseEntity<CompanyDto> createCompany(@RequestBody Company company) {
		Company c = companyService.getCompany(company.getId());
		if (c == null) {
			companyService.addCompany(company);
			return ResponseEntity.ok(companyMapper.companyToCompanyDto(company));
		}else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> modifyCompany(@PathVariable String id, @RequestBody Company company) {
		Company comp = companyService.getCompany(id);
		if (comp != null) {
			//companyService.deleteCompany(comp);
			comp.setName(company.getName());
			comp.setAddress(company.getAddress());
			return ResponseEntity.ok(companyMapper.companyToCompanyDto(company));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDto> deleteCompany(@PathVariable String id) {
		Company company = companyService.getCompany(id);
		if (company == null) {
			return ResponseEntity.notFound().build();
		} else {
			companyService.deleteCompany(company);
			return ResponseEntity.ok(companyMapper.companyToCompanyDto(company));
		}
	}
	
	@PostMapping("/{companyId}/addEmployee")
	public ResponseEntity<EmployeeDto> addEmployeeToCompany(@RequestBody Employee employee,@PathVariable String companyId) {
		Employee emp = employeeService.getEmployee(employee.getId());
		Company comp = companyService.getCompany(companyId);
		if (emp == null) {
			employeeService.addEmployee(employee);
			//hozzáadjuk a company-hoz, ha volt ilyen
			if (comp != null) {
				return ResponseEntity.ok(employeeMapper.employeeToDto(comp.addEmployee(employee)));
			}
		}else {
			return ResponseEntity.ok(employeeMapper.employeeToDto(comp.addEmployee(emp))); 
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{companyId}/deleteEmployee/{employeeId}")
	public ResponseEntity<EmployeeDto> deleteEmployeeFromCompany(@PathVariable String companyId, @PathVariable long employeeId) {
		Company comp = companyService.getCompany(companyId);
		if (comp != null) {
			Employee emp = comp.removeEmployee(employeeId);
			return ResponseEntity.ok(employeeMapper.employeeToDto(emp));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/{companyId}/changeEmployees")
	public boolean changeEmployeeList(@RequestBody List<Employee> employees, @PathVariable String companyId) {
		Company company = this.companyService.getCompany(companyId);
		if (company != null) {
			company.removeAllEmployee();
			for (Employee emp : employees) {
				Employee temp = employeeService.getEmployee(emp.getId());
				if (temp == null) {
					employeeService.addEmployee(emp);
					temp=emp;
				}
				company.addEmployee(temp);
			}
			return true;
		}
		return false;
	}
	
	

}
