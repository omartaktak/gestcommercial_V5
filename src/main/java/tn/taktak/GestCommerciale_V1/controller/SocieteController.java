package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import tn.taktak.GestCommerciale_V1.entity.Societe;
import tn.taktak.GestCommerciale_V1.service.SocieteService;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class SocieteController implements Serializable  {

	@ManagedProperty("#{societeService}")
	private SocieteService societeService;
    private List<Societe> listeSociete;
	private Societe societe = new Societe();
	
	public static boolean modif=false;
	public static String id=null;
	
	@PostConstruct
	public void loadSociete() {
		listeSociete= societeService.findAll();
		if(listeSociete.isEmpty()==false)
		{
			setSociete(listeSociete.get(0));
			modif=true;
		}
	}
	
	public void save() {
		if(modif==false)
		{
			societeService.save(societe);
			societe=new Societe(); 
			listeSociete= societeService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Societe Enregistré!", null));
		}
		else
			update();
	}
	
	public void remove(Societe rep) {
		societeService.remove(rep);
		listeSociete= societeService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Societe Supprimé!", null));
	}
	
	public void clear()
	{
		societe=new Societe();
	}
	
	public void selectedSociete(Societe art) 
	{
		setSociete(art);
		id=art.getMatFiscale();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('articleDialog').show();");
	}
	
	public void update()
	{
		boolean champs_vide=false;
		//societe.setMatFiscale(id);
		if(societe.getMatFiscale().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La mat.fiscale ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(societe.getRaisonSociale().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le raison social ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(societe.getCregimeTva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le régime TVA ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(societe.getNregistreCommerce().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le registre de commerce ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(societe.getPrixTimbre().equals(BigDecimal.ZERO))
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le prix du timbre ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(societe.getNcd().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le NCD ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(champs_vide==false)
		{
			societeService.save(societe);
			//societe=new Societe();
			listeSociete = societeService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Société Mis à jour!", null));
		}
	}
}
