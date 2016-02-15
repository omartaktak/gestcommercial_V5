package tn.taktak.GestCommerciale_V1.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import tn.taktak.GestCommerciale_V1.SecurityUser;
import tn.taktak.GestCommerciale_V1.entity.Representant;
import tn.taktak.GestCommerciale_V1.service.RepresentantService;
import lombok.Getter;
import lombok.Setter;


@ManagedBean
@Getter
@Setter
@ViewScoped
public class SecurityUserController {

	//SecurityUser secuser=new SecurityUser(); 
	
	@Autowired 
	private  RepresentantService representantService;
	
//	private static UserService userService;
//	
//	@Autowired
//	public void setUserService(UserService userService) {
//		UserController.userService = userService;
//	}
	
	public  Representant getCurrentUser()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    if (principal instanceof Representant) 
	    {
	    	//String userName = ((Representant) principal).getUsername();
	    	//Representant user = representantService.findUserByLogin(userName); 
	    	//User loginUser = userService.findUserByEmail(email);
	    	
	    	return (Representant) principal;
	    	//return new Representant(principal);
	    }

	    return null;
	}
}
