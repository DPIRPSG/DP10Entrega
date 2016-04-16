package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Auditor;
import domain.Barter;
import domain.LegalText;
import domain.Match;
import domain.User;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MatchServiceTest extends AbstractTest {

	// Service under test -------------------------

	@Autowired
	private MatchService matchService;

	// Other services needed -----------------------

	@Autowired
	private BarterService barterService;
	
	@Autowired
	private LegalTextService legalTextService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuditorService auditorService;

	// Tests ---------------------------------------
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que se puede crear un Match en condiciones normales
	 */
	@Test
	public void testCreateMatchOk() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si ningún Barter del Match es del usuario que está logueado, falla
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value=true)
	public void testCreateMatchError1() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro Móvil")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro Móvil")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si los dos Barter del Match son del usuario que está logueado, falla
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value=true)
	public void testCreateMatchError2() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user2");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro Móvil")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro Móvil")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si algún barter está incluido en un Match no cancelado, falla
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value=true)
	public void testCreateMatchError3(){
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("¡¡Intercambio urgente!!")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si se crea un Match sin un Barter del usuario logueado, falla
	 */
	@Test(expected=NullPointerException.class)
	@Rollback(value=true)
	public void testCreateMatchError4() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		//match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si se crea un Match sin un Barter de otro usuario, falla
	 */
	@Test(expected=NullPointerException.class)
	@Rollback(value=true)
	public void testCreateMatchError5() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		//match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si se intenta crear un Match sin un LegalText, falla
	 */
	@Test(expected=ConstraintViolationException.class)
	@Rollback(value=true)
	public void testCreateMatchError6() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		//match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si se intenta crear un Match sin estar logueado, falla
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value=true)
	public void testCreateMatchError7() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
				
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si se intenta crear un Match con un Auditor ya asignado, falla
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value=true)
	public void testCreateMatchError8() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		Auditor auditor;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		auditor = auditorService.findAll().iterator().next();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match.setAuditor(auditor);
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
	
	/**
	 * Acme-Barter - Level C - 11.4
	 * An actor who is authenticated as a user must
	 * be able to create a match between two barters.
	 */
	/**
	 * Test que comprueba que si se intenta crear un Match con un Report ya escrito, falla
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value=true)
	public void testCreateMatchError9() {
		Match match;
		Barter creatorBarter, receiverBarter;
		LegalText legalText;
		Collection<Barter> barters;
		Collection<Match> matches;
		int numMatches;
		
		barters = barterService.findAll();
		matches = matchService.findAll();
		
		numMatches = matches.size();
		
		authenticate("user1");
		
		creatorBarter = null;
		receiverBarter = null;
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}		
		
		legalText = legalTextService.findAll().iterator().next();
				
		match = matchService.create();
		match.setCreatorBarter(creatorBarter);
		match.setReceiverBarter(receiverBarter);
		match.setLegalText(legalText);
		match.setReport("Test");
		match = matchService.save(match);
		
		matches = matchService.findAll();
		
		for(Barter b : barters) {
			if(b.getTitle().equals("Quiero otro PC")) {
				creatorBarter = b;
			} else if(b.getTitle().equals("Quiero otro Portátil")) {
				receiverBarter = b;
			}
		}	
		
		Assert.isTrue(matches.size() == (numMatches+1));
		Assert.isTrue(matches.contains(match));
		Assert.isTrue(creatorBarter.getCreatedMatch().contains(match));
		Assert.isTrue(receiverBarter.getReceivedMatch().contains(match));
		Assert.isTrue(match.getLegalText().equals(legalText));
		
		authenticate(null);
	}
}
