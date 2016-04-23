package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import utilities.InvalidPostTestException;
import utilities.InvalidPreTestException;
import domain.Actor;
import domain.Barter;
import domain.Item;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BarterServiceTest extends AbstractTest {

	// Service under test -------------------------
	
	@Autowired
	private BarterService barterService;
	
	// Other services needed -----------------------
	
	@Autowired
	private ItemService itemService;
		
	// Tests ---------------------------------------
	
	/**
	 * Acme-Six-Pack - Level C - 11.2
	 * Create a Barter.
	 * 
	 * Positive test case: Crear un Barter asociandole Items
	 * 
	 */
	
	@Test 
	public void testBarterCreationOk() {
		// Declare variables
		User customer;
		Barter result;
		Item offered;
		Item requested;
		
		// Load objects to test
/*
		offered = createItem("Item offered");
		requested = createItem("Item requested");
		
		// Checks basic requirements
		
		// Execution of test
		authenticate("user1");
		
		result = barterService.create();
		
		result.setOffered(offered);
		result.setRequested(requested);
		result.setTitle("Example barter");
		
		result = barterService.save(result);
				
		// Checks results
		authenticate("admin");
		Assert.isTrue(barterService.findAll().contains(result), "El nuevo barter no se encuentra en el sistema."); // First check
		unauthenticate();
*/
	}
	
	/**
	 * Negative test case: Crear un Barter como cancelado
	 *
	 */
	
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBarterCreationErrorIsCancelled() {
		// Declare variables
		User customer;
		Barter result;
		Item offered;
		Item requested;
		
		// Load objects to test
/*
		offered = createItem("Item offered");
		requested = createItem("Item requested");
		
		// Checks basic requirements
		
		// Execution of test
		authenticate("user1");
	
		result = barterService.create();
		
		result.setOffered(offered);
		result.setRequested(requested);
		result.setTitle("Example barter");
		result.setCancelled(true);
		
		result = barterService.save(result);
				
		// Checks results
		try{
			authenticate("admin");
			Assert.isTrue(!barterService.findAll().contains(result), "El nuevo barter se encuentra en el sistema."); // First check
			unauthenticate();
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
*/

	}
	
	/**
	 * Negative test case: Crear un Barter cambiando el registerMoment 
	 *
	 */
	
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBarterCreationErrorRegisterMomentChanged() {
		// Declare variables
		User customer;
		Barter result;
		Item offered;
		Item requested;
		
		// Load objects to test
/*
		offered = createItem("Item offered");
		requested = createItem("Item requested");
		
		// Checks basic requirements
		
		// Execution of test
		authenticate("user1");
	
		result = barterService.create();
		
		result.setOffered(offered);
		result.setRequested(requested);
		result.setTitle("Example barter");
		
		Calendar a = Calendar.getInstance();
		a.add(Calendar.DAY_OF_MONTH, -5);
		result.setRegisterMoment(a.getTime());
		
		result = barterService.save(result);
				
		// Checks results
		try{
			authenticate("admin");
			Assert.isTrue(!barterService.findAll().contains(result), "El nuevo barter se encuentra en el sistema."); // First check
			unauthenticate();
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
*/

	}
	
	/**
	 * Negative test case: Crear un Barter relacionado con otro 
	 *
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBarterCreationErrorIsRelated() {
		// Declare variables
		User customer;
		Barter result;
		Barter barter2;
		Item offered;
		Item requested;
		Collection<Barter> barters;
		
		// Load objects to test
/*
		offered = createItem("Item offered");
		requested = createItem("Item requested");
		barter2 = null;
		
		if(barterService.findAllNotCancelled().iterator().hasNext()){
			barter2 = barterService.findAllNotCancelled().iterator().next();
		}
		
		// Checks basic requirements
		try{
			Assert.notNull(barter2, "No barter found");
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("user1");
	
		result = barterService.create();
		
		result.setOffered(offered);
		result.setRequested(requested);
		result.setTitle("Example barter");
		barters = result.getRelatedBarter();
		barters.add(barter2);
		result.setRelatedBarter(barters);
		
		result = barterService.save(result);
				
		// Checks results
		try{
			authenticate("admin");
			Assert.isTrue(!barterService.findAll().contains(result), "El nuevo barter se encuentra en el sistema."); // First check
			unauthenticate();
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
*/

	}
	
	
	/**
	 * Negative test case: Crear un Barter como admin
	 *
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBarterCreationErrorAdmin() {
		// Declare variables
		User customer;
		Barter result;
		Item offered;
		Item requested;
		
		// Load objects to test
/*
		offered = createItem("Item offered");
		requested = createItem("Item requested");
		
		// Checks basic requirements
		
		// Execution of test
		authenticate("user1");
	
		result = barterService.create();
		
		result.setOffered(offered);
		result.setRequested(requested);
		result.setTitle("Example barter");
		
		authenticate("admin");
		result = barterService.save(result);
				
		// Checks results
		try{
			authenticate("admin");
			Assert.isTrue(!barterService.findAll().contains(result), "El nuevo barter se encuentra en el sistema."); // First check
			unauthenticate();
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
*/

	}
	
	/**
	 * Acme-Six-Pack - Level C - 12.3
	 * Cancel a barter if he or she thinks that it is inappropriate. Barters that are cancelled
are not displayed to users, only to administrators
	 * 
	 * Positive test case: Cancelar un barter y comprobar que no lo ve un usuario. 
	 * El barter no debe estar involucrado en ningún match (para asegurarnos que funciona como debe)
	 * 
	 */
	@Test 
	public void testBarterCancellationOk() {
		// Declare variables
		String username;
		Barter result;
		
		// Load objects to test
		
		authenticate("admin");
		result = null;
		username = "";
		
		for(Barter b:barterService.findAll()){
			boolean isAcceptable;
			
			isAcceptable = !b.isCancelled(); // No debe estar cancelado
			isAcceptable = isAcceptable && b.getCreatedMatch().isEmpty(); // Ningún match asociado
			isAcceptable = isAcceptable && b.getReceivedMatch().isEmpty(); // Ningún match asociado
			
			if (isAcceptable){
				result = b;
				username = b.getUser().getUserAccount().getUsername();
				break;
			}
				
		}
		
		unauthenticate();
		
		// Checks basic requirements
		try{
			Assert.notNull(result);
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("admin");
		
		barterService.cancel(result);
		
		result = barterService.findOne(result.getId());
				
		// Checks results
		try{
			Assert.isTrue(!barterService.findAllNotCancelled().contains(result), "El barter no se ha cancelado correctamente."); // First check
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
	}
	
	/**
	 * Positive test case: Cancelar un barter como propietario.
	 *
	 */
	@Test 
	public void testBarterCancellationOkUser() {
		// Declare variables
		String username;
		Barter result;
		
		// Load objects to test
		
		authenticate("admin");
		result = null;
		username = "";
		
		for(Barter b:barterService.findAll()){
			boolean isAcceptable;
			
			isAcceptable = !b.isCancelled(); // No debe estar cancelado
			isAcceptable = isAcceptable && b.getCreatedMatch().isEmpty(); // Ningún match asociado
			isAcceptable = isAcceptable && b.getReceivedMatch().isEmpty(); // Ningún match asociado
			
			if (isAcceptable){
				result = b;
				username = b.getUser().getUserAccount().getUsername();
				break;
			}
				
		}
		
		unauthenticate();
		
		// Checks basic requirements
		try{
			Assert.notNull(result);
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate(username);
		
		barterService.cancel(result);
		
		result = barterService.findOne(result.getId());
				
		// Checks results
		try{
			Assert.isTrue(!barterService.findAllNotCancelled().contains(result), "El barter no se ha cancelado correctamente."); // First check
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
	}
	
	/**
	 * Negative test case: Cancelarlo un usuario que no sea el propietario
	 *
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBarterCancellationErrorNotPropietary() {
		// Declare variables
		String username;
		String usernameToUse;
		Barter result;
		
		// Load objects to test
		
		authenticate("admin");
		result = null;
		username = "";
		usernameToUse = "user1";
		
		for(Barter b:barterService.findAll()){
			boolean isAcceptable;
			
			isAcceptable = !b.isCancelled(); // No debe estar cancelado
			isAcceptable = isAcceptable && b.getCreatedMatch().isEmpty(); // Ningún match asociado
			isAcceptable = isAcceptable && b.getReceivedMatch().isEmpty(); // Ningún match asociado
			isAcceptable = isAcceptable && ! b.getUser().getUserAccount().getUsername().equals(usernameToUse);//No es el usuario con el que quiero realizar la operación
			
			if (isAcceptable){
				result = b;
				username = b.getUser().getUserAccount().getUsername();
				break;
			}
				
		}
		
		unauthenticate();
		
		// Checks basic requirements
		try{
			Assert.notNull(result);
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate(usernameToUse);
		
		barterService.cancel(result);
		
		result = barterService.findOne(result.getId());
				
		// Checks results
		try{
			Assert.isTrue(barterService.findAllNotCancelled().contains(result), "El barter se ha cancelado correctamente."); // First check
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
	}
	
	/**
	 * Acme-Barter - Level C - 12.5.2
	 * The total number of barters that have been registered.
	 */
	@Test 
	public void testTotalBarters() {
		// Declare variables
		int totalUsersInTest;
		int result;
		
		// Load objects to test
		authenticate("admin");
		totalUsersInTest = barterService.findAll().size();
		
		// Checks basic requirements

		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");		
		
		// Checks results	
	}
	
	/**
	 * Acme-Barter - Level C - 12.5.3
	 * The total number of barters that have been cancelled.
	 */
	@Test 
	public void testTotalBartersCancelled() {
		// Declare variables
		int totalUsersInTest;
		int result;
		
		// Load objects to test
		authenticate("admin");
		totalUsersInTest = barterService.findAll().size() - barterService.findAllNotCancelled().size();
		
		// Checks basic requirements

		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");		
		
		// Checks results	
	}
	
	
	/**
	 * Negative test case: Cancelarlo un auditor
	 *
	 */
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true) 
	public void testBarterCancellationErrorAuditor() {
		// Declare variables
		String username;
		Barter result;
		
		// Load objects to test
		
		authenticate("admin");
		result = null;
		username = "";
		
		for(Barter b:barterService.findAll()){
			boolean isAcceptable;
			
			isAcceptable = !b.isCancelled(); // No debe estar cancelado
			isAcceptable = isAcceptable && b.getCreatedMatch().isEmpty(); // Ningún match asociado
			isAcceptable = isAcceptable && b.getReceivedMatch().isEmpty(); // Ningún match asociado
			
			if (isAcceptable){
				result = b;
				username = b.getUser().getUserAccount().getUsername();
				break;
			}
				
		}
		
		unauthenticate();
		
		// Checks basic requirements
		try{
			Assert.notNull(result);
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("auditor1");
		
		barterService.cancel(result);
		
		result = barterService.findOne(result.getId());
				
		// Checks results
		try{
			Assert.isTrue(barterService.findAllNotCancelled().contains(result), "El barter se ha cancelado correctamente."); // First check
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPostTestException(e.toString());
		}
	}
	
	/**
	 * Acme-Six-Pack - Level B - 4.1
	 * Relate any two barters.
	 * 
	 * Positive test case: Relacionar dos barter cualesquiera
	 * 
	 */
	@Test 
	public void testBarterRelateOk() {
		// Declare variables
		Barter barter1;
		Barter barter2;
		
		// Load objects to test
		
		authenticate("admin");
		barter1 = null;
		barter2 = null;
		
		for(Barter b:barterService.findAll()){
			if (barter1 != null && !b.getRelatedBarter().contains(barter1)
					&& !barter1.getRelatedBarter().contains(barter2)) {
				barter2 = b;
				break;
			}
			barter1 = b;
		}
		unauthenticate();
		
		// Checks basic requirements
		try{
			Assert.notNull(barter1);
			Assert.notNull(barter2);
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("admin");
		
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");
		
//		result = barterService.findOne(result.getId());
//				
//		// Checks results
//		try{
//			Assert.isTrue(!barterService.findAllNotCancelled().contains(result), "El barter no se ha cancelado correctamente."); // First check
//		}catch (Exception e) {
//			// TODO: handle exception
//			throw new InvalidPostTestException(e.toString());
//		}
	}
	
	/**
	 * Positive test case: Asociarlo varias veces y comprobar que se muestra una sola vez
	 *
	 */
	
	/**
	 * Negative test case: Relacionarlo un auditor
	 *
	 */
	
	/**
	 * Negative test case: Relacionarlo un user
	 *
	 */
	

	
//	private Item createItem(String name){
//		Item result;
//		
//		result = itemService.create();
//		
//		result.setName(name);
//		result.setDescription("Descripción -- " + name);
//		
//		return result;
//	}
}
