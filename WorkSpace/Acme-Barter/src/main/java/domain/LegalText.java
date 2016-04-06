package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public abstract class LegalText extends DomainEntity{

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String text;
	
	@NotBlank
	@NotNull
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<MatchEntity> matches;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy = "legalText")
	public Collection<MatchEntity> getMatches() {
		return matches;
	}
	public void setMatches(Collection<MatchEntity> matches) {
		this.matches = matches;
	}

}
