package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.BarterRepository;
import domain.Barter;

@Service
@Transactional
public class BarterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BarterRepository barterRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BarterService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Barter findOne(int barterId) {
		Barter result;

		result = barterRepository.findOne(barterId);

		return result;
	}

	public Collection<Barter> findAll() {
		Collection<Barter> result;

		result = barterRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public void flush() {
		barterRepository.flush();
	}

	public Collection<Barter> findAllNotCancelled() {
		Collection<Barter> result;

		result = barterRepository.findAllNotCancelled();

		return result;
	}
}
