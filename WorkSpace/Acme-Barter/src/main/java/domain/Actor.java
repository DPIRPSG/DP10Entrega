package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;


@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity{

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------
	private String name;
	private String surname;
	private String phone;
	
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotBlank
	@NotNull
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<Folder> messageBoxes;
	private Collection<Message> sent;
	private Collection<Message> received;
	private UserAccount userAccount;
	
	@NotNull
	@Valid
	@OneToMany(mappedBy = "actor")
	@Size(min = 4)
	public Collection<Folder> getMessageBoxes() {
		return messageBoxes;
	}
	public void setMessageBoxes(Collection<Folder> folders) {
		this.messageBoxes = folders;
	}
	
	@Valid
	@OneToMany(mappedBy = "sender")
	@NotNull
	public Collection<Message> getSent() {
		return sent;
	}
	public void setSent(Collection<Message> sent) {
		this.sent = sent;
	}
	
	@Valid
	@NotNull
	@ManyToMany(mappedBy = "recipients")
	public Collection<Message> getReceived() {
		return received;
	}
	public void setReceived(Collection<Message> received) {
		this.received = received;
	}
	
	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)	
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
