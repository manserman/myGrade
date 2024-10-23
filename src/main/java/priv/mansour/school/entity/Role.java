package priv.mansour.school.entity;
/**
 *  @author mohamadou.mansour
 *  enums of users Roles
 */
public enum Role {

	ADMIN("ADMIN"), TEACHER("TEACHER"), STUDENT("STUDENT");

	private final String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
