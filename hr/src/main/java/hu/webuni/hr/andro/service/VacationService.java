package hu.webuni.hr.andro.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.Vacation;
import hu.webuni.hr.andro.model.VacationState;
import hu.webuni.hr.andro.repository.VacationRepository;

@Service
public class VacationService {

	@Autowired
	VacationRepository vacationRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	public List<Vacation> getAll(){
		return vacationRepository.findAll();
	}
	
	public Vacation getById(long id) {
		Optional<Vacation> optVac = vacationRepository.findById(id);
		if (optVac.isPresent()) {
			return optVac.get();
		}
		return null;
	}

	public Vacation addVacation(Vacation vacation) {
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
		if (vacation != null && confirmator != null && vacation.getState() == VacationState.NEW) {
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
	
	
}
