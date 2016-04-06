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

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "name"), @Index(columnList = "surname") })
public class Auditor extends Actor {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	private String picture;

	@URL
	@Valid
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

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
