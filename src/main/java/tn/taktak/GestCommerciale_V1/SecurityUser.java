package tn.taktak.GestCommerciale_V1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import tn.taktak.GestCommerciale_V1.entity.Representant;
import tn.taktak.GestCommerciale_V1.entity.Role;

public class SecurityUser extends Representant implements UserDetails {

	//private static final long serialVersionUID = 1L; 
	
	List<Role> userRoles=new ArrayList<Role>();
	public SecurityUser(Representant user) 
	{
		
		if(user != null) 
		{ //this.setId(user.getCrepresentant()); 
		  this.setCrepresentant(user.getCrepresentant());
		  this.setNom(user.getNom()); 
		  this.setEmail(user.getEmail()); 
		  this.setMotPasse(user.getMotPasse()); 
		  this.setLogin(user.getLogin()); 
		  
		  userRoles=CustomUserDetailsService.userlistRoles;
		  //this.userRoles= roleController.listOfRoleByUser(this.getCrepresentant());
		  //this.userRoles= usersAndRolesController.listOfRoleByUser_V1(this.getCrepresentant());
		 // this.userRoles= usersAndRolesController.listOfRoleByUser(this.getCrepresentant());
	      
		  //this.setRoles(user.getRoles()); 
		  }	
	} 
	
	public SecurityUser() {
		// TODO Auto-generated constructor stub
	}

	//@Override 
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); 
	    //userRoles = usersAndRolesController.listOfRoleByUser(this.getCrepresentant());
		if(userRoles != null) 
		{
			for (Role role : userRoles) 
			{ 
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName()); 
			  authorities.add(authority); 
			}
		} return authorities; 
	}

	public String getPassword() {
		
		return super.getMotPasse();
	}

	public String getUsername() {
		return super.getLogin();
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	} 
}
