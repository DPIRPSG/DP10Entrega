package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
	private Collection<SocialIdentity> socialIdentities;
	private Barter barter;
	private Collection<User> followed;
	private Collection<Match> receivedMatches;
	private Collection<Match> createdMatches;
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}
	public void setSocialIdentities(Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	@Valid
	@ManyToOne(optional=true)
	public Barter getBarter() {
		return barter;
	}
	public void setBarter(Barter barter) {
		this.barter = barter;
	}
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<User> getFollowed() {
		return followed;
	}
	public void setFollowed(Collection<User> followed) {
		this.followed = followed;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="receiver")
	public Collection<Match> getReceivedMatches() {
		return receivedMatches;
	}
	public void setReceivedMatches(Collection<Match> receivedMatch) {
		this.receivedMatches = receivedMatch;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="creator")
	public Collection<Match> getCreatedMatches() {
		return createdMatches;
	}
	public void setCreatedMatches(Collection<Match> createdMatch) {
		this.createdMatches = createdMatch;
	}
	
	
	
	

}
