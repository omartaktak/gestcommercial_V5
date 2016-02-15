package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import tn.taktak.GestCommerciale_V1.entity.Representant;
import tn.taktak.GestCommerciale_V1.entity.Role;
import tn.taktak.GestCommerciale_V1.entity.UsersAndRoles;
import tn.taktak.GestCommerciale_V1.service.UsersAndRolesService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class UsersAndRolesController implements Serializable   {

	@ManagedProperty("#{usersAndRolesService}")
	private UsersAndRolesService usersAndRolesService;
	
    private List<UsersAndRoles> listusersandRole;
	private UsersAndRoles role = new UsersAndRoles();
	
	@PostConstruct
	public void loadRepresentant() {
		listusersandRole= usersAndRolesService.findAll();
	}
	
	public void save() {
		usersAndRolesService.save(role);
	}
	
	public void clear()
	{
		role=new UsersAndRoles();
	}
	
	public void remove(UsersAndRoles r)
	{
		usersAndRolesService.remove(r);
	}
	
	public List<Role> listOfRoleByUser(String user_id)
	{
		return usersAndRolesService.listOfRoleByUser(user_id);
	}
	
	public List<Role> listOfRoleByUser_V1(String user_id)
	{
		return usersAndRolesService.listOfRoleByUser_V1(user_id);
	}
	
	public List<Representant> listOfUserByRole(int role_id)
	{
		return usersAndRolesService.listOfUserByRole(role_id);
	}
}
