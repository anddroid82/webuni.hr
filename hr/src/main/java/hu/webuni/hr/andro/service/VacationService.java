package hu.webuni.hr.andro.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.Vacation;
import hu.webuni.hr.andro.model.VacationSearch;
import hu.webuni.hr.andro.model.VacationState;
import hu.webuni.hr.andro.repository.VacationRepository;

@Service
public class VacationService {

	@Autowired
	VacationRepository vacationRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	public Page<Vacation> getAll(Pageable pageable){
		return vacationRepository.findAll(pageable);
	}
	
	public Vacation getById(long id) {
		Optional<Vacation> optVac = vacationRepository.findById(id);
		if (optVac.isPresent()) {
			return optVac.get();
		}
		return null;
	}

	//@Transactional
	public Vacation addVacation(Vacation vacation) {
		vacation.setState(VacationState.NEW);
		Employee employee = employeeService.getEmployee(vacation.getOwner().getId());
		if (employee == null) {
			return null;
		}
		vacation.setOwner(employee);
		return vacationRepository.save(vacation);
	}
	
	@Transactional
	public Vacation modifyVacation(Vacation vacation) {
		if (vacationRepository.existsById(vacation.getId()) && vacation.getState() == VacationState.NEW) {
			return vacationRepository.save(vacation);
		}
		return null;
	}
	
	@Transactional
	public Vacation confirmVacation(long vacationId, boolean confirm, long confirmatorId) {
		Vacation vacation = this.getById(vacationId);
		Employee confirmator = employeeService.getEmployee(confirmatorId);
		if (vacation != null && confirmator != null && vacation.getState() == VacationState.NEW && vacation.getOwner().getSuperior().getId() == confirmatorId) {
			if (confirm) {
				vacation.setState(VacationState.AGREE);
			}else {
				vacation.setState(VacationState.DENIED);
			}
			vacation.setConfirmator(confirmator);
			return vacation;
		}
		return null;
	}
	
	
	@Transactional
	public Vacation deleteVacation(long id) {
		Vacation vacation = this.getById(id);
		if (vacation != null && vacation.getState() == VacationState.NEW) {
			vacationRepository.delete(vacation);
			return vacation;
		}
		return null;
	}
	
	public Page<Vacation> findVacationsByExample(VacationSearch example, Pageable pageable) {
		
		Specification<Vacation> spec = Specification.where(null);
		
		if (example.getState() != null) {
			spec = spec.and(VacationSpecifications.hasState(example.getState()));
		}
		if (StringUtils.hasText(example.getOwnerName())) {
			spec = spec.and(VacationSpecifications.hasOwnerName(example.getOwnerName()));
		}
		if (StringUtils.hasText(example.getConfirmatorName())) {
			spec = spec.and(VacationSpecifications.hasConfirmatorName(example.getConfirmatorName()));
		}
		if (example.getSubmitFrom() != null && example.getSubmitTo() != null) {
			spec = spec.and(VacationSpecifications.betweenSubmitDates(example.getSubmitFrom(),example.getSubmitTo()));
		}
		if (example.getFromDate() != null && example.getToDate() != null) {
			spec = spec.and(VacationSpecifications.intersectDates(example.getFromDate(),example.getToDate()));
		}
		
		return vacationRepository.findAll(spec,pageable);
	}
	
}
