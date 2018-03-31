package hello.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {
	
	private String id;

	private String firstName;
	
	private String lastName;

	public Customer() {
		this.id = UUID.randomUUID().toString();
		this.firstName = UUID.randomUUID().toString();
		this.lastName = UUID.randomUUID().toString();
	}

	public Customer(String firstName, String lastName) {
		this.id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Customer(String id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format(this.getClass().getSimpleName() +": [id=%s, firstName='%s', lastName='%s']", id, firstName, lastName);
	}
}
