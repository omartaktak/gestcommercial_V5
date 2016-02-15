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

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.BonAchat;
import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.service.BonAchatService;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class BonAchatController implements Serializable {

	@ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
	@ManagedProperty("#{bonAchatService}")
	private BonAchatService bonAchatService;
    private List<BonAchat> listeBonAchat;
	private BonAchat bonAchat = new BonAchat();
	public static BonAchat bonachatstc;
	public static List<BonAchat> bonachatstatic;
	public static boolean modif=false;
	
	
	public void getbonAchatId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("BonAchat");
		bonAchat.setCbonAchat(compteur.getNPiece());
	}
	
	@PostConstruct
	public void loadBonAchat() {
		if(modif==false)
			getbonAchatId();
		else
			bonAchat=bonachatstc;
		if(bonAchat.getDateCreation()==null)
			bonAchat.setDateCreation(new Date());
		//getbonAchatId();
		listeBonAchat= bonAchatService.findAll();
		//DevisClient devis = new DevisClient();
	}
	
	public void save() {
		bonAchatService.save(bonAchat);
		bonAchat=new BonAchat();
		
		listeBonAchat = bonAchatService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bon Achat Enregistré!", null));
		
		if(modif==true)
		 {
			 modif=false;
			 bonachatstc=null;
		 }
		 else if(modif==false)
			 updateCompteur("BonAchat");
		
		
		getbonAchatId();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:cbonAchat");
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
	
	public void remove(BonAchat ba) {
		bonAchatService.remove(ba);
		listeBonAchat = bonAchatService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bon Achat Supprimé!", null));
	}
	
	public void filterBonAchat()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formDevis:rechercheDevis");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			listeBonAchat=bonAchatService.filterBonAchat(recherche);
			bonachatstatic=listeBonAchat;
			context.update("formDevis:devisTable");
		}
		else
		{
			listeBonAchat=bonAchatService.findAll();
			bonachatstatic=listeBonAchat;
		}
	}
	
	public void clear()
	{
		bonAchat=new BonAchat();
	}
	
}
