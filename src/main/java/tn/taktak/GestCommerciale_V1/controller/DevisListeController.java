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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;
import tn.taktak.GestCommerciale_V1.service.DevisClientService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
//@RequestScoped
public class DevisListeController implements Serializable {

	@ManagedProperty("#{devisClientService}")
	private DevisClientService devisClientService;
    
	@ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
	
	private List<DevisClient> listedevis;
	private DevisClient devis = new DevisClient();
	public static DevisClient devisstc=null;
	public static List<DevisClient> devisstatic;
	public static boolean modif=false;
	
	public void getbonAchatId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("DevisClient");
		devis.setCdevisClient(compteur.getNPiece());
	}
	
	@PostConstruct
	public void loadDevisClient() {
		if(modif==false)
			getbonAchatId();
		else
			devis=devisstc;
		
		if(devis.getDateCreation()==null)
			devis.setDateCreation(new Date());
		
		if(devisstatic!=null)
			listedevis=devisstatic;
		else
			listedevis = devisClientService.findAll();
		//DevisClient devis = new DevisClient();
	}
	
	public void save() {
		
			devisClientService.save(devis);
			devis=new DevisClient();
			listedevis = devisClientService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Devis Enregistré!", null));
		 if(modif==true)
		 {
			 modif=false;
			 devisstc=null;
		 }
		 else if(modif==false)
			 updateCompteur("DevisClient");
		 
		 getbonAchatId();
		 RequestContext context = RequestContext.getCurrentInstance();
		 context.update("formPrincipal:cdevisClient");
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
	
	public void remove(DevisClient devis) {
		devisClientService.remove(devis);
		listedevis = devisClientService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Devis Supprimé!", null));
	}
	
	public void clear()
	{
		devis=new DevisClient();
	}
	

	public void filterDevis()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formDevis:rechercheDevis");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			listedevis=devisClientService.filterDevis(recherche);
			devisstatic=listedevis;
			context.update("formDevis:devisTable");
		}
		else
		{
			listedevis=devisClientService.findAll();
			devisstatic=listedevis;
		}
	}
	
//	public void updateDevisClient()
//	{
//		boolean isfodec=devis.isFodec();
//		
//		if(isfodec)
//		{
//			
//		}
//	}
}
