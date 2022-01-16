package hu.webuni.hr.andro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.andro.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

}
