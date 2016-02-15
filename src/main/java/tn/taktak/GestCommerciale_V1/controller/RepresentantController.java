package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import tn.taktak.GestCommerciale_V1.entity.Representant;
import tn.taktak.GestCommerciale_V1.entity.Role;
import tn.taktak.GestCommerciale_V1.entity.Unite;
import tn.taktak.GestCommerciale_V1.service.RepresentantService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class RepresentantController implements Serializable {

	
	@ManagedProperty("#{representantService}")
	private RepresentantService representantService;
    private List<Representant> listeRepresentant;
	private Representant representant = new Representant();
	private String passConf;
	private List<Role> listRole;
	
	public static List<Representant> listrep=null;
	public static String id=null;
	
	
	@PostConstruct
	public void loadRepresentant() {
		listeRepresentant= representantService.findAll();
		//DevisClient devis = new DevisClient();
		
		//getCurrentUser();
	}
	
	
	public  void getCurrentUser()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    
		if(principal instanceof Representant)
		{
			setRepresentant((Representant) principal);
			//representant=(Representant) principal;
		}
//		else if (principal instanceof UserDetails) 
//	    {
//	    	String email = ((UserDetails) principal).getUsername();
////	    	User loginUser = userService.findUserByEmail(email);
////	    	return new SecurityUser(loginUser);
//	    }
//
//	    return null;
	}
	
	public void logout()
	{
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false); 
		 FacesContext ctx = FacesContext.getCurrentInstance();

	        ExternalContext extContext = ctx.getExternalContext();
	        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/login"));
	        try {
	        	extContext.redirect(url);
	        } catch (IOException ioe) {
	            throw new FacesException(ioe);

	        }
	}
	
	public boolean verifymp()
	{
		String mp=representant.getMotPasse();
		//if(passConf!=mp)
		if(passConf.equals(mp)==false)
			return false;
		return true ;
	}
	public void save() {
				
		if(verifymp())
		{
			representantService.save(representant);
			representant=new Representant(); 
			listeRepresentant = representantService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Representant Enregistré!", null));
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le mot de passe n'est pas conforme avec la confirmation!", null));
		}
	}
	
	public void remove(Representant rep) {
		representantService.remove(rep);
		listeRepresentant= representantService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Représentant Supprimé!", null));
	}
	
	public void clear()
	{
		representant=new Representant();
	}
	
	public String redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/ListeRepresentant.xhtml"));
        try {
        	extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null;
	}
	
	public void filterRepresentant()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formArticle:rechercheArticle");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			listeRepresentant=representantService.filterRepresentant(recherche);
			listrep=listeRepresentant;
			context.update("formArticle:articleTable");
		}
		else
		{
			listeRepresentant=representantService.findAll();
			listrep=listeRepresentant;
		}
	}
	
	public void selectedRepresentant(Representant art) 
	{
		setRepresentant(art);
		id=art.getCrepresentant();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('articleDialog').show();");
	}
	
	public void update()
	{
		boolean champs_vide=false;
		representant.setCrepresentant(id);
		if(representant.getCrepresentant().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(representant.getNom().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le nom ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(representant.getPrenom().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le prénom ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(champs_vide==false)
		{
			representantService.save(representant);
			representant=new Representant();
			listeRepresentant = representantService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Représentant Mis à jour!", null));
		}
	}
	
}
