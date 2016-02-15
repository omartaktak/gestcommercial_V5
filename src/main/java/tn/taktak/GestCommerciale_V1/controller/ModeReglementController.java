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

import tn.taktak.GestCommerciale_V1.entity.ModeReglement;
import tn.taktak.GestCommerciale_V1.service.ModeReglementService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class ModeReglementController implements Serializable {

	@ManagedProperty("#{modeReglementService}")
	private ModeReglementService modeReglementService;
	
	private List<ModeReglement> modeReglements;
	private ModeReglement modereglement = new ModeReglement();
	
	public static List<ModeReglement> listmode=null;
	public static String id=null;
	

	@PostConstruct
	public void loadModeReglement() {
		modeReglements = modeReglementService.findAll();
	}
	
	public List<ModeReglement> findAll()
	{
		return modeReglementService.findAll();
	}
	
	public void save() {
		modeReglementService.save(modereglement);
		modereglement=new ModeReglement();
		modeReglements = modeReglementService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mode Reg Enregistré!", null));
	}
	
	public void remove(ModeReglement mr) {
		modeReglementService.remove(mr);
		modeReglements = modeReglementService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mode Reg Supprimé!", null));
	}
	
	public void clear()
	{
		modereglement=new ModeReglement();
	}
	
	public String redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();											  
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/ListeModeReg.xhtml"));
        try {
        	extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null;
	}
	
	public void filterModeReg()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formArticle:rechercheArticle");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			modeReglements=modeReglementService.filterModeReglement(recherche);
			listmode=modeReglements;
			context.update("formArticle:articleTable");
		}
		else
		{
			modeReglements=modeReglementService.findAll();
			listmode=modeReglements;
		}
	}
	
	public void selectedmodeReglement(ModeReglement art) 
	{
		setModereglement(art);
		id=art.getCmodeReglement();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('articleDialog').show();");
	}
	
	public void update()
	{
		boolean champs_vide=false;
		modereglement.setCmodeReglement(id);
		if(modereglement.getCmodeReglement().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(modereglement.getDesModeReglement().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La désignation ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(champs_vide==false)
		{
			modeReglementService.save(modereglement);
			modereglement=new ModeReglement();
			modeReglements = modeReglementService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mode Reg Mis à jour!", null));
		}
	}
}
