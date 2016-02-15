package tn.taktak.GestCommerciale_V1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.taktak.GestCommerciale_V1.entity.Role;
import tn.taktak.GestCommerciale_V1.repository.RoleRepository;


@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public List<Role> findAll()
	{
	   return	roleRepository.findAll();
	}
	
	public void save(Role rep)
	{
		roleRepository.save(rep);
	}
	
	public void remove(Role rep)
	{
		roleRepository.delete(rep);
	}
	
	public List<Role> listOfRoleByUser(String user_id)
	{
		return roleRepository.listOfRoleByUser(user_id);
	}
	
	public List<Role> listOfRoleByUser_V1(String user_id)
	{
		return roleRepository.listOfRoleByUser_V1(user_id);
	}
	
//	public List<Role> filterRepresentant(String t)
//	{
//		return roleRepository.filterRepresentant(t);
//	}
}
