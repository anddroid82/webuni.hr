package hu.webuni.hr.andro.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.mapper.CompanyMapper;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.AvgPaymentOfCompany;
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
	 * private MappingJacksonValue getMappingJSON(Object object, boolean full) {
	 * List<String> fields = new ArrayList<>(Arrays.asList("id", "name",
	 * "address")); if (full) { fields.add("employees"); } HashSet<String>
	 * properties = new HashSet<>(fields); SimpleBeanPropertyFilter propFilter =
	 * SimpleBeanPropertyFilter.filterOutAllExcept(properties); FilterProvider
	 * filter = new
	 * SimpleFilterProvider().addFilter("CompanyFilterExcludeEmployees",
	 * propFilter); MappingJacksonValue mapping = new MappingJacksonValue(object);
	 * mapping.setFilters(filter); return mapping; }
	 */

	@GetMapping
	public ResponseEntity<List<CompanyDto>> getAll(@RequestParam(required = false) boolean full) {
		List<CompanyDto> companies = null;
		if (full) {
			companies = new ArrayList<>(companyMapper.companiesToCompanyDtos(companyService.getCompanies(full)));
		} else {
			companies = new ArrayList<>(
					companyMapper.companiesToCompanyDtosWithoutEmployees(companyService.getCompanies(full)));
		}
		return ResponseEntity.ok(companies);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id, @RequestParam(required = false) boolean full) {
		Company c = companyService.getCompany(id,full);
		if (c == null) {
			return ResponseEntity.notFound().build();
		} else {
			if (full) {
				return ResponseEntity.ok(companyMapper.companyToCompanyDto(companyService.getCompany(id,full)));
			} else {
				return ResponseEntity
						.ok(companyMapper.companyToCompanyDtoWithoutEmployees(companyService.getCompany(id,full)));
			}
		}
	}

	@PostMapping
	public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto company) {
		Company cresult = companyService.addCompany(companyMapper.companyDtoToCompany(company));
		return ResponseEntity.ok(companyMapper.companyToCompanyDto(cresult));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> modifyCompany(@PathVariable Long id, @RequestBody CompanyDto company) {
		Company comp = companyService.modifyCompany(companyMapper.companyDtoToCompany(company));
		if (comp != null) {
			return ResponseEntity.ok(companyMapper.companyToCompanyDto(comp));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDto> deleteCompany(@PathVariable Long id) {
		Company company = companyService.deleteCompany(id);
		if (company == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(companyMapper.companyToCompanyDto(company));
		}
	}

	@PostMapping("/{companyId}/addEmployee")
	public ResponseEntity<EmployeeDto> addEmployeeToCompany(@RequestBody EmployeeDto employee,
			@PathVariable Long companyId) {
		Employee emp = companyService.addEmployeeToCompany(employeeMapper.dtoToEmployee(employee), companyId);
		if (emp != null) {
			return ResponseEntity.ok(employeeMapper.employeeToDto(emp));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{companyId}/deleteEmployee/{employeeId}")
	public ResponseEntity<EmployeeDto> deleteEmployeeFromCompany(@PathVariable Long companyId,
			@PathVariable long employeeId) {
		Employee employee = companyService.deleteEmployeeFromCompany(employeeId, companyId);
		if (employee != null) {
			return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/emppaymentgreater/{payment}")
	public ResponseEntity<List<CompanyDto>> findByEmployeePaymentGreaterThan(@PathVariable int payment) {
		return ResponseEntity.ok(companyMapper.companiesToCompanyDtos(companyService.findByEmployeePaymentGreaterThan(payment)));
	}
	
	@GetMapping("/empcountgreater/{count}")
	public ResponseEntity<List<CompanyDto>> findByEmployeeCountGreaterThan(@PathVariable long count) {
		return ResponseEntity.ok(companyMapper.companiesToCompanyDtos(companyService.findByEmployeeCountGreaterThan(count)));
	}
	
	@GetMapping("/avgpayment/{companyId}")
	public ResponseEntity<List<AvgPaymentOfCompany>> getAvgPaymentByRankOfCompany(@PathVariable long companyId) {
		return ResponseEntity.ok(companyService.getAvgPaymentByRankOfCompany(companyId));
	}
	
	
	@PostMapping("/{companyId}/changeEmployees")
	public boolean changeEmployeeList(@RequestBody List<EmployeeDto> employees, @PathVariable Long companyId) {
		return companyService.changeEmployeeListOfCompany(employeeMapper.dtosToEmployees(employees), companyId);
	}
}
