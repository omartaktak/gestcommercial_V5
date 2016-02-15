package tn.taktak.GestCommerciale_V1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import tn.taktak.GestCommerciale_V1.entity.Representant;
import tn.taktak.GestCommerciale_V1.entity.Role;
import tn.taktak.GestCommerciale_V1.service.RepresentantService;
import tn.taktak.GestCommerciale_V1.service.RoleService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired 
	private RepresentantService representantService; 
	
	@Autowired
	private RoleService roleService;
	
	
	List<Role> userRoles=new ArrayList<Role>();
	static List<Role> userlistRoles=null;
	//@Override 
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException 
	{
		Representant user = representantService.findUserByLogin(userName); 
		if(user == null)
		{
			throw new UsernameNotFoundException("UserName "+userName+" not found");
		}
		userRoles=roleService.listOfRoleByUser(user.getCrepresentant());
		if(userRoles!=null)
			userlistRoles=userRoles;
		
		return new SecurityUser(user); 
	} 
}
