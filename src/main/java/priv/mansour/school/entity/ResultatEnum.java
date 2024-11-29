package priv.mansour.school.entity;

public enum ResultatEnum {
	ACQUIRED("ACQUIRED"), IN_PROCESS("IN PROCESS"), FAILED("FAILED");

	private final String value;

	ResultatEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}


}
