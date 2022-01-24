package hu.webuni.hr.andro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Position;
import hu.webuni.hr.andro.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	PositionRepository positionRepository;
	
	public List<Position> getPositions() {
		return positionRepository.findAllFull();
	}
	
}
