package hu.webuni.hr.andro.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	List<EmployeeDto> employeesToDtos(List<Employee> employees);

	EmployeeDto employeeToDto(Employee employee);
	
	Employee employeeDtoToEmployee(EmployeeDto employeeDto);

}
