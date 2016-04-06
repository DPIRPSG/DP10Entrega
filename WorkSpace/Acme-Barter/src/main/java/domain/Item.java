package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Item extends DomainEntity{

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String name;
	private String description;
	private Collection<String> pictures;
	
	@NotBlank
	@NotNull
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull
	@ElementCollection
	public Collection<String> getPictures() {
		return pictures;
	}
	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Barter> x;
	private Collection<Barter> y;
	
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="offered")
	public Collection<Barter> getX() {
		return x;
	}
	public void setX(Collection<Barter> x) {
		this.x = x;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="requested")
	public Collection<Barter> getY() {
		return y;
	}
	public void setY(Collection<Barter> y) {
		this.y = y;
	}

}
