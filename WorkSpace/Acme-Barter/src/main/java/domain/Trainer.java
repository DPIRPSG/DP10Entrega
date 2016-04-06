package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.Valid;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "name"), @Index(columnList = "surname") })
public class Trainer extends Actor {

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

}
