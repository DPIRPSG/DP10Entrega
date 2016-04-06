package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "name"), @Index(columnList = "surname") })
public class Auditor extends Actor {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Match> matches;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "auditor")
	public Collection<Match> getMatches() {
		return matches;
	}
	public void setMatches(Collection<Match> matches) {
		this.matches = matches;
	}

}
