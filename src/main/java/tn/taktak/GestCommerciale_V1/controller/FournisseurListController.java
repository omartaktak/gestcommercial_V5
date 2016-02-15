package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.Fournisseur;
import tn.taktak.GestCommerciale_V1.service.FournisseurService;

@ManagedBean
@Getter
@Setter
@ViewScoped
//@RequestScoped
public class FournisseurListController implements Serializable {

	@ManagedProperty("#{fournisseurService}")
	private FournisseurService fournisseurService;
    private List<Fournisseur> fournisseurs;
	private Fournisseur fournisseur = new Fournisseur();
	public static List<Fournisseur> listfournisseurs=null;

	public static String id=null;
	
	@PostConstruct
	public void loadFournisseur() {
		if(listfournisseurs!=null)
			fournisseurs=listfournisseurs;
		else
		fournisseurs = fournisseurService.findAll();
	}
	
	public void selectedFournisseur(Fournisseur f) 
	{
		setFournisseur(f);
		id=f.getCfournisseur();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('fournDialog').show();");
	}
	public void save() {
		boolean champs_vide=false;
	
		if(fournisseur.getCfournisseur().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(fournisseur.getCregimeTva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le régime tva ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(fournisseur.getRaisonSociale().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le Raison social ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(champs_vide==false)
		{
			fournisseurService.save(fournisseur);
			fournisseur=new Fournisseur();
			fournisseurs = fournisseurService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fournisseur Enregistré!", null));
		}
	}
	
	public void update()
	{
		boolean champs_vide=false;
		fournisseur.setCfournisseur(id);
		if(fournisseur.getCfournisseur().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(fournisseur.getCregimeTva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le régime tva ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(fournisseur.getRaisonSociale().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le Raison social ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(champs_vide==false)
		{
			fournisseurService.save(fournisseur);
			fournisseur=new Fournisseur();
			fournisseurs = fournisseurService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fournisseur mis à jour!", null));
		}
	}
	
	public void remove(Fournisseur fournisseur) {
		
		fournisseurService.remove(fournisseur);
		fournisseurs = fournisseurService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fournisseur Supprimé!", null));
	}
	
	public void clear()
	{
		fournisseur=new Fournisseur();
	}
	
	public String redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/ListeFournisseur.xhtml"));
        try {

            extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null;
 
    }
	
	public void filterFournisseur()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formFourn:rechercheFourn");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			
			fournisseurs=fournisseurService.filterFournisseur(recherche);
			listfournisseurs=fournisseurs;
			context.update("formFourn:fournTable");
		}
		else
		{
			fournisseurs=fournisseurService.findAll();
			listfournisseurs=fournisseurs;
		}
		 
	}
	
	public  Fournisseur findFournisseurById(String id)
	{
		Fournisseur art= fournisseurService.findFournisseurById(id);
		return art;
	}
}
