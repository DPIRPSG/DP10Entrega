package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BarterRepository;
import domain.Barter;
import domain.Item;
import domain.Match;
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
	
	@Autowired
	private ActorService actorService;
	
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
	
	public Barter create(){
		
		Assert.isTrue(actorService.checkAuthority("USER"), "Only a user can create an barter");
		
		Barter barter;
		Collection<Match> createdMatch;
		Collection<Match> receivedMatch;
		Collection<Barter> relatedBarter;
		
		barter = new Barter();
		createdMatch = new ArrayList<>();
		receivedMatch = new ArrayList<>();
		relatedBarter = new ArrayList<>();
		
		barter.setCreatedMatch(createdMatch);
		barter.setReceivedMatch(receivedMatch);
		barter.setRelatedBarter(relatedBarter);
		
		return barter;
		
	}
	
	public Barter save(Barter barter){
		
		Assert.notNull(barter);
		Barter result;
		
		result = barterRepository.save(barter);
		
		return result;
	}
	
	public void saveToEdit(Barter barter){
		Assert.notNull(barter);
		Assert.isTrue(actorService.checkAuthority("USER"), "Only a user can save a barter");
		
		if(barter.getId() == 0){
			User user;
			Item offered = null;
			Item requested = null;
			Collection<Match> createdMatch;
			Collection<Match> receivedMatch;
			Collection<Barter> relatedBarter;
			
			user = userService.findByPrincipal();
			createdMatch = new ArrayList<>();
			receivedMatch = new ArrayList<>();
			relatedBarter = new ArrayList<>();
			
			barter.setCancelled(false);
			barter.setRegisterMoment(new Date());
			
			barter.setUser(user);
			barter.setOffered(offered);
			barter.setRequested(requested);
			barter.setCreatedMatch(createdMatch);
			barter.setReceivedMatch(receivedMatch);
			barter.setRelatedBarter(relatedBarter);
		}else{
			barter = this.save(barter);
		}
	}
	
	public void cancel(Barter barter){
		
		Assert.notNull(barter);
		Assert.isTrue(barter.getId() != 0);
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an administrator can cancel an activity.");
		Assert.isTrue(!barter.isCancelled(), "This barter is already deleted.");
		
		barter.setCancelled(true);	
		
		barterRepository.save(barter);
		
	}
	
}
