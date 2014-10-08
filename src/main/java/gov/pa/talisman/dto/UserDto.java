package gov.pa.talisman.dto;

/**
 * Specialized user data for REST layer.
 * 
 */
public class UserDto {

	private Integer id;
	private String fullname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}
