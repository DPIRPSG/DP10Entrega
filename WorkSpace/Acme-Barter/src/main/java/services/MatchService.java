package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MatchRepository;
import domain.Auditor;
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

		@Autowired
		private AuditorService auditorService;
		
		
		// Constructors -----------------------------------------------------------

		public MatchService() {
			super();
		}

		// Simple CRUD methods ----------------------------------------------------
		
		public Match create(){
			
			Assert.isTrue(actorService.checkAuthority("USER"), "Only an User can create a Match.");
			
			Match result;
			
			result = new Match();
			
			result.setCancelled(false);
			result.setCreationMoment(new Date()); // Aquí por ser NotNull, pero debe sobreescribirse en el save().
			result.setReport(null);
			
			return result;
			
		}
		
		public void save(Match match){
			
			Assert.isTrue(actorService.checkAuthority("ADMIN") || actorService.checkAuthority("USER") || actorService.checkAuthority("AUDITOR"), "Only an Actor loged in into the system can create a Match.");
			
			Assert.notNull(match);
			
			if(match.getId() == 0){ // Está siendo creado
				
				User user;
				
				user = userService.findByPrincipal();
				
				Assert.isTrue(user == match.getCreatorBarter().getUser(), "You are not the creator of Your Barter.");
				
				Assert.isTrue(user != match.getReceiverBarter().getUser(), "You can't be the creator of The Other Barter.");
				
				Assert.isTrue(matchRepository.findAllNotCancelledByBarterId(match.getCreatorBarter().getId()).isEmpty() == true, "Your Barter is involved in other Match that are not cancelled.");
				
				Assert.isTrue(matchRepository.findAllNotCancelledByBarterId(match.getReceiverBarter().getId()).isEmpty() == true, "The Other Barter is involved in other Match that are not cancelled.");
				
				match.setCancelled(false);
				match.setCreationMoment(new Date());
				match.setReport(null);
				
			}else{ // Está siendo editado
				
				// No sirve de nada, puesto que al hacer el set ya el findOne te trae el objeto modificado (antes del save)
//				Match originalMatch;
//				
//				originalMatch = matchRepository.findOne(match.getId());
//				
//				if(originalMatch.getCancelled() == true){ // Si está cancelado, en el save no debe haber cambiado nada del mismo.
//					
//					Assert.isTrue(match.equals(originalMatch), "You can't edit, sign or manage a cancelled Match.");
//					
//				}else{ // Si no está cancelado se comprueba que no hayan variados los cambios que no se deben poder modificar una vez establecidos.
//					
//					Assert.isTrue(match.getCreationMoment().equals(originalMatch.getCreationMoment()), "You can't edit the creationMoment of a Match.");
//					
//					if(originalMatch.getOfferSignsDate() != null){
//						Assert.isTrue(match.getOfferSignsDate().equals(originalMatch.getOfferSignsDate()), "This Match has already been signed.");
//					}
//					
//					if(originalMatch.getRequestSignsDate() != null){
//						Assert.isTrue(match.getRequestSignsDate().equals(originalMatch.getRequestSignsDate()), "This Match has already been signed.");
//					}
//					
//					Assert.isTrue(match.getLegalText().equals(originalMatch.getLegalText()), "You can't edit the legal text of a Match.");
//					
//					if(originalMatch.getAuditor() != null){
//						Assert.isTrue(match.getAuditor().equals(originalMatch.getAuditor()), "You can't change the auditor of a Match.");
//					}
//					
//					Assert.isTrue(match.getCreatorBarter().equals(originalMatch.getCreatorBarter()), "You can't edit the barters involved in a Match.");
//					
//					Assert.isTrue(match.getReceiverBarter().equals(originalMatch.getReceiverBarter()), "You can't edit the barters involved in a Match.");
//					
//				}
				
			}
			
			matchRepository.save(match);
			
		}
		
		public void cancel(Match match) { // Método usado para que un User cancele un match en el que esté involucrado.
			
			Assert.isTrue(actorService.checkAuthority("USER"), "Only an User loged in into the system can cancel a Match.");
			
			Assert.isTrue(match.getCancelled() == false, "You can't edit, sign or manage a cancelled Match.");
			
			User user;
			int userId;
			
			user = userService.findByPrincipal();
			userId = user.getId();
			
			Assert.isTrue(matchRepository.findAllUserInvolves(userId).contains(match), "You can't cancel a Match in which you aren't involved.");

			match.setCancelled(true);
			
			this.save(match);
			
		}
		
		public void cancel(Collection<Match> matchs) { // Método usado para que un admin cancele varios matchs a la vez
			
			Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an Administrator loged in into the system can cancel several matchs at the same time.");
			
			for(Match m: matchs){
				if(m.getCancelled() == false){
					m.setCancelled(true);
					this.save(m);
				}
			}
			
		}
		
		public void sign(Match match) {
			
			Assert.isTrue(actorService.checkAuthority("USER"), "Only an User loged in into the system can sign a Match.");
			
			Assert.isTrue(match.getCancelled() == false, "You can't edit, sign or manage a cancelled Match.");
			
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
				Assert.isTrue(matchToSign.getOfferSignsDate() == null, "You have already signed this Match.");
				matchToSign.setOfferSignsDate(new Date());
			}else if(matchToSign.getReceiverBarter().getUser() == user){
				Assert.isTrue(matchToSign.getRequestSignsDate() == null, "You have already signed this Match.");
				matchToSign.setRequestSignsDate(new Date());
			}
			
			this.save(matchToSign);
			
		}

		public Match findOne(int matchId) {
			Match result;

			result = matchRepository.findOne(matchId);

			return result;
		}
		
		public Collection<Match> findAll() {
			Collection<Match> result;
			
			result = matchRepository.findAll();
			
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
		
		public Collection<Match> findAllByFollowedUser() {
			Collection<Match> result;
			User user;
			
			user = userService.findByPrincipal();
			
			result = matchRepository.findAllByFollowedUser(user.getId());
			
			return result;
		}
		
		public void selfAssignByAuditor(int matchId){
			Assert.isTrue(actorService.checkAuthority("AUDITOR"), "match.selfAssignByAuditor.noAuditor");

			Auditor auditor;
			Match match;
			
			auditor = auditorService.findByPrincipal();
			match = this.findOne(matchId);
			
			Assert.isTrue(match.getAuditor() == null, "match.selfAssignByAuditor.yetAssigned");
			
			match.setAuditor(auditor);
			
			this.save(match);
		}
		
		public Collection<Match> findAllByAuditor(){
			Assert.isTrue(actorService.checkAuthority("AUDITOR"), "match.findAllByAuditor.noAuditor");
			Collection<Match> result;
			int auditor;
			
			auditor = auditorService.findByPrincipal().getId();
			result = matchRepository.findAllByAuditorId(auditor);
			
			return result;
		}
		
		public void addReport(Match match){
			Assert.isTrue(actorService.checkAuthority("AUDITOR"), "match.selfAssignByAuditor.noAuditor");

			Auditor auditor;
			Match match_orig;
			
			auditor = auditorService.findByPrincipal();
			match_orig = this.findOne(match.getId());
			
			Assert.isTrue(match_orig.getAuditor().equals(auditor), "match.selfAssignByAuditor.notSelfAssigned");
			
			match_orig.setReport(match.getReport());
			
			this.save(match_orig);
		}
		

		public void flush() {
			matchRepository.flush();
		}
	
}
