package mms.google.auth;

public class AccountData {
	private String token;
	private String email;
	private String id;
	private String displayName;

	private String givenName;
	private String familyName;
	
	public AccountData(String token, String email, String id, String displayName, String givenName, String familyName) {
		super();
		this.token = token;
		this.email = email;
		this.id = id;
		this.displayName = displayName;
		this.givenName = givenName;
		this.familyName = familyName;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
}
