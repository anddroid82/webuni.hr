package hu.webuni.hr.andro.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.andro.dto.CompanyDto;
import hu.webuni.hr.andro.repository.CompanyDtoRepository;

@RestController
@RequestMapping("/api/companies")
public class HrCompanyRestController {

	@Autowired
	CompanyDtoRepository companyRepo;

	@GetMapping
	public List<CompanyDto> getAll(){
		return new ArrayList<>(companyRepo.getCompanies());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable String id) {
		CompanyDto companyDto=companyRepo.getCompany(id);
		if (companyDto == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(companyDto);
		}
	}
	
	@PostMapping
	public CompanyDto createCompany(@RequestBody CompanyDto company) {
		companyRepo.addCompany(company);
		return company;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> modifyCompany(@PathVariable String id,@RequestBody CompanyDto company) {
		CompanyDto comp = companyRepo.getCompany(id);
		if (comp != null) {
			companyRepo.deleteCompany(comp);
			company.setId(id);
			companyRepo.addCompany(company);
			return ResponseEntity.ok(company);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CompanyDto> deleteCompany(@PathVariable String id) {
		CompanyDto company = companyRepo.getCompany(id);
		if (company == null) {
			return ResponseEntity.notFound().build();
		}else {
			companyRepo.deleteCompany(company);
			return ResponseEntity.ok(company);
		}
	}
	
	
}
