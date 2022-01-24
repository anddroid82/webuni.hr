package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.andro.model.Company_;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.Employee_;
import hu.webuni.hr.andro.model.Position_;

public class EmployeeSpecifications {

	public static Specification<Employee> hasId(long id) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
	}

	public static Specification<Employee> hasName(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), name.toLowerCase() + "%");
	}

	public static Specification<Employee> hasPosition(String positionName) {
		return (root, cq, cb) -> cb.like(root.get(Employee_.position).get(Position_.name), positionName);
	}

	public static Specification<Employee> hasPayment(int payment) {
		return (root, cq, cb) -> cb.between(root.get(Employee_.payment), (int) Math.floor(payment * 0.95),
				(int) Math.floor(payment * 1.05));
	}

	public static Specification<Employee> hasEntrance(LocalDateTime entrance) {
		LocalDateTime entranceDay = LocalDateTime.of(entrance.toLocalDate(), LocalTime.of(0, 0));
		return (root, cq, cb) -> cb.between(root.get(Employee_.entrance), entranceDay, entranceDay.plusDays(1));
	}

	public static Specification<Employee> hasCompanyName(String companyName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)),
				companyName.toLowerCase() + "%");
	}

}
