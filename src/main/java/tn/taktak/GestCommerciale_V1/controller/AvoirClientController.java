package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import tn.taktak.GestCommerciale_V1.entity.AvoirClient;
import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.service.AvoirClientService;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class AvoirClientController implements Serializable  {

	@ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
	@ManagedProperty("#{avoirClientService}")
	private AvoirClientService avoirClientService;
    private List<AvoirClient> listeAvoirClient;
	private AvoirClient avoirClient = new AvoirClient();
	
	public static AvoirClient avoircltstc=null;
	public static List<AvoirClient> listavoirstatic;
	public static boolean modif=false;
	
	
	public void getbonAvoirClientId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("AvoirClient");
		avoirClient.setCavoirClient(compteur.getNPiece());
	}
	
	@PostConstruct
	public void loadAvoirClient() {
		
		if(modif==false)
			getbonAvoirClientId();
		else
			avoirClient=avoircltstc;

		
		if(avoirClient.getDateCreation()==null)
			avoirClient.setDateCreation(new Date());
		
		
		if(listavoirstatic!=null)
			listeAvoirClient=listavoirstatic;
		else
			listeAvoirClient = avoirClientService.findAll();
		
	}
	
	public void filterAvClient()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formDevis:rechercheDevis");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			listeAvoirClient=avoirClientService.filterAvClt(recherche);
			listavoirstatic=listeAvoirClient;
			context.update("formDevis:devisTable");
		}
		else
		{
			listeAvoirClient=avoirClientService.findAll();
			listavoirstatic=listeAvoirClient;
		}
	}
	
	public void save() {
		avoirClientService.save(avoirClient);
		avoirClient=new AvoirClient();
		listeAvoirClient = avoirClientService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'avoir client est Enregistrée!", null));
		
		if(modif==true)
		 {
			 modif=false;
			 avoircltstc=null;
		 }
		 else if(modif==false)
			 updateCompteur("AvoirClient");
		getbonAvoirClientId();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:cavoirClient");
		
	}
	
	public void updateCompteur(String typePiece)
	{
		String npiece="";
		CompteurPiece compteur=compteurPieceService.findCompteurById(typePiece);
		compteur.setCompteur(compteur.getCompteur() +1);
		int longueur=compteur.getLongueur();
		String longCompt=((Integer)compteur.getCompteur()).toString();
		for(int i=0;i<(longueur-longCompt.length());i++)
		{
			npiece+="0";
		}
		npiece+=longCompt;
		Calendar cal = Calendar.getInstance();
		String year=((Integer)cal.get(Calendar.YEAR)).toString();
		npiece=year.substring(2,4)+npiece;
		compteur.setNPiece(npiece);
		compteurPieceService.save(compteur);
	}
	
	public void remove(AvoirClient avoir) {
		avoirClientService.remove(avoir);
		listeAvoirClient = avoirClientService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'avoir client est Supprimée!", null));
	}
	
	public void clear()
	{
		avoirClient=new AvoirClient();
	}
	
}
