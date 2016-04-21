package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;
import domain.Folder;
import domain.Message;


import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class UserService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private UserRepository userRepository;
	
	//Supporting services ----------------------------------------------------

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	//Constructors -----------------------------------------------------------

	public UserService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------
	
	/** Devuelve customer preparado para ser modificado. Necesita usar save para que persista en la base de datos
	 * 
	 */
	// req: 10.1
	public User create(){
		User result;
		UserAccount userAccount;

		result = new User();
		
		userAccount = userAccountService.create("CUSTOMER");
		result.setUserAccount(userAccount);
		
		return result;
	}
	
	/**
	 * Almacena en la base de datos el cambio
	 */
	// req: 10.1
	private User save(User customer){
		Assert.notNull(customer);
		Assert.notNull(customer.getUserAccount().getUsername());
		Assert.notNull(customer.getUserAccount().getPassword());
		
		User modify;
		
		boolean result = true;
		for(Authority a: customer.getUserAccount().getAuthorities()){
			if(!a.getAuthority().equals("USER")){
				result = false;
				break;
			}
		}
		Assert.isTrue(result, "A user can only be a authority.user");
		
		if(customer.getId() == 0){
			result = true && !actorService.checkAuthority("ADMIN");
			result = result && !actorService.checkAuthority("USER");
			Assert.isTrue(result, "user.create.permissionDenied");
			
			Collection<Folder> folders;
			Collection<Message> sent;
			Collection<Message> received;
			UserAccount auth;
			
			//Encoding password
			auth = customer.getUserAccount();
			auth = userAccountService.modifyPassword(auth);
			customer.setUserAccount(auth);
			
			// Initialize folders
			folders = folderService.initializeSystemFolder(customer);
			customer.setMessageBoxes(folders);
			
			sent = new ArrayList<Message>();
			received = new ArrayList<Message>();
			customer.setSent(sent);
			customer.setReceived(received);
			
			// Initialize anothers
			Collection<User> users;
			
			users = new ArrayList<User>();
			customer.setFollowed(users);
			
		}
		//modify = customerRepository.saveAndFlush(customer);
		modify = userRepository.save(customer);		
		
		if(customer.getId() == 0){
			Collection<Folder> folders;

			folders = folderService.initializeSystemFolder(modify);
			folderService.save(folders);
		}
		return modify;
	}
	
	/** Only for create or edit personal data
	 * 
	 * @param user
	 * @return
	 */
	public User saveFromEdit(User user){
		user = this.save(user);
		
		return user;
	}
	
	public User saveFromOtherService(User user){

		user = this.save(user);
		
		return user;
	}
	
	/**
	 * Lista los customers registrados
	 */
	// req: 12.5
	public Collection<User> findAll(){
//		Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an admin can list customers");
		
		Collection<User> result;
		
		result = userRepository.findAll();
		
		return result;
	}
	
	private User findOne(int id){
		User res;
		
		res = userRepository.findOne(id);
		
		return res;		
	}

	//Other business methods -------------------------------------------------

	/**
	 * Devuelve el customers que está realizando la operación
	 */
	//req: x
	public User findByPrincipal(){
		User result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = userRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		
		return result;
	}
	
	public void followOrUnfollowById(int userIdOtherUser){
		Assert.isTrue(actorService.checkAuthority("USER"));
		User user, friend;
		
		user = this.findByPrincipal();
		friend = this.findOne(userIdOtherUser);
		Assert.notNull(friend);
		Assert.notNull(user);
		Assert.isTrue(friend.getId()!= user.getId(), "user.followOrUnfollowById.yourself");
		
		if(user.getFollowed().contains(friend))
			user.removeFollowed(friend);
		else
			user.addFollowed(friend);
	}
	
	/**A los que sigo
	 * 
	 * @return
	 */
	public Collection<User> getFollowed(){
		Assert.isTrue(actorService.checkAuthority("USER"));
		
		Collection<User> res;
		
		res = this.findByPrincipal().getFollowed();
		
		return res;
	}
	
	/**Los que me siguen
	 * 
	 * @return
	 */
	public Collection<User> getFollowers(){
		Assert.isTrue(actorService.checkAuthority("USER"));
		
		Collection<User> res;
		User actUser;
		
		actUser = this.findByPrincipal();
		
		res = userRepository.getFollowers(actUser.getId());
		
		return res;
	}
	
	public Integer getTotalNumberOfUsersRegistered(){
		Integer result;
		
		result = userRepository.getTotalNumberOfUsersRegistered();
		
		return result;
	}
	
}
