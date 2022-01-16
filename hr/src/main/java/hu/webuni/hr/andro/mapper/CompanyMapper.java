package hu.webuni.hr.andro.mapper;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.dto.PositionDto;
import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.model.Position;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	
	List<CompanyDto> companiesToCompanyDtos(List<Company> companies);
	List<Company> companyDtosToCompanies(List<CompanyDto> companies);
	
	@Mapping(source = "employees",target = "employees",qualifiedByName = "dtosToEmployees")
	Company companyDtoToCompany(CompanyDto company);
	
	@Named("dtosToEmployees")
	public static List<Employee> dtosToEmployees(List<EmployeeDto> e) {
		List<Employee> result=new ArrayList<>();
		for (EmployeeDto ed : e) {
			result.add(new Employee(ed.getId(),ed.getName(),dtoToPosition(ed.getPosition()),ed.getPayment(),ed.getEntrance(),null));
		}
		return result;
	}
	
	@Mapping(source = "employees",target = "employees",qualifiedByName = "employeesToDtos")
	CompanyDto companyToCompanyDto(Company company);
	
	@Named("employeesToDtos")
	public static List<EmployeeDto> employeesToDtos(List<Employee> e) {
		List<EmployeeDto> result=new ArrayList<>();
		for (Employee ed : e) {
			result.add(new EmployeeDto(ed.getId(),ed.getName(),positionToDto(ed.getPosition()),ed.getPayment(),ed.getEntrance(),null));
		}
		return result;
	}
	
	
	@Named(value = "withoutEmployees")
	@Mapping(target = "employees", ignore = true)
	CompanyDto companyToCompanyDtoWithoutEmployees(Company company);
	
	@IterableMapping(qualifiedByName = "withoutEmployees")
	List<CompanyDto> companiesToCompanyDtosWithoutEmployees(List<Company> companies);
	
	
	static PositionDto positionToDto(Position p) {
		if (p == null)
			return null;
		return new PositionDto(p.getId(), p.getName(), p.getEducation(), p.getMinPayment());
	}

	static Position dtoToPosition(PositionDto p) {
		if (p == null)
			return null;
		return new Position(p.getId(), p.getName(), p.getEducation(), p.getMinPayment());
	}
	
	
}
