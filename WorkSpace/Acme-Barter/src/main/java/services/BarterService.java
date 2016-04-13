package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BarterRepository;
import domain.Barter;
import domain.User;

@Service
@Transactional
public class BarterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BarterRepository barterRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public BarterService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Barter findOne(int barterId) {
		Barter result;

		result = barterRepository.findOne(barterId);

		return result;
	}

	public Collection<Barter> findAll() {
		Collection<Barter> result;

		result = barterRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public void flush() {
		barterRepository.flush();
	}

	public Collection<Barter> findAllNotCancelled() {
		Collection<Barter> result;

		result = barterRepository.findAllNotCancelled();

		return result;
	}

	public Collection<Barter> findBySingleKeywordNotCancelled(String keyword) {
		Assert.notNull(keyword);
		Assert.isTrue(!keyword.isEmpty());
		
		Collection<Barter> result;

		result = barterRepository.findBySingleKeywordNotCancelled(keyword);
		
		return result;
	}
	
	public Collection<Barter> findBySingleKeyword(String keyword) {
		Assert.notNull(keyword);
		Assert.isTrue(!keyword.isEmpty());
		
		Collection<Barter> result;

		result = barterRepository.findBySingleKeyword(keyword);
		
		return result;
	}
	
	public Collection<Barter> findByUserNotCancelled(int userId){
		Collection<Barter> result;
		
		result = barterRepository.findByUserIdNotCancelled(userId);
		
		return result;
	}

	public Collection<Barter> findAllByFollowedUser() {
		Collection<Barter> result;
		User user;
		
		user = userService.findByPrincipal();
		
		result = barterRepository.findAllByFollowedUser(user.getId());
		
		return result;
	}
}
