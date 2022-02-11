package hu.webuni.hr.andro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.andro.dto.VacationDto;
import hu.webuni.hr.andro.model.Vacation;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface VacationMapper {
	
	@Mapping(target = "owner.junior", ignore = true)
	@Mapping(target = "owner.company", ignore = true)
	@Mapping(target = "owner.password", ignore = true)
	@Mapping(target = "owner.roles", ignore = true)
	@Mapping(target = "owner.superior.superior", ignore = true)
	VacationDto vacationToDto(Vacation vacation);
	Vacation dtoToVacation(VacationDto vacation);
	
	List<VacationDto> vacationsToDtos(List<Vacation> vacations);
	List<Vacation> dtosToVacations(List<VacationDto> vacations);
	
}
