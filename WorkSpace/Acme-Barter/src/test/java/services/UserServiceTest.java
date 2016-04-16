package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import domain.Actor;
import domain.Barter;
import domain.Match;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class UserServiceTest extends AbstractTest {

	// Service under test -------------------------

	@Autowired
	private UserService customerService;
	
	// Other services needed -----------------------
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private BarterService barterService;
	
	@Autowired
	private MatchService matchService;
	
	// Tests ---------------------------------------
	
	/**
	 * Acme-Six-Pack - Level C - 8.1
	 * Register to the system as a customer.
	 */
	
	/**
	 * Positive test case: Registrarse como Customer
	 * 		- Acción
	 * 		+ Entrar el registro como anónimo
	 * 		+ Rellenar los campos
	 * 		+ Presionar en registrarse
	 * 		- Comprobación
	 * 		+ Entrar al sistema con privilegios de administrador
	 * 		+ Comprobar que existe ese nuevo usuario entre los usuarios registrados
	 * 		+ Cerrar su sesión
	 * 		+ Comprobar que puede loguearse con el nuevo usuario creado
	 * 		+ Cerrar su sesión
	 */
	
	@Test 
	public void testNewCustomer() {
		// Declare variables
		User customer;
		UserAccount userAccount;
		User customerRegistered;
		Actor authenticatedCustomer;
		
		// Load objects to test
		
		
		// Checks basic requirements
		
		
		// Execution of test
		customer = customerService.create();
		
		customer.setName("Nuevo");
		customer.setSurname("Customer");
		customer.setPhone("123456789");

		userAccount = userAccountService.create("CUSTOMER");
		
		userAccount.setUsername("nuevoCustomer");
		userAccount.setPassword("nuevoCustomer");
		
		customer.setUserAccount(userAccount);
		
		customerRegistered = customerService.saveFromOtherService(customer);
		
		// Checks results
		authenticate("admin");
		Assert.isTrue(customerService.findAll().contains(customerRegistered), "El customer nuevo registrado no se encuentra entre los customers registrados en el sistema."); // First check
		unauthenticate();
		
		authenticate("customer1");
		
		authenticatedCustomer = actorService.findByPrincipal();
		
		Assert.notNull(authenticatedCustomer, "No se ha podido loguear al customer que se acaba de registrar."); // Second check
		
		unauthenticate();

	}
	
	/**
	 * Negative test case: Registrarse como Customer con contraseña a null
	 * 		- Acción
	 * 		+ Entrar el registro como anónimo
	 * 		+ Rellenar los campos y la contraseña a null
	 * 		+ Presionar en registrarse
	 * 		- Comprobación
	 * 		+ Comprobar que salta una excepción del tipo: IllegalArgumentException
	 * 		+ Cerrar su sesión
	 */
	
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true)
//	@Test 
	public void testNewCustomerNullPassword() {
		// Declare variables
		User customer;
		UserAccount userAccount;
//		Customer customerRegistered;
//		Actor authenticatedCustomer;
		
		// Load objects to test
		
		
		// Checks basic requirements
		
		
		// Execution of test
		customer = customerService.create();
		
		customer.setName("Nuevo");
		customer.setSurname("Customer");
		customer.setPhone("123456789");

		userAccount = userAccountService.create("CUSTOMER");
		
		userAccount.setUsername("nuevoCustomer");
		userAccount.setPassword(null);
		
		customer.setUserAccount(userAccount);
		
		customerService.saveFromOtherService(customer);
		
		// Checks results
//		authenticate("admin");
//		Assert.isTrue(customerService.findAll().contains(customerRegistered), "El customer nuevo registrado no se encuentra entre los customers registrados en el sistema."); // First check
//		unauthenticate();
//		
//		authenticate("customer1");
//		
//		authenticatedCustomer = actorService.findByPrincipal();
//		
//		Assert.notNull(authenticatedCustomer, "No se ha podido loguear al customer que se acaba de registrar."); // Second check
		
		unauthenticate();

	}
	
	/**
	 * Negative test case: Registrarse como Customer estando identificado en el sistema
	 * 		- Acción
	 * 		+ Entrar el registro como customer
	 * 		+ Rellenar los campos y la contraseña a null
	 * 		+ Presionar en registrarse
	 * 		- Comprobación
	 * 		+ Comprobar que salta una excepción del tipo: IllegalArgumentException
	 * 		+ Cerrar su sesión
	 */
	
	@Test(expected=IllegalArgumentException.class)
	@Rollback(value = true)
//	@Test
	public void testNewCustomerAsCustomer() {
		// Declare variables
		User customer;
		UserAccount userAccount;
//		Customer customerRegistered;
//		Actor authenticatedCustomer;
		
		// Load objects to test
		authenticate("customer1");
		
		// Checks basic requirements
		
		
		// Execution of test
		customer = customerService.create();
		
		customer.setName("Nuevo");
		customer.setSurname("Customer");
		customer.setPhone("123456789");

		userAccount = userAccountService.create("CUSTOMER");
		
		userAccount.setUsername("nuevoCustomer");
		userAccount.setPassword("nuevoCustomer");
		
		customer.setUserAccount(userAccount);
		
		customerService.saveFromOtherService(customer);
		
		// Checks results
//		authenticate("admin");
//		Assert.isTrue(customerService.findAll().contains(customerRegistered), "El customer nuevo registrado no se encuentra entre los customers registrados en el sistema."); // First check
//		unauthenticate();
//		
//		authenticate("customer1");
//		
//		authenticatedCustomer = actorService.findByPrincipal();
//		
//		Assert.notNull(authenticatedCustomer, "No se ha podido loguear al customer que se acaba de registrar."); // Second check
		
		unauthenticate();

	}
	
	/**
	 * Acme-Barter - Level C - 12.5.1
	 * The total number of users who have registered to the system.
	 */
	@Test 
	public void testTotalUsers() {
		// Declare variables
		int totalUsersInTest;
		int result;
		
		// Load objects to test
		authenticate("admin");
		totalUsersInTest = customerService.findAll().size();
		
		// Checks basic requirements
				
		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");		
		
		// Checks results	
	}
	
	/**
	 * Acme-Barter - Level C - 12.5.5
	 * The users who have not created any barter during the last month.
	 */
	@Test 
	public void testUsersNotCreateRecentBarter() {
		// Declare variables
		Collection<User> inTest;
		Collection<User> result;
		Calendar limitCalendar;
		Date limitDate;
		
		// Load objects to test
		authenticate("admin");
		
		inTest = customerService.findAll();
		limitCalendar = Calendar.getInstance();
		limitCalendar.add(Calendar.MONTH, -1);
		limitDate = limitCalendar.getTime();
		
		for(Barter b:barterService.findAll()){
			boolean isRecent;
			
			isRecent = b.getRegisterMoment().after(limitDate);
			
			if(inTest.contains(b.getUser()) && isRecent){
				inTest.remove(b.getUser());
			}
		}
		
		// Checks basic requirements

		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");
		
				// En la variable inTest están los usuarios que debería devolver la query
		
		
		// Checks results	
	}
	
	/**
	 * Acme-Barter - Level B - 4.2.3
	 * The users who have registered more barters.
	 */
	@Test 
	public void testUsersMoreBarters() {
		// Declare variables
		Collection<User> inTest;
		Collection<User> result;
		Map<User, Integer> userBarters;
		int maxBarters;
		
		// Load objects to test
		authenticate("admin");
		
		inTest = customerService.findAll();
		
		userBarters = new HashMap<User, Integer>();
		maxBarters = 0;
		
		for(Barter b:barterService.findAll()){
			int value = 1;
			if(userBarters.containsKey(b.getUser()))
				value = userBarters.get(b.getUser()) + 1;

			userBarters.put(b.getUser(), value);
			
			if(value > maxBarters){
				inTest.clear();
				maxBarters = value;
			}
			if(value >= maxBarters)
				inTest.add(b.getUser());
		}
		
		// Checks basic requirements

		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");
		
				// En la variable inTest están los usuarios que debería devolver la query
		
		
		// Checks results	
	}
	
	/**
	 * Acme-Barter - Level B - 4.2.4
	 * The users who have more cancelled barters.
	 */
	@Test 
	public void testUsersMoreCancelledBarters() {
		// Declare variables
		Collection<User> inTest;
		Collection<User> result;
		Map<User, Integer> userBarters;
		int maxBarters;
		
		// Load objects to test
		authenticate("admin");
		
		inTest = customerService.findAll();
		
		userBarters = new HashMap<User, Integer>();
		maxBarters = 0;

		for (Barter b : barterService.findAll()) {
			if (b.isCancelled()) {
				int value = 1;
				if (userBarters.containsKey(b.getUser()))
					value = userBarters.get(b.getUser()) + 1;

				userBarters.put(b.getUser(), value);

				if (value > maxBarters) {
					inTest.clear();
					maxBarters = value;
				}
				if (value >= maxBarters)
					inTest.add(b.getUser());
			}
		}

		// Checks basic requirements

		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");
		
				// En la variable inTest están los usuarios que debería devolver la query
		
		
		// Checks results	
	}
	
	/**
	 * Acme-Barter - Level B - 4.2.5
	 * The users who have more matches.
	 */
	@Test 
	public void testUsersMoreMatches() {
		// Declare variables
		Collection<User> inTest;
		Collection<User> result;
		Map<User, Integer> userBarters;
		int maxBarters;
		
		// Load objects to test
		authenticate("admin");
		
		inTest = customerService.findAll();
		
		userBarters = new HashMap<User, Integer>();
		maxBarters = 0;
		
		for (Match b : matchService.findAll()) {
			if (!b.getCancelled() && b.getAuditor() != null) {
				// createMatch
				int value = 1;
				if (userBarters.containsKey(b.getCreatorBarter().getUser()))
					value = userBarters.get(b.getCreatorBarter().getUser()) + 1;

				userBarters.put(b.getCreatorBarter().getUser(), value);

				if (value > maxBarters) {
					inTest.clear();
					maxBarters = value;
				}
				if (value >= maxBarters)
					inTest.add(b.getCreatorBarter().getUser());
				
				//receivedMatch
				if(b.getReceiverBarter() != null){
					value = 1;
					if (userBarters.containsKey(b.getReceiverBarter().getUser()))
						value = userBarters.get(b.getReceiverBarter().getUser()) + 1;

					userBarters.put(b.getReceiverBarter().getUser(), value);

					if (value > maxBarters) {
						inTest.clear();
						maxBarters = value;
					}
					if (value >= maxBarters)
						inTest.add(b.getReceiverBarter().getUser());
									
				}
			}
		}
		
		// Checks basic requirements

		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");
		
				// En la variable inTest están los usuarios que debería devolver la query
		
		
		// Checks results	
	}
	
	/**
	 * Acme-Barter - Level A - 3.2.2
	 * The users who have got more matches audited.

	 */
	@Test 
	public void testUsersMoreMatchesAudited() {
		// Declare variables
		Collection<User> inTest;
		Collection<User> result;
		Map<User, Integer> userBarters;
		int maxBarters;
		
		// Load objects to test
		authenticate("admin");
		
		inTest = customerService.findAll();
		
		userBarters = new HashMap<User, Integer>();
		maxBarters = 0;
		
		for (Match b : matchService.findAll()) {
			if (!b.getCancelled()) {
				// createMatch
				int value = 1;
				if (userBarters.containsKey(b.getCreatorBarter().getUser()))
					value = userBarters.get(b.getCreatorBarter().getUser()) + 1;

				userBarters.put(b.getCreatorBarter().getUser(), value);

				if (value > maxBarters) {
					inTest.clear();
					maxBarters = value;
				}
				if (value >= maxBarters)
					inTest.add(b.getCreatorBarter().getUser());
				
				//receivedMatch
				if(b.getReceiverBarter() != null){
					value = 1;
					if (userBarters.containsKey(b.getReceiverBarter().getUser()))
						value = userBarters.get(b.getReceiverBarter().getUser()) + 1;

					userBarters.put(b.getReceiverBarter().getUser(), value);

					if (value > maxBarters) {
						inTest.clear();
						maxBarters = value;
					}
					if (value >= maxBarters)
						inTest.add(b.getReceiverBarter().getUser());
									
				}
			}
		}
		
		// Checks basic requirements

		// Execution of test
		Assert.notNull(null, "Test inacabado ya que no se sabe como se implementará");
		
				// En la variable inTest están los usuarios que debería devolver la query
		
		
		// Checks results	
	}
	
}
