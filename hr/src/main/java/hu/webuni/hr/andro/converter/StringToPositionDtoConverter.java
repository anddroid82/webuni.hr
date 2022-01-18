package hu.webuni.hr.andro.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import hu.webuni.hr.andro.dto.PositionDto;
import hu.webuni.hr.andro.mapper.CompanyMapper;
import hu.webuni.hr.andro.model.Position;
import hu.webuni.hr.andro.repository.PositionRepository;

@Component
public class StringToPositionDtoConverter implements Converter<String, PositionDto> {

	@Autowired
	PositionRepository positionRepository;
	
	@Override
	public PositionDto convert(String source) {
		Position p = positionRepository.getById(Long.parseLong(source));
		return CompanyMapper.positionToDto(p);
	}

	
	
}
