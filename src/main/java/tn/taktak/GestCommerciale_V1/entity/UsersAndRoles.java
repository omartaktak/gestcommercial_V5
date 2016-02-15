package tn.taktak.GestCommerciale_V1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UsersAndRoles {

	@Id
	private int id;
	private String user_id;
	private int  role_id;
	
	public UsersAndRoles(int id, String user_id, int role_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.role_id = role_id;
	}
	
	public UsersAndRoles() {
	
	}
}
