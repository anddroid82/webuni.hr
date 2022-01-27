package hu.webuni.hr.andro.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.model.Company;

@Mapper(componentModel = "spring", uses = {PositionMapper.class,EmployeeMapper.class})
public interface CompanyMapper {
	
	List<CompanyDto> companiesToCompanyDtos(List<Company> companies);
	List<Company> companyDtosToCompanies(List<CompanyDto> companies);
	
	Company companyDtoToCompany(CompanyDto company);
	CompanyDto companyToCompanyDto(Company company);
	
	@Named(value = "withoutEmployees")
	@Mapping(target = "employees", ignore = true)
	CompanyDto companyToCompanyDtoWithoutEmployees(Company company);
	
	@IterableMapping(qualifiedByName = "withoutEmployees")
	List<CompanyDto> companiesToCompanyDtosWithoutEmployees(List<Company> companies);

	
}
