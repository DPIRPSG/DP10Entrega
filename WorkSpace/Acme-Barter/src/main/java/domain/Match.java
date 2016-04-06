package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(name="MatchTable")
public class Match extends DomainEntity{

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private Date creationMoment;
	private Date offerSignsDate;
	private Date requestSignsDate;
	private boolean cancelled;
	private String report;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}
	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getOfferSignsDate() {
		return offerSignsDate;
	}
	public void setOfferSignsDate(Date offerSignsDate) {
		this.offerSignsDate = offerSignsDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getRequestSignsDate() {
		return requestSignsDate;
	}
	public void setRequestSignsDate(Date requestSignsDate) {
		this.requestSignsDate = requestSignsDate;
	}
	
	public boolean getCancelled() {
		return cancelled;
	}
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@NotBlank
	@NotNull
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}

	// Relationships ----------------------------------------------------------
	private LegalText legalText;
	private Auditor auditor;
	private Collection<Barter> barters;
	private User receiver;
	private User creator;
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public LegalText getLegalText() {
		return legalText;
	}
	public void setLegalText(LegalText legalText) {
		this.legalText = legalText;
	}
	
	@Valid
	@ManyToOne(optional=true)
	public Auditor getAuditor() {
		return auditor;
	}
	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
	
	@Valid
	@NotNull
	@Size(min=2, max=2)
	@OneToMany(mappedBy="match")
	public Collection<Barter> getBarters() {
		return barters;
	}
	public void setBarters(Collection<Barter> barters) {
		this.barters = barters;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
}
