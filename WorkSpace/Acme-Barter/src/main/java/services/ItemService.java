package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;

@Service
@Transactional
public class ItemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository itemRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public ItemService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Item findOne(int itemId) {
		Item result;

		result = itemRepository.findOne(itemId);

		return result;
	}
	
	/**
	 * Needed by BarterServiceTest
	 * @return
	 */
	public Collection<Item> findAll(){
		Assert.isTrue(actorService.checkAuthority("ADMIN"));
		Collection<Item> result;
		
		result = itemRepository.findAll();
		
		return result;
	}


	// Other business methods -------------------------------------------------

	public void flush() {
		itemRepository.flush();
	}
}
