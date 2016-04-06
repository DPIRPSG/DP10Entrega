package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;
import domain.SocialIdentity;

import repositories.SocialIdentityRepository;

@Service
@Transactional
public class SocialIdentityService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private SocialIdentityRepository socialIdentityRepository;
	
	//Supporting services ----------------------------------------------------
	
	@Autowired
	private CustomerService customerService;
	
	//Constructors -----------------------------------------------------------

	public SocialIdentityService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------
	
	/**
	 * Almacena en la base de datos el cambio
	 */
	// req: 10.1
	public void save(SocialIdentity socialIdentity){
		Assert.notNull(socialIdentity);
		
		User customer;
		Collection<SocialIdentity> userSocialIdentities;
		
		customer = customerService.findByPrincipal();
		userSocialIdentities = customer.getSocialIdentities();		
		socialIdentity = socialIdentityRepository.save(socialIdentity);
		
		userSocialIdentities.add(socialIdentity);
		
		customer.setSocialIdentities(userSocialIdentities);
		customerService.save(customer);
	}
	
	public Collection<SocialIdentity> findByPrincipal(){
		Collection<SocialIdentity> result;
		User custo;
		
		custo = customerService.findByPrincipal();
		result = custo.getSocialIdentities();
		
		return result;
	}
	
//	public void delete(){
//		User customer;
//		SocialIdentity socialIdentity;
//		
//		customer = customerService.findByPrincipal();
//		socialIdentity = customer.getSocialIdentity();
//		customer.setSocialIdentity(null);
//
//		socialIdentityRepository.delete(socialIdentity);
//		customerService.save(customer);
//	}
	
	//Other business methods -------------------------------------------------
	
//	public SocialIdentity findByPrincipalOrCreate(){
//		SocialIdentity result;
//		
//		result = this.findByPrincipal();
//		if(result == null)
//			result = new SocialIdentity();
//		
//		return result;
//	}


}
