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
		offered = null;
		requested = null;
		
		Iterator<Item> items = itemService.findAll().iterator();
		if(items.hasNext()){
			offered = items.next();
		}
		if(items.hasNext()){
			requested = items.next();
		}
		
		// Checks basic requirements
		try{
			Assert.notNull(offered, "No offered found");
			Assert.notNull(requested, "No requested found");
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("user1");
		
/*		result = barterService.create();
		
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
		offered = null;
		requested = null;
		
		Iterator<Item> items = itemService.findAll().iterator();
		if(items.hasNext()){
			offered = items.next();
		}
		if(items.hasNext()){
			requested = items.next();
		}
		
		// Checks basic requirements
		try{
			Assert.notNull(offered, "No offered found");
			Assert.notNull(requested, "No requested found");
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("user1");
/*	
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
		offered = null;
		requested = null;
		
		Iterator<Item> items = itemService.findAll().iterator();
		if(items.hasNext()){
			offered = items.next();
		}
		if(items.hasNext()){
			requested = items.next();
		}
		
		// Checks basic requirements
		try{
			Assert.notNull(offered, "No offered found");
			Assert.notNull(requested, "No requested found");
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("user1");
/*	
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
		offered = null;
		requested = null;
		barter2 = null;
		
		Iterator<Item> items = itemService.findAll().iterator();
		if(items.hasNext()){
			offered = items.next();
		}
		if(items.hasNext()){
			requested = items.next();
		}
		
		if(barterService.findAllNotCancelled().iterator().hasNext()){
			barter2 = barterService.findAllNotCancelled().iterator().next();
		}
		
		// Checks basic requirements
		try{
			Assert.notNull(offered, "No offered found");
			Assert.notNull(requested, "No requested found");
			Assert.notNull(barter2, "No barter found");
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("user1");
/*	
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
		offered = null;
		requested = null;
		
		Iterator<Item> items = itemService.findAll().iterator();
		if(items.hasNext()){
			offered = items.next();
		}
		if(items.hasNext()){
			requested = items.next();
		}
		
		// Checks basic requirements
		try{
			Assert.notNull(offered, "No offered found");
			Assert.notNull(requested, "No requested found");
		}catch (Exception e) {
			// TODO: handle exception
			throw new InvalidPreTestException(e.toString());
		}
		
		// Execution of test
		authenticate("user1");
/*	
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
}
