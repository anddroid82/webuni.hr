package hu.webuni.hr.andro.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.repository.CompanyDtoRepository;
import hu.webuni.hr.andro.repository.EmployeeDtoRepository;

@Controller
public class HrController {
	
	@Autowired
	EmployeeDtoRepository employeeRepo;
	
	@Autowired
	CompanyDtoRepository companyRepo;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String listEmployees(Map<String,Object> model) {
		model.put("employees", employeeRepo.getEmployees());
		model.put("referer", "employeelist");
		return "list";
	}
	
	@GetMapping("/details/{id}")
	public String detailsEmployee(@PathVariable long id, Map<String,Object> model) {
		EmployeeDto emp = employeeRepo.getEmployee(id);
		if (emp == null) {
			return "redirect:/error";
		}else {
			model.put("employee", employeeRepo.getEmployee(id));
			return "details";
		}
	}
	
	@GetMapping("add")
	public String addEmployee(Map<String,Object> model) {
		model.put("employee", new EmployeeDto(0L, "", "", 0, null));
		model.put("type", "add");
		return "modify";
	}
	
	@PostMapping("addModEmployee")
	public String addModEmployeeAction(@RequestParam String type, EmployeeDto employee, Map<String,Object> model) {
		List<String> errors=new ArrayList<>();
		boolean error=false;
		if (employee.getName().equals("")) {
			error=true;
			errors.add("Name is required!");
		}
		if (employee.getRank().equals("")) {
			error=true;
			errors.add("Rank is required!");
		}
		if (employee.getPayment() == 0) {
			error=true;
			errors.add("Payment is required!");
		}
		if (employee.getEntrance() == null) {
			error=true;
			errors.add("Entrance date is required!");
		}
		if (type.equals("add") && employeeRepo.getEmployee(employee.getId()) != null) {
			error=true;
			errors.add("Employee exist with this id!");
		}
		if (error) {
			model.put("employee", employee);
			model.put("type", type);
			model.put("errors", errors);
			return "modify";
		} else {
			if (type.equals("add")) {
				employeeRepo.addEmployee(employee);
			}else {
				//#TODO ezt módosítani kell, nem szabad törölni a repobol
				if (type.equals("modify")) {
					EmployeeDto empFromRepo = employeeRepo.getEmployee(employee.getId());
					empFromRepo.setName(employee.getName());
					empFromRepo.setRank(employee.getRank());
					empFromRepo.setPayment(employee.getPayment());
					empFromRepo.setEntrance(employee.getEntrance());;
				}else {
					employeeRepo.addEmployee(employee);
				}
			}
			return "redirect:list";
		}
	}
	
	@GetMapping("/modify/{id}")
	public String modifyEmployee(@PathVariable long id, Map<String,Object> model) {
		EmployeeDto emp = employeeRepo.getEmployee(id);
		if (emp == null) {
			return "redirect:/error";
		}else {
			model.put("employee", emp);
			model.put("type", "modify");
			return "modify";
		}
	}
	
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable long id) {
		EmployeeDto emp = employeeRepo.getEmployee(id);
		if (emp == null) {
			return "redirect:/error";
		}else {
			employeeRepo.deleteEmployee(emp);
			return "redirect:/list";			
		}
	}
	
	@GetMapping("/company/{id}/details")
	public String getCompanyData(@PathVariable String id, Map<String, Object> model) {
		CompanyDto companyDto = companyRepo.getCompany(id);
		model.put("company", companyDto);
		model.put("employees", companyDto.getEmployees());
		model.put("referer", "companydetails");
		return "company/details";
	}
	
	@GetMapping("/company/{companyId}/employeedelete/{employeeId}")
	public String deleteEmployeeFromCompany(@PathVariable String companyId,@PathVariable long employeeId, Map<String, Object> model) {
		CompanyDto companyDto = companyRepo.getCompany(companyId);
		if (companyDto == null) {
			return "redirect:/error";
		}
		EmployeeDto employeeDto = companyDto.removeEmployee(employeeId);
		model.put("company", companyDto);
		model.put("employees", companyDto.getEmployees());
		model.put("referer", "companydetails");
		return "company/details";
	}
	
	@GetMapping("/error")
	public String notFound() {
		return "error";
	}
	
	
}
