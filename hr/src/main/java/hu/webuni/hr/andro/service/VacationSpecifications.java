package hu.webuni.hr.andro.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.andro.model.Employee_;
import hu.webuni.hr.andro.model.Vacation;
import hu.webuni.hr.andro.model.VacationState;
import hu.webuni.hr.andro.model.Vacation_;

public class VacationSpecifications {

	public static Specification<Vacation> hasState(VacationState state) {
		return (root, cq, cb) -> cb.equal(root.get(Vacation_.state), state);
	}

	public static Specification<Vacation> hasOwnerName(String ownerName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Vacation_.owner).get(Employee_.name)), ownerName.toLowerCase()+"%");
	}
	
	public static Specification<Vacation> hasConfirmatorName(String confirmatorName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Vacation_.owner).get(Employee_.name)), confirmatorName.toLowerCase()+"%");
	}

	public static Specification<Vacation> betweenSubmitDates(LocalDate submitFrom, LocalDate submitTo) {
		return (root, cq, cb) -> cb.between(root.get(Vacation_.submitDateTime), LocalDateTime.of(submitFrom, LocalTime.of(0, 0)), LocalDateTime.of(submitTo, LocalTime.of(23, 59)));
	}

	public static Specification<Vacation> intersectDates(LocalDate fromDate, LocalDate toDate) {
		return (root, cq, cb) -> cb.and(cb.not(cb.lessThan(root.get(Vacation_.toDate), fromDate)),cb.not(cb.greaterThan(root.get(Vacation_.fromDate), toDate)));
		//!t1.end.isBefore(t2.begin) && !t1.begin.isAfter(t2.end);
	}
	
}
