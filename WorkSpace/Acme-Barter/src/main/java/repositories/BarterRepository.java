package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Barter;

@Repository
public interface BarterRepository extends JpaRepository<Barter, Integer> {
	
}
