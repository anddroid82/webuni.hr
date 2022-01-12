package hu.webuni.hr.andro.web;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.mapper.CompanyMapper;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.service.CompanyService;
import hu.webuni.hr.andro.service.EmployeeService;
import hu.webuni.hr.andro.validation.EmployeeAdd;

@Controller
public class HrController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	CompanyService companyService;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	CompanyMapper companyMapper;

	@GetMapping("/")
	public String home() {
		return "redirect:/list";
	}

	@GetMapping("/list")
	public String listEmployees(Map<String, Object> model) {
		model.put("employees", employeeMapper.employeesToDtos(employeeService.getEmployees()));
		model.put("referer", "employeelist");
		return "list";
	}

	@GetMapping("/details/{id}")
	public String detailsEmployee(@PathVariable long id, Map<String, Object> model) {
		Employee emp = employeeService.getEmployee(id);
		if (emp == null) {
			return "redirect:/error";
		} else {
			model.put("employee", employeeMapper.employeeToDto(employeeService.getEmployee(id)));
			return "details";
		}
	}

	@GetMapping("add")
	public String addEmployee(Map<String, Object> model) {
		model.put("employee", new EmployeeDto(0L, "", "", 0, null,null));
		model.put("type", "add");
		return "modify";
	}

	@PostMapping("addEmployee")
	public String addEmployeeAction(@Validated(EmployeeAdd.class) EmployeeDto employee, BindingResult bindingresult,
			Map<String, Object> model) {
		if (bindingresult.hasErrors()) {
			model.put("employee", employee);
			model.put("type", "add");
			model.put("errors", bindingresult.getFieldErrors());
			return "modify";
		} else {
			employeeService.addEmployee(employeeMapper.dtoToEmployee(employee));
			return "redirect:list";
		}
	}

	@PostMapping("modifyEmployee")
	public String modifyEmployeeAction(@Valid EmployeeDto employee,
			BindingResult bindingresult, Map<String, Object> model) {
		if (bindingresult.hasErrors()) {
			model.put("employee", employee);
			model.put("type", "modify");
			model.put("errors", bindingresult.getFieldErrors());
			return "modify";
		} else {
			Employee empFromServ = employeeService.getEmployee(employee.getId());
			empFromServ.setName(employee.getName());
			empFromServ.setRank(employee.getRank());
			empFromServ.setPayment(employee.getPayment());
			empFromServ.setEntrance(employee.getEntrance());
			employeeService.modifyEmployee(empFromServ);
			
			return "redirect:list";
		}
	}

	@GetMapping("/modify/{id}")
	public String modifyEmployee(@PathVariable long id, Map<String, Object> model) {
		EmployeeDto emp = employeeMapper.employeeToDto(employeeService.getEmployee(id));
		if (emp == null) {
			return "redirect:/error";
		} else {
			model.put("employee", emp);
			model.put("type", "modify");
			return "modify";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable long id) {
		Employee emp = employeeService.getEmployee(id);
		if (emp == null) {
			return "redirect:/error";
		} else {
			employeeService.deleteEmployee(emp);
			return "redirect:/list";
		}
	}

	@GetMapping("/companies")
	public String getCompanies(Map<String,Object> model) {
		model.put("companies", companyService.getCompanies());
		return "company/companies";
	}
	
	@GetMapping("/company/{id}/details")
	public String getCompanyData(@PathVariable Long id, Map<String, Object> model) {
		CompanyDto companyDto = companyMapper.companyToCompanyDto(companyService.getCompany(id));
		model.put("company", companyDto);
		model.put("employees", companyDto.getEmployees());
		model.put("referer", "companydetails");
		return "company/details";
	}

	@GetMapping("/company/{companyId}/employeedelete/{employeeId}")
	public String deleteEmployeeFromCompany(@PathVariable long companyId, @PathVariable long employeeId,
			Map<String, Object> model) {
		Company company = companyService.getCompany(companyId);
		if (company == null) {
			return "redirect:/error";
		}
		model.put("company", companyMapper.companyToCompanyDto(company));
		companyService.deleteEmployeeFromCompany(employeeId, companyId);
		model.put("employees", employeeMapper.employeesToDtos(company.getEmployees()));
		model.put("referer", "companydetails");
		return "company/details";
	}

	@GetMapping("/error")
	public String notFound() {
		return "error";
	}

}
