package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}

	// Relationships ----------------------------------------------------------
	private LegalText legalText;
	private Auditor auditor;
	private Barter creatorBarter;
	private Barter receiverBarter;
	
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
	@ManyToOne(optional=false)
	public Barter getCreatorBarter() {
		return creatorBarter;
	}
	public void setCreatorBarter(Barter creatorBarter) {
		this.creatorBarter = creatorBarter;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Barter getReceiverBarter() {
		return receiverBarter;
	}
	public void setReceiverBarter(Barter receiverBarter) {
		this.receiverBarter = receiverBarter;
	}
	
	
}
