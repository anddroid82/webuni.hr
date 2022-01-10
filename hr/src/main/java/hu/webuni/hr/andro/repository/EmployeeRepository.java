package hu.webuni.hr.andro.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.andro.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Override
	@Query List<Employee> findAll();

	//a kezdeti adatfeltöltéshez használjuk, hogy törölje az összes employee rekordot és 
	//állítsa be az autogenerated id értékét 1-re 
	@Modifying
	@Transactional
	@Query(value = "truncate table employee;ALTER SEQUENCE hibernate_sequence RESTART;", nativeQuery = true) void truncateTable();
	
	
	List<Employee> findByRank(String rank);
	List<Employee> findByNameStartsWithIgnoreCase(String name);
	List<Employee> findByEntranceBetween(LocalDateTime start,LocalDateTime end);
	
}
