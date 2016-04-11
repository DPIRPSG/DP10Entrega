package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Barter;

@Repository
public interface BarterRepository extends JpaRepository<Barter, Integer> {

	@Query("select b from Barter b where b.cancelled = false order by b.registerMoment desc")
	Collection<Barter> findAllNotCancelled();
	
}
