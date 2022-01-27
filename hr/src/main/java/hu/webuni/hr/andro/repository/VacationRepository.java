package hu.webuni.hr.andro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.webuni.hr.andro.model.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long>, PagingAndSortingRepository<Vacation, Long>, JpaSpecificationExecutor<Vacation> {

	
	
}
