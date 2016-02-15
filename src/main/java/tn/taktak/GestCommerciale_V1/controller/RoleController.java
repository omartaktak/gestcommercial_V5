package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.Role;
import tn.taktak.GestCommerciale_V1.service.RoleService;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class RoleController implements Serializable  {

	@ManagedProperty("#{roleService}")
	private RoleService roleService;
    
	@ManagedProperty("#{roleServiceV1}")
	private static RoleService roleServiceV1;
    
	
	private List<Role> listeRole;
	private Role role = new Role();
	
	@PostConstruct
	public void loadRepresentant() {
		listeRole= roleService.findAll();
		//DevisClient devis = new DevisClient();
	}
	
	public void save() {
		roleService.save(role);
	}
	
	public void clear()
	{
		role=new Role();
	}
	
	public static List<Role> listOfRoleByUser(String user_id)
	{
		return roleServiceV1.listOfRoleByUser(user_id);
	}
	
	public List<Role> listOfRoleByUser_V1(String user_id)
	{
		return roleService.listOfRoleByUser_V1(user_id);
	}
	
	public void remove(Role r)
	{
		roleService.remove(r);
	}
}
