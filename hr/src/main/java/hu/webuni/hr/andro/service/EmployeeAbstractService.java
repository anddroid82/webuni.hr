package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.Position;
import hu.webuni.hr.andro.repository.EmployeeRepository;
import hu.webuni.hr.andro.repository.PositionRepository;

@Service
public abstract class EmployeeAbstractService implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;

	@PostConstruct
	public void postConstruct() {
		System.out.println("postconstruct");
	}

	// kerdes: a Transactional-t pontosan hol kell használni? ha metódus hív
	// másikat, és az változtat, akkor a hívó is rá lehet rakni?
	@Transactional
	public Employee addEmployee(Employee employee) {
		employee.setId(null);
		return employeeRepository.save(employee);
	}

	@Transactional
	public Employee modifyEmployee(Employee employee) {
		if (employeeRepository.existsById(employee.getId())) {
			return employeeRepository.save(employee);
		}
		return null;
	}

	public Employee getEmployee(long id) {
		if (employeeRepository.existsById(id)) {
			return employeeRepository.getById(id);
		}
		return null;
	}

	public Employee deleteEmployee(Employee emp) {
		return this.deleteEmployee(emp.getId());
	}

	@Transactional
	public Employee deleteEmployee(long id) {
		if (employeeRepository.existsById(id)) {
			Employee emp = employeeRepository.getById(id);
			employeeRepository.deleteById(id);
			return emp;
		}
		return null;
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	public List<Employee> getEmployees(Integer pageNo, Integer pageSize, String sortBy) {
		if (pageNo == -1 || pageSize == -1) {
			Sort sort = Sort.by(sortBy);
			return employeeRepository.findAll(sort);
		}else {
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<Employee> page = employeeRepository.findAll(pageable);
			if (page.hasContent()) {
				return page.getContent();
			}
		}
		
		return new ArrayList<Employee>();
	}

//	public void setEmployees(List<Employee> employees) {
//		//this.employees = employees;
//	}

	public List<Employee> getEmployeesByRank(String rank) {
		return employeeRepository.findByPositionName(rank);
	}

	public List<Employee> getEmployeesByNameStartsWithIgnoreCase(String name) {
		return employeeRepository.findByNameStartsWithIgnoreCase(name);
	}

	public List<Employee> getEmployeesByEntranceBetween(LocalDateTime start, LocalDateTime end) {
		return employeeRepository.findByEntranceBetween(start, end);
	}

	public List<Employee> getEmployeesByPaymentGreaterThan(int payment) {
		return employeeRepository.findByPaymentGreaterThan(payment);
	}
	
	@Transactional
	public List<Employee> setPaymentToMinimumByPosition(String positionName, int payment){
		Position position = positionRepository.getByName(positionName);
		List<Employee> emps = employeeRepository.findByPosition_NameAndPaymentLessThan(positionName, payment);
		for (Employee e : emps) {
			e.setPayment(payment);
			employeeRepository.save(e);
		}
		position.setMinPayment(payment);
		positionRepository.save(position);
		return emps;
	}

}
