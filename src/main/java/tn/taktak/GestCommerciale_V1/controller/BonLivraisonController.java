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

import tn.taktak.GestCommerciale_V1.entity.BonLivraison;
import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.service.BonLivraisonService;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;
import lombok.Getter;
import lombok.Setter;


@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class BonLivraisonController implements Serializable {

	
	@ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
	
	@ManagedProperty("#{bonLivraisonService}")
	private BonLivraisonService bonLivraisonService;
    private List<BonLivraison> listebonLivraison;
	private BonLivraison bonLivraison = new BonLivraison();
	
	public static List<BonLivraison> bonLivStatic;
	public static boolean modif=false;
	public static BonLivraison bonlivstc=null;
	
	@PostConstruct
	public void loadBonLivraison() {
		if(modif==false)
			getbonLivId();
		else
			bonLivraison=bonlivstc;
		
		if(bonLivraison.getDateCreation()==null)
			bonLivraison.setDateCreation(new Date());
		
		if(bonLivStatic!=null)
			listebonLivraison=bonLivStatic;
		else
		     listebonLivraison= bonLivraisonService.findAll();
		//DevisClient devis = new DevisClient();
	}
	
	public void getbonLivId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("BonLivraison");
		bonLivraison.setCbonLivraison(compteur.getNPiece());
	}
	
	public void save() {
		bonLivraisonService.save(bonLivraison);
		bonLivraison=new BonLivraison();
		listebonLivraison = bonLivraisonService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le Bon de livraison est  Enregistré!", null));
		
		if(modif==true)
		 {
			 modif=false;
			 bonlivstc=null;
		 }
		 else if(modif==false)
			 updateCompteur("BonLivraison");
		
		
		getbonLivId();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:cbonLivraison");
		
		
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
	
	public void remove(BonLivraison bonLivraison) {
		bonLivraisonService.remove(bonLivraison);
		listebonLivraison = bonLivraisonService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le Bon de livraison est Supprimé!", null));
	}
	
	public void filterBonLiv()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formBonLiv:rechercheBonLiv");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			listebonLivraison=bonLivraisonService.filterBonLiv(recherche);
			bonLivStatic=listebonLivraison;
			context.update("formDevis:devisTable");
		}
		else
		{
			listebonLivraison=bonLivraisonService.findAll();
			bonLivStatic=listebonLivraison;
		}
	}
	
	public void clear()
	{
		bonLivraison=new BonLivraison();
	}
}
