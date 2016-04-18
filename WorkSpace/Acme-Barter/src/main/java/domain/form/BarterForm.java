package domain.form;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class BarterForm {
	private String title;
	private String offeredName;
	private String offeredDescription;
	private String offeredPictures;
	private String requestedName;
	private String requestedDescription;
	private String requestedPictures;
	
	@NotBlank
	@NotNull
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	@NotNull
	public String getOfferedName() {
		return offeredName;
	}
	public void setOfferedName(String offeredName) {
		this.offeredName = offeredName;
	}
	
	@NotBlank
	@NotNull
	public String getOfferedDescription() {
		return offeredDescription;
	}
	public void setOfferedDescription(String offeredDescription) {
		this.offeredDescription = offeredDescription;
	}
	
	@NotNull
	@ElementCollection
	public String getOfferedPictures() {
		return offeredPictures;
	}
	public void setOfferedPictures(String offeredPictures) {
		this.offeredPictures = offeredPictures;
	}
	
	@NotBlank
	@NotNull
	public String getRequestedName() {
		return requestedName;
	}
	public void setRequestedName(String requestedName) {
		this.requestedName = requestedName;
	}
	
	@NotBlank
	@NotNull
	public String getRequestedDescription() {
		return requestedDescription;
	}
	public void setRequestedDescription(String requestedDescription) {
		this.requestedDescription = requestedDescription;
	}
	
	@NotNull
	@ElementCollection
	public String getRequestedPictures() {
		return requestedPictures;
	}
	public void setRequestedPictures(String requestedPictures) {
		this.requestedPictures = requestedPictures;
	}
}
