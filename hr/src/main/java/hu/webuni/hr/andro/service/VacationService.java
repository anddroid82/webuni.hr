package hu.webuni.hr.andro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Vacation;
import hu.webuni.hr.andro.repository.VacationRepository;

@Service
public class VacationService {

	@Autowired
	VacationRepository vacationRepository;
	
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
}
