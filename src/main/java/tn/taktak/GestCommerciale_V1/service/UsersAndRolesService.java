package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Representant;
import tn.taktak.GestCommerciale_V1.entity.Role;
import tn.taktak.GestCommerciale_V1.entity.UsersAndRoles;
import tn.taktak.GestCommerciale_V1.repository.UsersAndRolesRepository;

@Service
public class UsersAndRolesService {

	@Autowired
	private UsersAndRolesRepository usersAndRolesRepository;
	
	public List<UsersAndRoles> findAll()
	{
	   return	usersAndRolesRepository.findAll();
	}
	
	public void save(UsersAndRoles rep)
	{
		usersAndRolesRepository.save(rep);
	}
	
	public void remove(UsersAndRoles rep)
	{
		usersAndRolesRepository.delete(rep);
	}
	
	public List<Role> listOfRoleByUser(String user_id)
	{
		return usersAndRolesRepository.listOfRoleByUser(user_id);
	}
	
	public List<Role> listOfRoleByUser_V1(String user_id)
	{
		return usersAndRolesRepository.listOfRoleByUser_V1(user_id);
	}
	
	public List<Representant> listOfUserByRole(int role_id)
	{
		return usersAndRolesRepository.listOfUserByRole(role_id);
	}
	
//	public List<Role> filterRepresentant(String t)
//	{
//		return roleRepository.filterRepresentant(t);
//	}
}
