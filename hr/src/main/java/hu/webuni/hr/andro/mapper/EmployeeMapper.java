package hu.webuni.hr.andro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.model.Employee;

@Mapper(componentModel = "spring", uses = {PositionMapper.class,CompanyMapper.class})
public interface EmployeeMapper {

	List<EmployeeDto> employeesToDtos(List<Employee> employees);
	List<Employee> dtosToEmployees(List<EmployeeDto> employees);

	@Mapping(target = "company.employees", ignore = true)
	@Mapping(target = "superior.superior", ignore = true)
	@Mapping(target = "superior.company", ignore = true)
	@Mapping(target = "superior.password", ignore = true)
	@Mapping(target = "superior.junior", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "junior", qualifiedByName = "juniorBase")
	EmployeeDto employeeToDto(Employee employee);

	@Mapping(target = "company.employees", ignore = true)
	@Mapping(target = "superior.superior", ignore = true)
	@Mapping(target = "superior.company", ignore = true)
	@Mapping(target = "superior.password", ignore = true)
	@Mapping(target = "superior.junior", ignore = true)
	@Mapping(target = "password", ignore = true)
	Employee dtoToEmployee(EmployeeDto employeeDto);	
	
	
	@Named("juniorBase")
	@Mapping(target = "company", ignore = true)
	@Mapping(target = "superior", ignore = true)
	@Mapping(target = "junior", ignore = true)
	@Mapping(target = "password", ignore = true)
	EmployeeDto employeeToDtoJuniorBase(Employee employee);
}
