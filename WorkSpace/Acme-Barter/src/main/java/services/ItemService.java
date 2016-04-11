package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ItemRepository;
import domain.Item;

@Service
@Transactional
public class ItemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository itemRepository;

	// Supporting services ----------------------------------------------------

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


	// Other business methods -------------------------------------------------

	public void flush() {
		itemRepository.flush();
	}
}
