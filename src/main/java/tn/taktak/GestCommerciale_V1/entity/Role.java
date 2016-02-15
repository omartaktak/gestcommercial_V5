package tn.taktak.GestCommerciale_V1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

	@Id
	private int id;
	private String roleName;
	
	public Role() {
	}

	public Role(int id, String role) {
		this.id = id;
		this.roleName= role;
	}
}