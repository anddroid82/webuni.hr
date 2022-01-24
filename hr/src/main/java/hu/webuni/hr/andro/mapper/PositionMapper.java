package hu.webuni.hr.andro.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.andro.dto.PositionDto;
import hu.webuni.hr.andro.model.Position;

@Mapper(componentModel = "spring")
public interface PositionMapper {

	PositionDto posToDto(Position p);
	Position dtoToPosition(PositionDto p);
	
	List<Position> dtosToPositions(List<PositionDto> p);
	List<PositionDto> positionsToDtos(List<Position> p);
	
	
}
