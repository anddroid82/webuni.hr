package hu.webuni.hr.andro.repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
}
