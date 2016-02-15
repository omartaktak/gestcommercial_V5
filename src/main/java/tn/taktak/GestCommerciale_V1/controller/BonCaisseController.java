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

import tn.taktak.GestCommerciale_V1.entity.BonCaisse;
import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.service.BonCaisseService;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class BonCaisseController implements Serializable {

	@ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
	
	@ManagedProperty("#{bonCaisseService}")
	private BonCaisseService bonCaisseService;
    private List<BonCaisse> listeBonCaisse;
	private BonCaisse bonCaisse = new BonCaisse();
	public static List<BonCaisse> boncaisstatic;
	public static boolean modif=false;
	public static BonCaisse bonCaissestc=null;
	
	public void getbonCaisseId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("BonCaisse");
		bonCaisse.setCbonCaisse(compteur.getNPiece());
	}
	
	@PostConstruct
	public void loadBonCaisse() {
		
		if(modif==false)
			getbonCaisseId();
		else
			bonCaisse=bonCaissestc;
		
		if(bonCaisse.getDateCreation()==null)
			bonCaisse.setDateCreation(new Date());
		
		listeBonCaisse = bonCaisseService.findAll();
		//DevisClient devis = new DevisClient();
	}
	
	public void save() {
		bonCaisseService.save(bonCaisse);
		bonCaisse=new BonCaisse();
		listeBonCaisse = bonCaisseService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bon Caisse Enregistré!", null));

		if(modif==true)
		 {
			 modif=false;
			 bonCaissestc=null;
		 }
		 else if(modif==false)
			 updateCompteur("BonCaisse");
		
		
		getbonCaisseId();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:cbonCaisse");
		
		
		
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
	
	
	public void filterBonCaisse()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formDevis:rechercheDevis");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			listeBonCaisse=bonCaisseService.filterBonCaisse(recherche);
			boncaisstatic=listeBonCaisse;
			context.update("formDevis:devisTable");
		}
		else
		{
			listeBonCaisse=bonCaisseService.findAll();
			boncaisstatic=listeBonCaisse;
		}
	}
	
	public void remove(BonCaisse bonCaisse) {
		bonCaisseService.remove(bonCaisse);
		listeBonCaisse = bonCaisseService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bon Caisse Supprimé!", null));
	}
	
	public void clear()
	{
		bonCaisse=new BonCaisse();
	}
}
