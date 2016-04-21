package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;
import domain.Message;
import domain.Auditor;


import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AuditorService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private AuditorRepository auditorRepository;
	
	//Supporting services ----------------------------------------------------

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	/*@Autowired
	private ShoppingCartService shoppingCartService;*/
	
	//Constructors -----------------------------------------------------------

	public AuditorService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------
	
	/** Devuelve trainer preparado para ser modificado. Necesita usar save para que persista en la base de datos
	 * 
	 */
	// req: 10.1
	public Auditor create(){
		Auditor result;
		UserAccount userAccount;

		result = new Auditor();
		
		userAccount = userAccountService.create("TRAINER");
		result.setUserAccount(userAccount);
		
		return result;
	}
	
	/**
	 * Almacena en la base de datos el cambio
	 */
	// req: 10.1
	public Auditor save(Auditor trainer){
		Assert.notNull(trainer);
		Assert.notNull(trainer.getUserAccount().getUsername());
		Assert.notNull(trainer.getUserAccount().getPassword());
		
		Auditor modify;
		
		boolean result = true;
		for(Authority a: trainer.getUserAccount().getAuthorities()){
			if(!a.getAuthority().equals("TRAINER")){
				result = false;
				break;
			}
		}
		Assert.isTrue(result, "A trainer can only be a authority.trainer");
		
		if(trainer.getId() == 0){
			Assert.isTrue(actorService.checkAuthority("ADMIN"), "trainer.create.permissionDenied");
			
			Collection<Folder> folders;
			Collection<Message> sent;
			Collection<Message> received;
//			Collection<ServiceEntity> services;
//			Collection<Activity> activities;
//			Collection<Comment> comments;
			UserAccount auth;
			
			//Encoding password
			auth = trainer.getUserAccount();
			auth = userAccountService.modifyPassword(auth);
			trainer.setUserAccount(auth);
			
			// Initialize folders
			folders = folderService.initializeSystemFolder(trainer);
			trainer.setMessageBoxes(folders);
			
			sent = new ArrayList<Message>();
			received = new ArrayList<Message>();
			trainer.setSent(sent);
			trainer.setReceived(received);
			
			// Initialize anothers
			
//			services = new ArrayList<ServiceEntity>();
//			activities = new ArrayList<Activity>();
//			comments = new ArrayList<Comment>();
//			trainer.setServices(services);
//			trainer.setActivities(activities);
//			trainer.setComments(comments);
//			trainer.setMadeComments(comments);

			
		}
		//modify = customerRepository.saveAndFlush(customer);
		modify = auditorRepository.save(trainer);		
		
		if(trainer.getId() == 0){
			Collection<Folder> folders;

			folders = folderService.initializeSystemFolder(modify);
			folderService.save(folders);
		}
		return modify;
	}
	
	public Collection<Auditor> findAll(){
		Collection<Auditor> result;
		
		result = auditorRepository.findAll();
		
		return result;
	}
	
	public Auditor findOne(int id){
		Auditor result;
		
		result = auditorRepository.findOne(id);
		
		return result;
	}
	

	//Other business methods -------------------------------------------------

	/**
	 * Devuelve el trainer que está realizando la operación
	 */
	//req: x
	public Auditor findByPrincipal(){
		Auditor result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = auditorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		
		return result;
	}
	
//	public void addService(ServiceEntity serv){
//		Trainer actTrainer;
//		Collection<ServiceEntity> services;
//		Collection<Trainer> trainers;
//		
//		actTrainer = this.findByPrincipal();
//		services = actTrainer.getServices();
//		
//		Assert.isTrue(!services.contains(serv), "You already have this service as specialised.");
//		
//		trainers = serv.getTrainers();
//
//		trainers.add(actTrainer);
//		serv.setTrainers(trainers);
//
//		services.add(serv);
//		actTrainer.setServices(services);
//
//		this.save(actTrainer);
//		
//		
//	}
	
//	public void removeService(ServiceEntity serv){
//		Trainer trainer;
//		
//		trainer = this.findByPrincipal();
//		
//		for(Activity activity : trainer.getActivities()) {
//			Assert.isTrue(activity.getService().getId() != serv.getId());
//		}
//		
//		Trainer actTrainer;
//		Collection<ServiceEntity> services;
//		Collection<Trainer> trainers;
//		
//		actTrainer = this.findByPrincipal();
//		services = actTrainer.getServices();
//		trainers = serv.getTrainers();
//		
//		trainers.remove(actTrainer);
//		serv.setTrainers(trainers);
//		
//		services.remove(serv);
//		actTrainer.setServices(services);
//		
//		this.save(actTrainer);
//		
//	}

//	public Collection<Trainer> findBySingleKeyword(String keyword) {
//		Assert.notNull(keyword);
//		Assert.isTrue(!keyword.isEmpty());
//		
//		Collection<Trainer> result;
//
//		result = trainerRepository.findBySingleKeyword(keyword);
//		
//		return result;
//	}
	

//	public Trainer findByCurriculumId(int curriculumId){
//		Assert.notNull(curriculumId);
//		
//		Trainer result;
//		
//		result = trainerRepository.findByCurriculumId(curriculumId);
//		
//		return result;
//	}
//
//	public Collection<Trainer> findAllByServiceId(int serviceId){
//		
//		Collection<Trainer> result;
//		
//		result = trainerRepository.findAllByServiceId(serviceId);
//		
//		return result;
//		
//	}
	
//	public Double ratioOfTrainerWithCurriculumUpToDate(){
//		
//		Double result;
//		Double numerator;
//		Double denominator;
//		Calendar limitDate;
//		Collection<Trainer> listOfTrainers;
//		
//		result = 0.0;
//		numerator = 0.0;
//		denominator = 0.0;
//		limitDate = Calendar.getInstance();
//		limitDate.set(limitDate.get(Calendar.YEAR)-1, limitDate.get(Calendar.MONTH), limitDate.get(Calendar.DAY_OF_MONTH));
//		listOfTrainers = findAll();
//		denominator = (double) listOfTrainers.size();
//		
//		for(Trainer t:listOfTrainers){
//			if(t.getCurriculum() != null){
//				Long trainerDate = t.getCurriculum().getUpdateMoment().getTime();
//				Long limit = limitDate.getTimeInMillis();
//				if(trainerDate > limit){
//					numerator = numerator + 1.0;
//				}
//			}
//		}
//		
//		result = numerator / denominator;
//		
//		return result;
//	}
	
	public void flush(){
		auditorRepository.flush();
	}

}
