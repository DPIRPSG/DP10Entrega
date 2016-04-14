package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MatchRepository;
import domain.Match;
import domain.User;

@Service
@Transactional
public class MatchService {

	// Managed repository -----------------------------------------------------

		@Autowired
		private MatchRepository matchRepository;

		// Supporting services ----------------------------------------------------
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private ActorService actorService;

		// Constructors -----------------------------------------------------------

		public MatchService() {
			super();
		}

		// Simple CRUD methods ----------------------------------------------------
		
		public Match create(){
			
			Assert.isTrue(actorService.checkAuthority("USER"), "Only a User can create a Match.");
			
			Match result;
			
			result = new Match();
			
			result.setCancelled(false);
			result.setCreationMoment(new Date()); // Aqu� por ser NotNull, pero debe sobreescribirse en el save().
			
			return result;
			
		}
		
		public void save(Match match){
			
			Assert.isTrue(actorService.checkAuthority("ADMIN") || actorService.checkAuthority("USER") || actorService.checkAuthority("AUDITOR"), "Only an Actor loged in into the system can create a Match.");
			
			Assert.notNull(match);
			
			if(match.getId() == 0){ // Est� siendo creado
				
				User user;
				
				user = userService.findByPrincipal();
				
				Assert.isTrue(user == match.getCreatorBarter().getUser(), "You are not the creator of Your Barter.");
				
				Assert.isTrue(user != match.getReceiverBarter().getUser(), "You can't be the creator of The Other Barter.");
				
				Assert.isTrue(matchRepository.findAllNotCancelledByBarterId(match.getCreatorBarter().getId()).isEmpty() == true, "Your Barter is involved in other Match that are not cancelled.");
				
				Assert.isTrue(matchRepository.findAllNotCancelledByBarterId(match.getReceiverBarter().getId()).isEmpty() == true, "The Other Barter is involved in other Match that are not cancelled.");
				
				match.setCancelled(false);
				match.setCreationMoment(new Date());
				
			}else{ // Est� siendo editado
				
				Match originalMatch;
				
				originalMatch = matchRepository.findOne(match.getId());
				
				if(originalMatch.getCancelled() == true){ // Si est� cancelado, en el save no debe haber cambiado nada del mismo.
					
					Assert.isTrue(match.equals(originalMatch), "You can't edit, sign or manage a cancelled Match.");
					
				}else{ // Si no est� cancelado se comprueba que no hayan variados los cambios que no se deben poder modificar una vez establecidos.
					
					Assert.isTrue(match.getCreationMoment() != originalMatch.getCreationMoment(), "You can't edit the creationMoment of a Match.");
					
					if(originalMatch.getOfferSignsDate() != null){
						Assert.isTrue(match.getOfferSignsDate() != originalMatch.getOfferSignsDate(), "This Match has already been signed.");
					}
					
					if(originalMatch.getRequestSignsDate() != null){
						Assert.isTrue(match.getRequestSignsDate() != originalMatch.getRequestSignsDate(), "This Match has already been signed.");
					}
					
					Assert.isTrue(match.getLegalText().equals(originalMatch.getLegalText()), "You can't edit the legal text of a Match.");
					
					if(originalMatch.getAuditor() != null){
						Assert.isTrue(match.getAuditor() != originalMatch.getAuditor(), "You can't change the auditor of a Match.");
					}
					
					Assert.isTrue(match.getCreatorBarter() != originalMatch.getCreatorBarter(), "You can't edit the barters involved in a Match.");
					
					Assert.isTrue(match.getReceiverBarter() != originalMatch.getReceiverBarter(), "You can't edit the barters involved in a Match.");
					
				}
				
			}
			
			matchRepository.save(match);
			
		}
		
		public void cancel(Match match) { // M�todo usado para que un User cancele un match en el que est� involucrado.
			
			Assert.isTrue(actorService.checkAuthority("USER"), "Only an User loged in into the system can cancel a Match.");
			
			User user;
			int userId;
			
			user = userService.findByPrincipal();
			userId = user.getId();
			
			Assert.isTrue(matchRepository.findAllUserInvolves(userId).contains(match), "You can't cancel a Match in which you aren't involved.");

			match.setCancelled(true);
			
			this.save(match);
			
		}
		
		public void cancel(Collection<Match> matchs) { // M�todo usado para que un admin cancele varios matchs a la vez
			
			Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an Administrator loged in into the system can cancel several matchs at the same time.");
			
			for(Match m: matchs){
				m.setCancelled(true);
				this.save(m);
			}
			
		}
		
		public void sign(Match match) {
			
			Assert.isTrue(actorService.checkAuthority("USER"), "Only an User loged in into the system can sign a Match.");
			
			User user;
			int userId;
			Collection<Match> matchsPrincipalInvolves;
			Match matchToSign;
			
			user = userService.findByPrincipal();
			userId = user.getId();
			matchsPrincipalInvolves = matchRepository.findAllUserInvolves(userId);
			matchToSign = null;
			
			Assert.isTrue(matchRepository.findAllUserInvolves(userId).contains(match), "You can't sign a Match in which you aren't involved.");
			
			for(Match m: matchsPrincipalInvolves){
				if(m == match){
					Assert.isTrue(m.getCreatorBarter().getUser() == user || m.getReceiverBarter().getUser() == user, "You can't sign a Match in which you aren't involved.");
					matchToSign = m;
					break;
				}
			}
			
			if(matchToSign.getCreatorBarter().getUser() == user){
				matchToSign.setOfferSignsDate(new Date());
			}else if(matchToSign.getReceiverBarter().getUser() == user){
				matchToSign.setRequestSignsDate(new Date());
			}
			
			this.save(matchToSign);
			
		}

		public Match findOne(int matchId) {
			Match result;

			result = matchRepository.findOne(matchId);

			return result;
		}


		// Other business methods -------------------------------------------------
		
		public void cancelEveryMatchNotSignedOneMonthSinceCreation() {
			Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an Admin loged in into the system can cancel all Matchs that haven't been signed one month after their creation.");

			Collection<Match> matchsNotSignedOneMonthSinceCreation;
			
			matchsNotSignedOneMonthSinceCreation = matchRepository.findAllNotSignedOneMonthSinceCreation();
			
			this.cancel(matchsNotSignedOneMonthSinceCreation);
		}
		
		public Collection<Match> findAllUserInvolves(int userId) {
			
			Collection<Match> result;
			
			result = matchRepository.findAllUserInvolves(userId);
			
			return result;
			
		}
		
		public Collection<Match> findAllNotSignedOneMonthSinceCreation() {
			
			Collection<Match> result;
			
			result = matchRepository.findAllNotSignedOneMonthSinceCreation();
			
			return result;
			
		}

		public void flush() {
			matchRepository.flush();
		}
	
}
