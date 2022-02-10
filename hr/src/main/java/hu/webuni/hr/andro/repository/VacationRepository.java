package hu.webuni.hr.andro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import hu.webuni.hr.andro.model.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long>, PagingAndSortingRepository<Vacation, Long>, JpaSpecificationExecutor<Vacation> {

	@EntityGraph("Vacation.full")
	@Query("select v from Vacation v where v.id=:id")
	Optional<Vacation> findByIdFull(Long id);
	
}
