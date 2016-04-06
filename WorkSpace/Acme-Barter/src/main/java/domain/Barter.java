package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class Barter extends DomainEntity{

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String title;
	private boolean cancelled;
	private Date registerMoment;

	@NotBlank
	@NotNull
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getRegisterMoment() {
		return registerMoment;
	}
	public void setRegisterMoment(Date registerMoment) {
		this.registerMoment = registerMoment;
	}
	
	// Relationships ----------------------------------------------------------
	private User user;
	private Item offered;
	private Item requested;
	private Match match;
	private Barter relatedBarter;

	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Item getOffered() {
		return offered;
	}
	public void setOffered(Item offered) {
		this.offered = offered;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)	
	public Item getRequested() {
		return requested;
	}
	public void setRequested(Item requested) {
		this.requested = requested;
	}
	
	@Valid
	@ManyToOne(optional=true)
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	
	@Valid
	@OneToOne(optional=true)
	public Barter getRelatedBarter() {
		return relatedBarter;
	}
	public void setRelatedBarter(Barter relatedBarter) {
		this.relatedBarter = relatedBarter;
	}

}
