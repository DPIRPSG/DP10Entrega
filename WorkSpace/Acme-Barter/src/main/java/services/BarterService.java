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
	
	public Collection<Barter> findByUserIdNotCancelledNotInMatchNotCancelled(int userId){
		Collection<Barter> result;
		
		result = barterRepository.findByUserIdNotCancelledNotInMatchNotCancelled(userId);
		
		return result;
	}
	
	public Collection<Barter> findAllOfOtherUsersByUserIdNotCancelledNotInMatchNotCancelled(int userId){
		Collection<Barter> result;
		
		result = barterRepository.findAllOfOtherUsersByUserIdNotCancelledNotInMatchNotCancelled(userId);
		
		return result;
	}

	public Collection<Barter> findAllByFollowedUser() {
		Collection<Barter> result;
		User user;
		
		user = userService.findByPrincipal();
		
		result = barterRepository.findAllByFollowedUser(user.getId());
		
		return result;
	}
	
	public Collection<Barter> findAllNotRelated(int barterId){
		Collection<Barter> result;
		Barter actualBarter;
		
		actualBarter = this.findOne(barterId);
		result = this.findAll();
		result.remove(actualBarter);
		result.removeAll(actualBarter.getRelatedBarter());
		
		return result;
	}
	
	public Barter create(){
		
		Assert.isTrue(actorService.checkAuthority("USER"), "Only a user can create a barter");
		
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
	
	public Barter saveToEdit(Barter barter){
		
		Assert.notNull(barter);
		Assert.isTrue(actorService.checkAuthority("USER") || actorService.checkAuthority("ADMIN"), "Only a user or an admin can save a barter");
		
		if(barter.getId() == 0){
			User user;
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
			barter.setCreatedMatch(createdMatch);
			barter.setReceivedMatch(receivedMatch);
			barter.setRelatedBarter(relatedBarter);
			barter = this.save(barter);
		}else{
			Barter barterPreSave;
			barterPreSave = this.findOne(barter.getId());
			barterPreSave.setRelatedBarter(barter.getRelatedBarter());
			barter = this.save(barterPreSave);
		}
		
		return barter;
	}
	
	public Barter saveToRelate(Barter input){
		Assert.notNull(input);
		Assert.isTrue(input.getId() > 0);
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an admin can relate a barter");

		Barter result;
		
		result = this.findOne(input.getId());
		Assert.notNull(result);
				
		result.setRelatedBarter(input.getRelatedBarter());
		
		result = this.save(result);
		
		for(Barter related:input.getRelatedBarter()){
			if(!related.getRelatedBarter().contains(result) && related != null){
				Collection<Barter> temp;
				
				temp = related.getRelatedBarter();
				temp.add(result);
				related.setRelatedBarter(temp);
				this.save(related);
			}
			if(countRelateBarter(result, related) > 1){
				Collection<Barter> temp;
				
				temp = result.getRelatedBarter();
				temp.remove(related);
				result.setRelatedBarter(temp);
			}
		}
		
		return result;
	}
	
	public void cancel(Barter barter){
		
		Assert.notNull(barter);
		Assert.isTrue(barter.getId() != 0);
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an administrator can cancel a barter.");
		Assert.isTrue(!barter.isCancelled(), "This barter is already deleted.");
		
		barter.setCancelled(true);	
		
		barterRepository.save(barter);
		
	}
	
	public Integer getTotalNumberOfBarterRegistered(){
		Integer result;
		
		result = barterRepository.getTotalNumberOfBarterRegistered();
		
		return result;
	}
	
	public Integer getTotalNumberOfBarterCancelled(){
		Integer result;
		
		result = barterRepository.getTotalNumberOfBarterCancelled();
		
		return result;
	}
	
	public Double ratioOfBarterNotRelatedToAnyBarter(){
		Double result;
		
		result = barterRepository.ratioOfBarterNotRelatedToAnyBarter();
		
		return result;
	}
	
	private int countRelateBarter(Barter barterOrigin, Barter barterToCount){
		int res = 0;
		
		for(Barter a:barterOrigin.getRelatedBarter()){
			if(a.equals(barterToCount))
				res++;
		}
		
		return res;
	}
}
