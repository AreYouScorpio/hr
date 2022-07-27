package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    public Optional<Position> findByName(String name);
}

