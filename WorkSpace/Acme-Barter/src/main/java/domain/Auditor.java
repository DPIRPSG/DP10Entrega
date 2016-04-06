package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Auditor extends Actor{

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	private Collection<MatchEntity> matches;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	public Collection<MatchEntity> getMatches() {
		return matches;
	}
	public void setMatches(Collection<MatchEntity> matches) {
		this.matches = matches;
	}

}
