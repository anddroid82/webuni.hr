package hu.webuni.hr.andro.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.repository.CompanyDtoRepository;
import hu.webuni.hr.andro.repository.EmployeeDtoRepository;

@RestController
@RequestMapping("/api/companies")
public class HrCompanyRestController {

	@Autowired
	CompanyDtoRepository companyRepo;
	
	@Autowired
	EmployeeDtoRepository employeeRepo;

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
	}

	@GetMapping
	public MappingJacksonValue getAll(@RequestParam(required = false) boolean full) {
		List<CompanyDto> companies = new ArrayList<>(companyRepo.getCompanies());
		return this.getMappingJSON(companies, full);
	}

	@GetMapping("/{id}")
	public MappingJacksonValue getCompany(@PathVariable String id, @RequestParam(required = false) boolean full) {
		CompanyDto companyDto = companyRepo.getCompany(id);
		return this.getMappingJSON(companyDto, full);

	}

	@PostMapping
	public CompanyDto createCompany(@RequestBody CompanyDto company) {
		companyRepo.addCompany(company);
		return company;
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> modifyCompany(@PathVariable String id, @RequestBody CompanyDto company) {
		CompanyDto comp = companyRepo.getCompany(id);
		if (comp != null) {
			companyRepo.deleteCompany(comp);
			company.setId(id);
			companyRepo.addCompany(company);
			return ResponseEntity.ok(company);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDto> deleteCompany(@PathVariable String id) {
		CompanyDto company = companyRepo.getCompany(id);
		if (company == null) {
			return ResponseEntity.notFound().build();
		} else {
			companyRepo.deleteCompany(company);
			return ResponseEntity.ok(company);
		}
	}
	
	@PostMapping("/{companyId}/addEmployee")
	public EmployeeDto addEmployeeToCompany(@RequestBody EmployeeDto employee,@PathVariable String companyId) {
		EmployeeDto emp = employeeRepo.getEmployee(employee.getId());
		CompanyDto comp = companyRepo.getCompany(companyId);
		if (emp == null) {
			//ha még nem volt ilyen id-vel alkalmazott, akkor az employeeRepo-ba is felvesszük
			employeeRepo.addEmployee(employee);
			//hozzáadjuk a company-hoz, ha volt ilyen
			if (comp != null) {
				return comp.addEmployee(employee);
			}
		}else {
			return comp.addEmployee(emp); 
		}
		return null;
	}
	
	@GetMapping("/{companyId}/deleteEmployee/{employeeId}")
	public EmployeeDto deleteEmployeeFromCompany(@PathVariable String companyId, @PathVariable long employeeId) {
		CompanyDto comp = companyRepo.getCompany(companyId);
		if (comp != null) {
			EmployeeDto emp = comp.removeEmployee(employeeId);
			return emp;
		}
		return null;
	}
	
	@PostMapping("/{companyId}/changeEmployees")
	public boolean changeEmployeeList(@RequestBody List<EmployeeDto> employees, @PathVariable String companyId) {
		CompanyDto company = this.companyRepo.getCompany(companyId);
		if (company != null) {
			company.removeAllEmployee();
			for (EmployeeDto emp : employees) {
				EmployeeDto temp = employeeRepo.getEmployee(emp.getId());
				if (temp == null) {
					employeeRepo.addEmployee(emp);
					temp=emp;
				}
				company.addEmployee(temp);
			}
			return true;
		}
		return false;
	}
	
	

}
