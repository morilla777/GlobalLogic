package com.globallogic.users.bean;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

	private String name;
	
	@NotEmpty(message= "El campo 'email' es obligatorio")
	@Length(message = "El largo máximo del campo 'email' es 25",max = 25)
	@Email(message = "El formato del campo 'email' es inválido")
	private String email;
	@Length(message = "El largo máximo del campo 'password' es 10",max = 10)
	@NotEmpty(message= "El campo 'password' es obligatorio")
	@Pattern(regexp = "(([A-Z]{1})([a-z]{1,7})([1-9]{2}))", message = "El formato del campo 'password' es inválido")
	private String password;
	private List<Phone> phones;
	
	public User() {
		phones = new ArrayList<>();
	}
	
	public void addPhone(Phone phone) {
		phones.add(phone);
	}

}
