package net.marioosh.gwt.shared.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
public class User extends AbstractEntity implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_user")
	private Long id;

	@NotEmpty
	@Size(min=3)
	private String login;

	private String password;

	private String firstname;

	private String lastname;

	@Valid
	@NumberFormat(style=Style.NUMBER, pattern="### ### ###")
	private String telephone;

	@Valid
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date date;

	@NotEmpty
	private String email;

	public User() {}

	public User(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "{ " +
				"id: " + id + ", " +	
				"login: " + login + "," +
				"password: " + password + ", " +
				"firstname: " + firstname + ", " +
				"lastname: " + lastname + ", " +
				"email: " + email + ", " +
				"date: " + date + ", " +
				"telephone: " + telephone +
				" }";
	}
}
