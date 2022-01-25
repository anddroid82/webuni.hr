package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.webuni.hr.andro.model.Company;
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
	public Employee addEmployee(Employee employee) {
		employee.setId(null);
		return employeeRepository.save(employee);
	}

	public Employee modifyEmployee(Employee employee) {
		if (employeeRepository.existsById(employee.getId())) {
			return employeeRepository.save(employee);
		}
		return null;
	}

	public Employee getEmployee(long id) {
		Optional<Employee> empOpt = employeeRepository.findByIdFull(id);
		if (empOpt.isPresent()) {
			return empOpt.get();
		}
		return null;
	}

	public Employee deleteEmployee(Employee emp) {
		return this.deleteEmployee(emp.getId());
	}

	@Transactional
	public Employee deleteEmployee(long id) {
		Optional<Employee> empOpt = employeeRepository.findByIdFull(id);
		if (empOpt.isPresent()) {
			employeeRepository.deleteById(id);
			return empOpt.get();
		}
		return null;
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll(Sort.by("name"));
	}

	public List<Employee> getEmployees(Integer pageNo, Integer pageSize, String sortBy) {
		if (pageNo == -1 || pageSize == -1) {
			Sort sort = Sort.by(sortBy);
			return employeeRepository.findAll(sort);
		} else {
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

	public List<Employee> setPaymentToMinimumByPosition(String positionName, int payment) {
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

	public List<Employee> findEmployeesByExample(Employee example) {

		long id = example.getId();
		String name = example.getName(); // név eleji egyezés case-insensitive
		String positionName = example.getPosition().getName(); // pontos egyezés
		int payment = example.getPayment(); // +-5%
		LocalDateTime entrance = example.getEntrance(); // a megadott napon, idő nem lényeg
		String companyName = example.getCompany().getName(); // a név eleje egyezzen, case insensitive

		Specification<Employee> spec = Specification.where(null);
		if (id > 0) {
			spec = spec.and(EmployeeSpecifications.hasId(id));
		}
		if (StringUtils.hasText(name)) {
			spec = spec.and(EmployeeSpecifications.hasName(name));
		}
		if (StringUtils.hasText(name)) {
			spec = spec.and(EmployeeSpecifications.hasPosition(positionName));
		}
		if (payment > 0) {
			spec = spec.and(EmployeeSpecifications.hasPayment(payment));
		}
		if (entrance != null) {
			spec = spec.and(EmployeeSpecifications.hasEntrance(entrance));
		}
		if (StringUtils.hasText(companyName)) {
			spec = spec.and(EmployeeSpecifications.hasCompanyName(companyName));
		}
		return employeeRepository.findAll(spec);
	}

}
