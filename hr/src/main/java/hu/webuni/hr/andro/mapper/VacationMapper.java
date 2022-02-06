package hu.webuni.hr.andro.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.andro.dto.VacationDto;
import hu.webuni.hr.andro.model.Vacation;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, CompanyMapper.class})
public interface VacationMapper {
	
	VacationDto vacationToDto(Vacation vacation);
	Vacation dtoToVacation(VacationDto vacation);
	
	List<VacationDto> vacationsToDtos(List<Vacation> vacations);
	List<Vacation> dtosToVacations(List<VacationDto> vacations);
	
}
