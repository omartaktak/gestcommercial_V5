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

import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;
import tn.taktak.GestCommerciale_V1.service.FactureClientService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class FactureClientController implements Serializable {

	@ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
	@ManagedProperty("#{factureClientService}")
	private FactureClientService factureClientService;
    private List<FactureClient> listeFactureClient;
	private FactureClient factureClient = new FactureClient();
	
	public static List<FactureClient> facturestatic;
	public static FactureClient facturestc=null;
	public static boolean modif=false;
	
	
	
	public void getFactClientId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("FactureClient");
		factureClient.setCfactureClient(compteur.getNPiece());
	}
	
	@PostConstruct
	public void loadFactureClient() {
		
		if(modif==false)
			getFactClientId();
		else
			factureClient=facturestc;
		
		if(factureClient.getDateCreation()==null)
			factureClient.setDateCreation(new Date());
		
		listeFactureClient = factureClientService.findAll();
		
	}
	
	public List<FactureClient> findAllFacture()
	{
		return  factureClientService.findAll();
	}
	
	public void save() {
		factureClientService.save(factureClient);
		factureClient=new FactureClient();
		listeFactureClient = factureClientService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La facture est Enregistrée!", null));
		
		if(modif==true)
		 {
			 modif=false;
			 facturestc=null;
		 }
		 else if(modif==false)
			 updateCompteur("FactureClient");
		
		
		getFactClientId();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:cfactureClient");
		
		
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
	
	public void filterFacture()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formDevis:rechercheDevis");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			listeFactureClient=factureClientService.filterFacture(recherche);
			facturestatic=listeFactureClient;
			context.update("formDevis:devisTable");
		}
		else
		{
			listeFactureClient=factureClientService.findAll();
			facturestatic=listeFactureClient;
		}
	}
	
	public void remove(FactureClient facture) {
		factureClientService.remove(facture);
		listeFactureClient = factureClientService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La facture est Supprimée!", null));
	}
	
	public List<FactureClient> findFactureOfClient(String t)
	{
		return  factureClientService.findFactureOfClient(t);
	}
	
	public void clear()
	{
		factureClient=new FactureClient();
	}
}
