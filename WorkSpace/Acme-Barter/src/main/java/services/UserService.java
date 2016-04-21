package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Barter;
import domain.Match;
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
	
	@Autowired
	private BarterService barterService;
	
	@Autowired
	private MatchService matchService;
	
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
			result = result && !actorService.checkAuthority("AUDITOR");
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
	
	// DASHBOARD
	public Integer getTotalNumberOfUsersRegistered(){
		Integer result;
		
		result = userRepository.getTotalNumberOfUsersRegistered();
		
		return result;
	}
	
	public Collection<User> getUsersAbovePencentile90(){
		Collection<User> result = new HashSet<>();
		Collection<Barter> allBarters = new HashSet<>();
		Map<User,Integer> numberOfBarterPerUser = new HashMap<>();
		Integer max = 0;
		Double percentile90 = 0.0;
		
		allBarters = barterService.findAll();
		
		for(Barter b:allBarters){
			if(numberOfBarterPerUser.containsKey(b.getUser())){
				numberOfBarterPerUser.put(b.getUser(), numberOfBarterPerUser.get(b.getUser())+1);
			}else{
				numberOfBarterPerUser.put(b.getUser(), 1);			}
		}
		
		if(!numberOfBarterPerUser.values().equals(null)){
			for(Integer i:numberOfBarterPerUser.values()){
				if(max < i){
					max = i;
				}
			}
		}

		if(max != 0){
			percentile90 = max*0.9;
		}
		
		if(!numberOfBarterPerUser.keySet().isEmpty()){
			for(User u:numberOfBarterPerUser.keySet()){
				if(!numberOfBarterPerUser.get(u).equals(null) && numberOfBarterPerUser.get(u) >= percentile90){
					result.add(u);
				}
			}
		}

		return result;
	}
	
	@SuppressWarnings("deprecation")
	public Collection<User> getUsersWithNoBarterThisMonth(){
		Collection<User> result = new HashSet<>();
		Collection<Barter> allBarter = new HashSet<>();
		Collection<User> allUser = new HashSet<>();
		Map<User,Collection<Barter>> bartersPerUser = new HashMap<>();
		Date aMonthAgo = new Date();
		
		allBarter = barterService.findAll();
		if(aMonthAgo.getMonth() == 0){
			aMonthAgo.setMonth(11);
			aMonthAgo.setYear(aMonthAgo.getYear()-1);
		}else{
			aMonthAgo.setMonth(aMonthAgo.getMonth()-1);

		}
		if(!allBarter.isEmpty()){
			for(Barter b:allBarter){
				Collection<Barter> one = new HashSet<>();
				if(bartersPerUser.containsKey(b.getUser())){
					one = bartersPerUser.get(b.getUser());
					one.add(b);
					bartersPerUser.put(b.getUser(), one);
				}else{
					one.add(b);
					bartersPerUser.put(b.getUser(), one);				
				}
			}
		}
		
		allUser = bartersPerUser.keySet();
		result.addAll(allUser);
				
		if(!result.isEmpty()){
			for(User u:allUser){
				for(Barter b:bartersPerUser.get(u)){
					if(b.getRegisterMoment().after(aMonthAgo)){
						result.remove(u);
					}
				}
			}
		}
		
		return result;
	}
	
	public Integer minumumNumberBarterPerUser(){
		Integer result = Integer.MAX_VALUE;
		Collection<Barter> allBarter = new HashSet<>();
		Collection<User> allUser = new HashSet<>();
		Map<User, Collection<Barter>> barterPerUser = new HashMap<>();
		
		allBarter = barterService.findAll();
		allUser = findAll();
		
		if(!allBarter.isEmpty()){
			for(Barter b:allBarter){
				Collection<Barter> one = new HashSet<>();
				if(barterPerUser.containsKey(b.getUser())){
					one = barterPerUser.get(b.getUser());
					one.add(b);
					barterPerUser.put(b.getUser(), one);
				}else{
					one.add(b);
					barterPerUser.put(b.getUser(), one);				
				}
			}
		}
		
		for(User u:barterPerUser.keySet()){
			if(barterPerUser.get(u).size() < result){
				result = barterPerUser.get(u).size();
			}
		}
		
		if(!barterPerUser.keySet().isEmpty()){
			allUser.removeAll(barterPerUser.keySet());
			if(!allUser.isEmpty()){
				result = 0;
			}
		}
		
		if(result == Integer.MAX_VALUE){
			result = 0;
		}
		
		return result;
	}
	
	public Integer maximumNumberBarterPerUser(){
		Integer result = 0;
		Collection<Barter> allBarter = new HashSet<>();
		Map<User, Collection<Barter>> barterPerUser = new HashMap<>();
		
		allBarter = barterService.findAll();
		
		if(!allBarter.isEmpty()){
			for(Barter b:allBarter){
				Collection<Barter> one = new HashSet<>();
				if(barterPerUser.containsKey(b.getUser())){
					one = barterPerUser.get(b.getUser());
					one.add(b);
					barterPerUser.put(b.getUser(), one);
				}else{
					one.add(b);
					barterPerUser.put(b.getUser(), one);				
				}
			}
		}
		
		for(User u:barterPerUser.keySet()){
			if(barterPerUser.get(u).size() > result){
				result = barterPerUser.get(u).size();
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unused")
	public Double averageNumberBarterPerUser(){
		Double result = 0.0;
		Collection<Barter> allBarter = new HashSet<>();
		Map<User, Collection<Barter>> barterPerUser = new HashMap<>();
		Double numerator = 0.0;
		Double denominator = 1.0;
		
		allBarter = barterService.findAll();
		
		if(!allBarter.isEmpty()){
			for(Barter b:allBarter){
				Collection<Barter> one = new HashSet<>();
				if(barterPerUser.containsKey(b.getUser())){
					one = barterPerUser.get(b.getUser());
					one.add(b);
					barterPerUser.put(b.getUser(), one);
				}else{
					one.add(b);
					barterPerUser.put(b.getUser(), one);				
				}
			}
		}
		
		denominator = (double) barterPerUser.keySet().size();
		
		for(User u:barterPerUser.keySet()){
			numerator += barterPerUser.get(u).size();
		}
		
		if(denominator == null){
			denominator = 1.0;
		}
		
		if(numerator == null){
			numerator = 0.0;
		}

		result = numerator / denominator;
		
		return result;
		
	}
	
	public Collection<User> getUsersWithMoreBarters(){
		Collection<User> result;
		
		result = userRepository.getUsersWithMoreBarters();
		
		return result;
	}
	
	public Collection<User> getUsersWithMoreBartersCancelled(){
		Collection<User> result;
		
		result = userRepository.getUsersWithMoreBartersCancelled();
		
		return result;
	}
	
	public Collection<User> getUsersWithMoreMatches(){
		Collection<User> result = new HashSet<>();
		Collection<Match> allMatch = new HashSet<>();
		Map<User, Integer> numberOfMatchesPerUser = new HashMap<>();
		Integer max = 0;
		
		allMatch = matchService.findAll();
		
		for(Match m:allMatch){
			if(numberOfMatchesPerUser.containsKey(m.getCreatorBarter().getUser())){
				numberOfMatchesPerUser.put(m.getCreatorBarter().getUser(), numberOfMatchesPerUser.get(m.getCreatorBarter().getUser())+1);
			}
			if(numberOfMatchesPerUser.containsKey(m.getReceiverBarter().getUser())){
				numberOfMatchesPerUser.put(m.getReceiverBarter().getUser(), numberOfMatchesPerUser.get(m.getReceiverBarter().getUser())+1);	
			}
			if(!numberOfMatchesPerUser.containsKey(m.getCreatorBarter().getUser())){
				numberOfMatchesPerUser.put(m.getCreatorBarter().getUser(), 1);
			}
			if(!numberOfMatchesPerUser.containsKey(m.getReceiverBarter().getUser())){
				numberOfMatchesPerUser.put(m.getReceiverBarter().getUser(), 1);	
			}
		}
		
		if(!numberOfMatchesPerUser.values().equals(null)){
			for(Integer i:numberOfMatchesPerUser.values()){
				if(max < i){
					max = i;
				}
			}
		}
		
		if(!numberOfMatchesPerUser.keySet().isEmpty()){
			for(User u:numberOfMatchesPerUser.keySet()){
				if(!numberOfMatchesPerUser.get(u).equals(null) && numberOfMatchesPerUser.get(u) == max){
					result.add(u);
				}
			}
		}
		
		return result;
	}
	
	public Collection<User> getUsersWithMoreMatchesAudited(){
		Collection<User> result = new HashSet<>();
		Collection<Match> allMatch = new HashSet<>();
		Map<User, Integer> numberOfMatchesPerUser = new HashMap<>();
		Integer max = 0;
		
		allMatch = matchService.findAll();
		
		for(Match m:allMatch){
			if(!(m.getReport() == null)){
				if(numberOfMatchesPerUser.containsKey(m.getCreatorBarter().getUser())){
					numberOfMatchesPerUser.put(m.getCreatorBarter().getUser(), numberOfMatchesPerUser.get(m.getCreatorBarter().getUser())+1);
				}
				if(numberOfMatchesPerUser.containsKey(m.getReceiverBarter().getUser())){
					numberOfMatchesPerUser.put(m.getReceiverBarter().getUser(), numberOfMatchesPerUser.get(m.getReceiverBarter().getUser())+1);	
				}
				if(!numberOfMatchesPerUser.containsKey(m.getCreatorBarter().getUser())){
					numberOfMatchesPerUser.put(m.getCreatorBarter().getUser(), 1);
				}
				if(!numberOfMatchesPerUser.containsKey(m.getReceiverBarter().getUser())){
					numberOfMatchesPerUser.put(m.getReceiverBarter().getUser(), 1);	
				}
			}
		}
		
		if(!numberOfMatchesPerUser.values().equals(null)){
			for(Integer i:numberOfMatchesPerUser.values()){
				if(max < i){
					max = i;
				}
			}
		}
		
		if(!numberOfMatchesPerUser.keySet().isEmpty()){
			for(User u:numberOfMatchesPerUser.keySet()){
				if(!numberOfMatchesPerUser.get(u).equals(null) && numberOfMatchesPerUser.get(u) == max){
					result.add(u);
				}
			}
		}
		
		return result;
	}
}
