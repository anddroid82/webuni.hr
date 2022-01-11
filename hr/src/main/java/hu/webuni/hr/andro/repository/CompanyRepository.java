package hu.webuni.hr.andro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.andro.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
}
