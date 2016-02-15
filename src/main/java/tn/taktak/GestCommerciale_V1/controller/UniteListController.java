package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.Unite;
import tn.taktak.GestCommerciale_V1.service.UniteService;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class UniteListController implements Serializable {

	@ManagedProperty("#{uniteService}")
	private UniteService uniteService;
	
	private List<Unite> unites;
	private Unite unite = new Unite();
	
	public static List<Unite> listunite=null;
	public static String id=null;
	
	@PostConstruct
	public void loadUnite() {
		unites = uniteService.findAll();
	}
	
	public List<String> findDesUnite(){
		return uniteService.findDesUnite();
	   
	}
	
	public void save() {
		uniteService.save(unite);
		unite=new Unite();
		unites = uniteService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unité Enregistré!", null));
	}
	
	public void remove(Unite unite) {
		uniteService.remove(unite);
		unites = uniteService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unité Supprimé!", null));
	}
	
	public void clear()
	{
		unite=new Unite();
	}
	
	public String redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/ListeUnite.xhtml"));
        try {
        	extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null;
	}
	
	public void filterUnite()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formArticle:rechercheArticle");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			unites=uniteService.filterUnite(recherche);
			listunite=unites;
			context.update("formArticle:articleTable");
		}
		else
		{
			unites=uniteService.findAll();
			listunite=unites;
		}
	}
	
	public void selectedUnite(Unite art) 
	{
		setUnite(art);
		id=art.getCunite();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('articleDialog').show();");
	}
	
	public void update()
	{
		boolean champs_vide=false;
		unite.setCunite(id);
		if(unite.getCunite().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(unite.getDesUnite().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La désignation ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(champs_vide==false)
		{
			uniteService.save(unite);
			unite=new Unite();
			unites = uniteService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Unité Mis à jour!", null));
		}
	}

}
