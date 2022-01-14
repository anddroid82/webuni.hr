package hu.webuni.hr.andro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	List<EmployeeDto> employeesToDtos(List<Employee> employees);

	@Mapping(source = "company", target = "company", qualifiedByName = "companyToDto")
	EmployeeDto employeeToDto(Employee employee);

	@Mapping(source = "company", target = "company", qualifiedByName = "dtoToCompany")
	Employee dtoToEmployee(EmployeeDto employeeDto);

	@Named("companyToDto")
	public static CompanyDto companyToDto(Company company) {
		if (company == null)
			return null;
		return new CompanyDto(company.getId(), company.getName(), company.getAddress());
	}

	@Named("dtoToCompany")
	public static Company companyToDto(CompanyDto company) {
		if (company == null)
			return null;
		return new Company(company.getId(), company.getName(), company.getAddress());
	}
}
