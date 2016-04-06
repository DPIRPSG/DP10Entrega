package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
	private SocialIdentity socialIdentity;
		
	@Valid
	@OneToOne(optional = true)
	public SocialIdentity getSocialIdentity() {
		return socialIdentity;
	}
	public void setSocialIdentity(SocialIdentity socialIdentity) {
		this.socialIdentity = socialIdentity;
	}

}
