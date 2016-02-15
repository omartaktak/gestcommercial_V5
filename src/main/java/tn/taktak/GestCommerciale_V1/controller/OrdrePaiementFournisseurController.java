package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import tn.taktak.GestCommerciale_V1.entity.AvoirClient;
import tn.taktak.GestCommerciale_V1.entity.BonAchat;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementFournisseur;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;
import tn.taktak.GestCommerciale_V1.entity.Fournisseur;
import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementFournisseur;
import tn.taktak.GestCommerciale_V1.service.BonAchatService;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;
import tn.taktak.GestCommerciale_V1.service.DetailOrdrePaiementClientService;
import tn.taktak.GestCommerciale_V1.service.DetailOrdrePaiementFournisseurService;
import tn.taktak.GestCommerciale_V1.service.OrdrePaiementFournisseurService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class OrdrePaiementFournisseurController  implements Serializable  {

	@ManagedProperty("#{bonAchatService}")
	private BonAchatService bonAchatService;
    @ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
    @ManagedProperty("#{ordrePaiementFournisseurService}")
	private OrdrePaiementFournisseurService ordrePaiementFournisseurService;
    @ManagedProperty("#{detailOrdrePaiementFournisseurService}")
	private DetailOrdrePaiementFournisseurService detailOrdrePaiementFournisseurService;
    
	private OrdrePaiementFournisseur orPayFrn;
	private BigDecimal sumBASelect;
	private List<BonAchat> listBA;
	private List<BonAchat> selectedBA;
	private List<OrdrePaiementFournisseur> listeOPF;
	
	public static List<OrdrePaiementFournisseur> listeOPFstc;
	public static List<BonAchat> listBAStat=null;
	public static BigDecimal  stticsumSelect=new BigDecimal(0);
	public static OrdrePaiementFournisseur orPayFrnStatic=null; 
	
	public void getOrdrePayFournisseurId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("OrdrePaiementFournisseur");
		orPayFrn.setCordrePaiementFournisseur(compteur.getNPiece());
	}
	
	@PostConstruct
	public void loadOrdrePayementClient() {
		
		if(orPayFrnStatic!=null)
			orPayFrn = orPayFrnStatic;
		else
		{	orPayFrn=new OrdrePaiementFournisseur() ;
			getOrdrePayFournisseurId();
		}
		if(stticsumSelect.equals(BigDecimal.ZERO))
			setSumBASelect(BigDecimal.ZERO);
		else
			setSumBASelect(stticsumSelect);
		if(orPayFrn.getDateCreation()==null)
			orPayFrn.setDateCreation(new Date());
		if (listBAStat!=null)	
		{
			listBA=listBAStat;
		}
		else
			setListBA(findAllBA());
		orPayFrnStatic = orPayFrn;
		
		if(listeOPFstc!=null)
			listeOPF=listeOPFstc;
	}
	
	public List<BonAchat> findAllBA()
	{
		return bonAchatService.findAll();
	}
	
	public void processChecked() {
	     
	     setSumBASelect(BigDecimal.ZERO);
		 for(BonAchat f :selectedBA)
		 {
			 if(f.getObservation()=="BonAchat")
				 setSumBASelect(sumBASelect.add(f.getMtNonSoldee()));
			 else if(f.getObservation()=="Accompte" || f.getObservation()=="Avoir" )
			 {
				 setSumBASelect(sumBASelect.subtract(f.getNetApayer()));
			 }
		 }
		 stticsumSelect=getSumBASelect();
		 RequestContext context = RequestContext.getCurrentInstance();
		 context.update("form-unit:sumFactSelect");
	}
	
	public void ajaxListenerUnS(UnselectEvent event)
	{
		BonAchat ff=(BonAchat)event.getObject();
		//setSumFactSelect(sumFactSelect.subtract(ff.getMtNonSoldee()));
		if(ff.getObservation()=="BonAchat")
			 setSumBASelect(sumBASelect.subtract(ff.getMtNonSoldee()));
		else if(ff.getObservation()=="Accompte" || ff.getObservation()=="Avoir")
		{
			 setSumBASelect(sumBASelect.add(ff.getNetApayer()));
		}
		setSumBASelect(getSumBASelect().setScale(3, BigDecimal.ROUND_UP));
		stticsumSelect=getSumBASelect();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("form-unit:sumFactSelect");
	}
	
	public void ajaxListener(SelectEvent event)
	{
		 	 setSumBASelect(BigDecimal.ZERO);
		
		 	// FactureClient ff=(FactureClient)event.getObject();
			 for(BonAchat f :selectedBA)
			 {
				 //setSumFactSelect(sumFactSelect.add(f.getMtNonSoldee()));
				 
				 if(f.getObservation()=="BonAchat")
					 setSumBASelect(sumBASelect.add(f.getMtNonSoldee()));
				 else if(f.getObservation()=="Accompte" ||f.getObservation()=="Avoir")
				 {
					 setSumBASelect(sumBASelect.subtract(f.getNetApayer()));
				 }
			 }
			 setSumBASelect(getSumBASelect().setScale(3, BigDecimal.ROUND_UP));
			 stticsumSelect=getSumBASelect();
			 RequestContext context = RequestContext.getCurrentInstance();
			 context.update("form-unit:sumFactSelect");
	}
	
	public void processVentiler(AjaxBehaviorEvent event)
	{
		BigDecimal diff;
		if(!getSumBASelect().equals(BigDecimal.ZERO))
		{
			diff=getSumBASelect().subtract(orPayFrn.getMtReglement());
			orPayFrn.setMtVentiler(diff);
			
			orPayFrn.setMtVentiler(orPayFrn.getMtVentiler().negate());
			orPayFrn.setMtVentiler(orPayFrn.getMtVentiler().setScale(3, BigDecimal.ROUND_UP));
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("form-unit:reste-vent");
		}
		orPayFrnStatic = orPayFrn;
	}
	
	public void processVentilerEscompte(AjaxBehaviorEvent event)
	{
		BigDecimal res;
	//	OrdrePaiementClient ordre=getOrPayClt();
		if(!getOrPayFrn().getMtEscompte().equals(BigDecimal.ZERO) && !getOrPayFrn().getMtVentiler().equals(BigDecimal.ZERO) )
		{
			res=orPayFrn.getMtEscompte().add(orPayFrn.getMtVentiler());
			orPayFrn.setMtVentiler(res);

			orPayFrn.setMtVentiler(orPayFrn.getMtVentiler().setScale(3, BigDecimal.ROUND_UP));
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("form-unit:reste-vent");
		}
		orPayFrnStatic = orPayFrn;
	}
	
	public void getFournOfReglement(Fournisseur fourn)
	{
		orPayFrn.setCfournisseur(fourn.getCfournisseur());
		setListBA(bonAchatService.findBAOfFournisseur(fourn.getCfournisseur()));
		listBA=CreerListeBAAPayee(listBA);
		listBAStat=listBA;
		orPayFrnStatic = orPayFrn;
	}
	
	public void save() {
		
		if(orPayFrn.getCfournisseur()!="" && orPayFrn.getDateCreation()!=null && orPayFrn.getMtReglement()!=null && orPayFrn.getMtVentiler()!=null)
		{
			List<DetailOrdrePaiementFournisseur> listdetailfinal;
			if(orPayFrn.getMtVentiler().compareTo(new BigDecimal(0))==1)
				orPayFrn.setGenererAcompte(true);
			ordrePaiementFournisseurService.save(orPayFrn);
			
			listdetailfinal=TraitementBA(selectedBA);
			detailOrdrePaiementFournisseurService.saveList(listdetailfinal);
			
			orPayFrn=new OrdrePaiementFournisseur();
			orPayFrnStatic=orPayFrn;
			if(orPayFrn.getDateCreation()==null)
				orPayFrn.setDateCreation(new Date());
			updateCompteur("OrdrePaiementFournisseur");
			getOrdrePayFournisseurId();
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("form-unit:code");
			
			selectedBA.clear();
			setSumBASelect(new BigDecimal(0));
			context.update("form-unit:sumFactSelect");
			
			//listfacture=CreerListeFactureAPayee(listfacture);
			listBA.clear();
			context.update("form-unit:detailTable");
			
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ordre payment client Enregistré!", null));
		}
		else
		{
			RequestContext context = RequestContext.getCurrentInstance();
			if(orPayFrn.getCfournisseur()==null|| orPayFrn.getCfournisseur()=="")
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sélectionner un fournisseur!", null));
			if(orPayFrn.getDateCreation()==null)
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sélectionner une date!", null));
			if(orPayFrn.getMtReglement()==null)
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mt Réglement ne doit pas etre null!", null));
			if(orPayFrn.getMtVentiler()==null)
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mt Ventiler ne doit pas etre null!", null));
		}
	}
	
	
	public void SeletedFournisseur(Fournisseur fourn)
	{
		listeOPF=ordrePaiementFournisseurService.findAllListOfOrdrePayFournisseur(fourn.getCfournisseur());
		listeOPFstc=listeOPF;
	}
	
	public void supprimerOPF(OrdrePaiementFournisseur opf)
	{
		List<DetailOrdrePaiementFournisseur> listeDetailOPF= detailOrdrePaiementFournisseurService.findDetailOfOrdrePayFournisseur(opf.getCordrePaiementFournisseur());
		if(listeDetailOPF.size()>0)
		for(DetailOrdrePaiementFournisseur detailopf:listeDetailOPF)
		{
			if(detailopf.getCordrePaiementFournisseurAcompte()!=null)
			{
				OrdrePaiementFournisseur opc1=ordrePaiementFournisseurService.findOPFById(detailopf.getCbonAchat());
				opc1.setGenererAcompte(true);		
				ordrePaiementFournisseurService.save(opc1);
			}
			/*else if(detailopf.getCavoir()!=null)
			{
				AvoirClient avc=avoirClientService.findAVCById(detailopc.getCfactureClient());
				avc.setSoldee(null);
				avoirClientService.save(avc);
			}*/
			detailOrdrePaiementFournisseurService.remove(detailopf);
		}
		ordrePaiementFournisseurService.remove(opf);
		listeOPF.remove(opf);
		listeOPFstc=listeOPF;
	}
	
	
	public List<BonAchat> CreerListeBAAPayee(List<BonAchat> listba)
	{
		int resCompare;
		List<BonAchat> listebaFourn=new ArrayList<BonAchat>() ;
		 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 //String dmy = dmyFormat.format(bonAchat.getDateCreation());
		 
		for(BonAchat f : listba)
		{
			
			List<DetailOrdrePaiementFournisseur> listeDetailPayfacture=new ArrayList<DetailOrdrePaiementFournisseur>() ;
			DetailOrdrePaiementFournisseur detailOrdPay=new DetailOrdrePaiementFournisseur();
			BonAchat baFournlocal=new BonAchat();
			baFournlocal.setObservation("BonAchat");
			listeDetailPayfacture=detailOrdrePaiementFournisseurService.findListFactureDetail(f.getCbonAchat());
			if(listeDetailPayfacture.size()>0)
			{
				detailOrdPay=listeDetailPayfacture.get(0);
				resCompare=detailOrdPay.getMtNonSoldee().compareTo(new BigDecimal(0));
				if(resCompare==1)
				{
					baFournlocal.setCbonAchat(detailOrdPay.getCbonAchat());
					baFournlocal.setDateCreation(f.getDateCreation());
					baFournlocal.setNetApayer(f.getNetApayer());
					baFournlocal.setMtSoldee(detailOrdPay.getMtSoldee());
					baFournlocal.setMtNonSoldee(detailOrdPay.getMtNonSoldee());
					listebaFourn.add(baFournlocal);
				}
			}
			else
			{
				baFournlocal=f;
				if(baFournlocal.getMtSoldee()==null)
					baFournlocal.setMtSoldee(new BigDecimal(0));
				if(baFournlocal.getMtNonSoldee()==null)
					baFournlocal.setMtNonSoldee(f.getNetApayer());
				baFournlocal.setObservation("BonAchat");
				listebaFourn.add(baFournlocal);
			}
			
			//listeDetail.add(detailOrdPay);
		}
		List<OrdrePaiementFournisseur>listeAccompte=ordrePaiementFournisseurService.findListOfOrdrePayFournisseur(orPayFrn.getCfournisseur());
		if(listeAccompte.size()>0)
		{
			for(OrdrePaiementFournisseur opf : listeAccompte)
			{
				BonAchat baFournlocal=new BonAchat();
				baFournlocal.setObservation("Accompte");
				baFournlocal.setCbonAchat(opf.getCordrePaiementFournisseur());
				baFournlocal.setDateCreation(opf.getDateCreation());
				baFournlocal.setNetApayer(opf.getMtVentiler());
				baFournlocal.setMtSoldee(new BigDecimal(0));
				baFournlocal.setMtNonSoldee(new BigDecimal(0));
				listebaFourn.add(baFournlocal);
			}
		}
		
		/*List<AvoirClient> listAvoir=avoirClientService.findListOfAvoirClient(orPayClt.getCclient());
		if(listAvoir.size()>0)
		{
			for(AvoirClient avc : listAvoir)
			{
				FactureClient factClientlocal=new FactureClient();
				factClientlocal.setObservation("Avoir");
				factClientlocal.setCfactureClient(avc.getCavoirClient());
				factClientlocal.setDateCreation(avc.getDateCreation());
				factClientlocal.setNetApayer(avc.getNetApayer());
				factClientlocal.setMtSoldee(new BigDecimal(0));
				factClientlocal.setMtNonSoldee(new BigDecimal(0));
				listeFactClient.add(factClientlocal);
			}
		}*/
		return listebaFourn;
	}
	
	public List<DetailOrdrePaiementFournisseur> TraitementBA(List<BonAchat> listBA)
	{
		int resCompare;BigDecimal mtReglementTotal;
		List<DetailOrdrePaiementFournisseur> listdetailOrdPay=new ArrayList<DetailOrdrePaiementFournisseur>() ;
		BigDecimal somAccompte=new BigDecimal(0);
		BigDecimal somFact=new BigDecimal(0);
		for(BonAchat f : listBA)
		{
			if(f.getObservation()=="Accompte" || f.getObservation()=="Avoir")// Récupérer le montant des Accomptes Sélectionnés.
				somAccompte=somAccompte.add(f.getNetApayer());
			else  // Récupérer le montant des BA Sélectionnées.
				somFact=somFact.add(f.getMtNonSoldee());
		}
		
		if(orPayFrn.getMtEscompte()!=null)
			mtReglementTotal=orPayFrn.getMtReglement().add(orPayFrn.getMtEscompte());
		else
			mtReglementTotal=orPayFrn.getMtReglement();
		
		mtReglementTotal=mtReglementTotal.add(somAccompte);
		for(BonAchat f : listBA)
		{
			DetailOrdrePaiementFournisseur detailOrdPay=new DetailOrdrePaiementFournisseur();
			if(listdetailOrdPay.size()==0)
				detailOrdPay.setNligne(1);
			else
				detailOrdPay.setNligne(listdetailOrdPay.size()+1);
			detailOrdPay.setCordrePaiementFournisseur(orPayFrn.getCordrePaiementFournisseur());
			detailOrdPay.setCbonAchat(f.getCbonAchat());
			
			if(f.getMtSoldee()==null)
			   detailOrdPay.setMtSoldee(BigDecimal.ZERO);
			else
			   detailOrdPay.setMtSoldee(f.getMtSoldee());
			
			if(f.getMtNonSoldee()==null)
			   detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
			else
			   detailOrdPay.setMtNonSoldee(f.getMtNonSoldee());
			
			if(f.getObservation()=="BonAchat")
			{
				orPayFrn.setMtVentiler(orPayFrn.getMtVentiler().setScale(3, BigDecimal.ROUND_UP));
				if(orPayFrn.getMtVentiler().equals(BigDecimal.ZERO)||orPayFrn.getMtVentiler().equals(new BigDecimal("0.000")) )
				{
					detailOrdPay.setMtSoldee(f.getMtSoldee().add(f.getMtNonSoldee()));
					detailOrdPay.setMtRetiree(f.getMtNonSoldee());
					detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
				}
				else
				{
					resCompare=somFact.compareTo(mtReglementTotal);
					if(resCompare==1)//Le cas où la somme des factures sélectionnées est supérieur à MtReglement
					{
						resCompare=mtReglementTotal.compareTo(f.getMtNonSoldee());
						if(resCompare==1 ||resCompare==0)//MtReglement est sup ou égal à netApayer pour une facture.
						{
							detailOrdPay.setMtSoldee(detailOrdPay.getMtSoldee().add(f.getMtNonSoldee()));
							detailOrdPay.setMtRetiree(f.getMtNonSoldee());
							detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
							mtReglementTotal=mtReglementTotal.subtract(f.getMtNonSoldee());
						}
						else //MtReglement est inférieur à MtNonSoldee pour une facture.
						{
							//BigDecimal diff=f.getMtNonSoldee().subtract(mtReglementTotal);
							//BigDecimal diff=f.getMtNonSoldee().add(orPayClt.getMtVentiler());
							detailOrdPay.setMtSoldee(detailOrdPay.getMtSoldee().add(mtReglementTotal));
							detailOrdPay.setMtRetiree(mtReglementTotal);
							detailOrdPay.setMtNonSoldee(detailOrdPay.getMtNonSoldee().subtract(mtReglementTotal));
							mtReglementTotal=new BigDecimal(0);
						}
					}
					else if(resCompare==-1)// Si le montant réglement est supèrieur à la somme des BAs sélectionnées: Génération Accompte
					{
						detailOrdPay.setMtSoldee(f.getMtSoldee().add(f.getMtNonSoldee()));
						detailOrdPay.setMtRetiree(f.getMtNonSoldee());
						detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
					}
				}
			}
			else if(f.getObservation()=="Accompte")
			{
				detailOrdPay.setMtSoldee(f.getNetApayer());
				detailOrdPay.setMtRetiree(f.getMtNonSoldee());
				detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
				detailOrdPay.setCordrePaiementFournisseurAcompte(f.getCbonAchat());
				OrdrePaiementFournisseur opf=ordrePaiementFournisseurService.findOPFById(f.getCbonAchat());
				opf.setGenererAcompte(false);
				ordrePaiementFournisseurService.save(opf);
			}
//			else if(f.getObservation()=="Avoir")
//			{
//				detailOrdPay.setMtSoldee(f.getNetApayer());
//				detailOrdPay.setMtRetiree(f.getMtNonSoldee());
//				detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
//				AvoirClient avc=avoirClientService.findAVCById(f.getCfactureClient());
//				avc.setSoldee("oui");
//				avoirClientService.save(avc);
//			}
			listdetailOrdPay.add(detailOrdPay);
		}
		return listdetailOrdPay;
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
	
	public void clear()
	{
		orPayFrn=new OrdrePaiementFournisseur();
	}
	
}
