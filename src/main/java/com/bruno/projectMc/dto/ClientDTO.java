package com.bruno.projectMc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.bruno.projectMc.domain.Client;
import com.bruno.projectMc.services.validation.ClientUpdate;

@ClientUpdate
public class ClientDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=2, max=120, message="O tamanho deve ser entre 2 e 120 caracteres")
	private String name;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email invalido")
	private String email;
	public ClientDTO() {
		super();
	}
	
	public ClientDTO(Client obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
}
