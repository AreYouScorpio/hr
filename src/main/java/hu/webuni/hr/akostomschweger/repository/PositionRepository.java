package hu.webuni.hr.akostomschweger.repository;

import hu.webuni.hr.akostomschweger.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Integer> {

        public List<Position> findByName(String name);
    }

