package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
	
	@Query("select m from Match m join m.creatorBarter cb join m.receiverBarter rb where m.cancelled = false and ( cb.user.id = ?1 or  rb.user.id = ?1)")
	Collection<Match> findAllUserInvolves(int userId);
	
	// Every [barter]match that remains unsigned one month after they were created.
	@Query("select m from Match m where ( m.offerSignsDate = null or m.requestSignsDate = null ) and ( YEAR(m.creationMoment) <= YEAR(CURRENT_DATE) and ( MONTH(CURRENT_DATE) - MONTH(m.creationMoment) >= 1) and DAY(m.creationMoment) <= DAY(CURRENT_DATE) )")
	Collection<Match> findAllNotSignedOneMonthSinceCreation();
	
	@Query("select m from Match m where m.creatorBarter.id = ?1 or m.receiverBarter.id = ?1")
	Collection<Match> findAllNotCancelledByBarterId(int barterId);
	
}
