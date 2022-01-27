package hu.webuni.hr.andro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.model.Employee;

@Mapper(componentModel = "spring", uses = {PositionMapper.class,CompanyMapper.class})
public interface EmployeeMapper {

	List<EmployeeDto> employeesToDtos(List<Employee> employees);
	List<Employee> dtosToEmployees(List<EmployeeDto> employees);

	@Mapping(target = "company.employees", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	@Mapping(target = "company.employees", ignore = true)
	Employee dtoToEmployee(EmployeeDto employeeDto);	
	
}
