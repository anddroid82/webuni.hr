package hu.webuni.hr.andro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.andro.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
	
	//@Query(value = "select p from Position p order by p.name")
	//List<Position> findAll();
	
	@EntityGraph("Position.full")
	@Query(value = "select p from Position p order by p.name")
	List<Position> findAllFull();
	
	Position getByName(String name);
	
}
