package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);	
	
	@Query("select u from User u join u.followed f where f.id = ?1")
	Collection<User> getFollowers(int userId);
	
	// DASHBOARD
	@Query("select count(u) from User u")
	Integer getTotalNumberOfUsersRegistered();
}