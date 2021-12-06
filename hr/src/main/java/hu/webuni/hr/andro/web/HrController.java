package hu.webuni.hr.andro.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.andro.model.Employee;

@Controller
public class HrController {
	
	private List<Employee> employees = new ArrayList<>();
	
	{
		employees.add(new Employee((long) 723, "Teszt Elek", "rendszergazda", 450000, LocalDateTime.of(2019, 10, 2, 0, 0)));
		employees.add(new Employee((long) 561, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0)));
		employees.add(new Employee((long) 278, "Tra Pista", "rendszer tervező", 800000, LocalDateTime.of(2011, 2, 23, 0, 0)));
	}
	
	private Employee getEmployee(long id) {
		for (Employee e:employees) {
			if (e.getId() == id) return e;
		}
		return null;
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/list")
	public String listEmployees(Map<String,Object> model) {
		model.put("employees", employees);
		return "list";
	}
	
	@GetMapping("/details/{id}")
	public String detailsEmployee(@PathVariable long id, Map<String,Object> model) {
		model.put("employee", this.getEmployee(id));
		return "details";
	}
	
	@GetMapping("add")
	public String addEmployee(Map<String,Object> model) {
		model.put("newEmployee", new Employee(0L, "", "", 0, null));
		model.put("type", "add");
		return "modify";
	}
	
	@PostMapping("addEmployee")
	public String addEmployeeAction(Employee employee, Map<String,Object> model) {
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
		if (this.getEmployee(employee.getId()) != null) {
			error=true;
			errors.add("Employee exist with this id!");
		}
		if (error) {
			model.put("newEmployee", employee);
			model.put("type", "add");
			model.put("errors", errors);
			return "modify";
		} else {
			employees.add(employee);
			return "redirect:list";
		}
	}
	
	@GetMapping("/modify/{id}")
	public String modifyEmployee(@PathVariable long id, Map<String,Object> model) {
		model.put("newEmployee", this.getEmployee(id));
		model.put("type", "add");
		return "modify";
	}
	
}
