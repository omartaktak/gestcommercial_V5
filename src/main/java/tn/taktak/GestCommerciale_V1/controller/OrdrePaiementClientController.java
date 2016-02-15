package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.AvoirClient;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.CompteurPiece;
import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;
import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.service.AvoirClientService;
import tn.taktak.GestCommerciale_V1.service.CompteurPieceService;
import tn.taktak.GestCommerciale_V1.service.DetailOrdrePaiementClientService;
import tn.taktak.GestCommerciale_V1.service.FactureClientService;
import tn.taktak.GestCommerciale_V1.service.OrdrePaiementClientService;

@ManagedBean
@Getter
@Setter
@ViewScoped
//@RequestScoped
public class OrdrePaiementClientController implements Serializable {

	@ManagedProperty("#{detailOrdrePaiementClientService}")
	private DetailOrdrePaiementClientService detailOrdrePaiementClientService;
    
	@ManagedProperty("#{ordrePaiementClientService}")
	private OrdrePaiementClientService ordrePaiementClientService;
    
	@ManagedProperty("#{avoirClientService}")
	private AvoirClientService avoirClientService;
	
	/*@ManagedProperty("#{factureClientController}")
	private FactureClientController factureClientController;*/
	
	@ManagedProperty("#{factureClientService}")
	private FactureClientService factureClientService;
	
	@ManagedProperty("#{compteurPieceService}")
	private CompteurPieceService compteurPieceService;
	private List<OrdrePaiementClient> listeOrPayClt;
	@ManagedProperty("#{orPayClt}")
	private OrdrePaiementClient orPayClt;
	
	private List<FactureClient> listfacture;
	private List<FactureClient> selectedFacture;
	private FactureClient factclient;
	private BigDecimal sumFactSelect;
	private List<OrdrePaiementClient> listeOPC;
	public static List<OrdrePaiementClient> listeOPCstc;
	//private boolean selected; 
	public static List<FactureClient> listFactStat=null;
	public static BigDecimal  stticsumSelect=new BigDecimal(0);
	public static OrdrePaiementClient orPayCltStatic=null; 
	
	
	public void getOrdrePayClientId()
	{
		CompteurPiece compteur=compteurPieceService.findCompteurById("OrdrePaiementClient");
		orPayClt.setCordrePaiementClient(compteur.getNPiece());
	}
	
	@PostConstruct
	public void loadOrdrePayementClient() {
		
		if(orPayCltStatic!=null)
			orPayClt = orPayCltStatic;
		else
		{	orPayClt=new OrdrePaiementClient() ;
			getOrdrePayClientId();
		}
		if(stticsumSelect.equals(BigDecimal.ZERO))
			setSumFactSelect(BigDecimal.ZERO);
		else
			setSumFactSelect(stticsumSelect);
		if(orPayClt.getDateCreation()==null)
		   orPayClt.setDateCreation(new Date());
		if (listFactStat!=null)	
		{
			listfacture=listFactStat;
		}
		else
			setListfacture(findAllFacture());
		orPayCltStatic = orPayClt;
		
		if(listeOPCstc!=null)
			listeOPC=listeOPCstc;
	}
	
	public List<FactureClient> findAllFacture()
	{
		return factureClientService.findAll();
	}
	
//	public void permissionChanged(ValueChangeEvent event) {
//	
////		String sss=event.getNewValue().toString();
////		Object obj=event.getNewValue().getClass();
//		setListfacture(findAllFacture());
//		//setSumFactSelect(10);
//	}
	
	public void processChecked() {
	     
	     setSumFactSelect(BigDecimal.ZERO);
		 for(FactureClient f :selectedFacture)
		 {
			 if(f.getObservation()=="Facture")
				 setSumFactSelect(sumFactSelect.add(f.getMtNonSoldee()));
			 else if(f.getObservation()=="Accompte" || f.getObservation()=="Avoir" )
			 {
				 setSumFactSelect(sumFactSelect.subtract(f.getNetApayer()));
			 }
		 }
		 stticsumSelect=getSumFactSelect();
		 RequestContext context = RequestContext.getCurrentInstance();
		 context.update("form-unit:sumFactSelect");
	}
	
	
	public void ajaxListenerUnS(UnselectEvent event)
	{
		FactureClient ff=(FactureClient)event.getObject();
		//setSumFactSelect(sumFactSelect.subtract(ff.getMtNonSoldee()));
		
		
		if(ff.getObservation()=="Facture")
			 setSumFactSelect(sumFactSelect.subtract(ff.getMtNonSoldee()));
		else if(ff.getObservation()=="Accompte" || ff.getObservation()=="Avoir")
		{
			 setSumFactSelect(sumFactSelect.add(ff.getNetApayer()));
		}
		setSumFactSelect(getSumFactSelect().setScale(3, BigDecimal.ROUND_UP));
		stticsumSelect=getSumFactSelect();
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("form-unit:sumFactSelect");
	}
	
	public void ajaxListener(SelectEvent event)
	{
		 	 setSumFactSelect(BigDecimal.ZERO);
		
		 	// FactureClient ff=(FactureClient)event.getObject();
			 for(FactureClient f :selectedFacture)
			 {
				 //setSumFactSelect(sumFactSelect.add(f.getMtNonSoldee()));
				 
				 if(f.getObservation()=="Facture")
					 setSumFactSelect(sumFactSelect.add(f.getMtNonSoldee()));
				 else if(f.getObservation()=="Accompte" ||f.getObservation()=="Avoir")
				 {
					 setSumFactSelect(sumFactSelect.subtract(f.getNetApayer()));
				 }
			 }
			 setSumFactSelect(getSumFactSelect().setScale(3, BigDecimal.ROUND_UP));
			 stticsumSelect=getSumFactSelect();
			 RequestContext context = RequestContext.getCurrentInstance();
			 context.update("form-unit:sumFactSelect");
	}
	
	/*public void ajaxListener(AjaxBehaviorEvent event)
	{
		
			String ff=event.getClass().toString();
		 	// setSumFactSelect(BigDecimal.ZERO);
			 for(FactureClient f :selectedFacture)
			 {
				 setSumFactSelect(sumFactSelect.add(f.getNetApayer()));
			 }
			 stticsumSelect=getSumFactSelect();
			 RequestContext context = RequestContext.getCurrentInstance();
			 context.update("form-unit:sumFactSelect");
	}*/
	
	public void processVentiler(AjaxBehaviorEvent event)
	{
		BigDecimal diff;
		if(!getSumFactSelect().equals(BigDecimal.ZERO))
		{
			diff=getSumFactSelect().subtract(orPayClt.getMtReglement());
			orPayClt.setMtVentiler(diff);
			
			orPayClt.setMtVentiler(orPayClt.getMtVentiler().negate());
			orPayClt.setMtVentiler(orPayClt.getMtVentiler().setScale(3, BigDecimal.ROUND_UP));
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("form-unit:reste-vent");
		}
		orPayCltStatic = orPayClt;
	}
	
	public void processVentilerEscompte(AjaxBehaviorEvent event)
	{
		BigDecimal res;
	//	OrdrePaiementClient ordre=getOrPayClt();
		if(!getOrPayClt().getMtEscompte().equals(BigDecimal.ZERO) && !getOrPayClt().getMtVentiler().equals(BigDecimal.ZERO) )
		{
			res=orPayClt.getMtEscompte().add(orPayClt.getMtVentiler());
			orPayClt.setMtVentiler(res);

			orPayClt.setMtVentiler(orPayClt.getMtVentiler().setScale(3, BigDecimal.ROUND_UP));
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("form-unit:reste-vent");
		}
		orPayCltStatic = orPayClt;
	}
	
	public void getClientOfReglement(Client client)
	{
		orPayClt.setCclient(client.getCclient());
		setListfacture(factureClientService.findFactureOfClient(client.getCclient()));
		listfacture=CreerListeFactureAPayee(listfacture);
		listFactStat=listfacture;
		orPayCltStatic = orPayClt;
	}
	
	public void SelectedClient(Client client)
	{
		listeOPC=ordrePaiementClientService.findAllListOfOrdrePayClient(client.getCclient());
		listeOPCstc=listeOPC;
	}
	
//	public void ajaxListener(AjaxBehaviorEvent event) {
//	    System.out.println(selected);
//	}
	
	public void save() {
		
		if(orPayClt.getCclient()!="" && orPayClt.getDateCreation()!=null && orPayClt.getMtReglement()!=null && orPayClt.getMtVentiler()!=null)
		{
			List<DetailOrdrePaiementClient> listdetailfinal;
			if(orPayClt.getMtVentiler().compareTo(new BigDecimal(0))==1)
					orPayClt.setGenererAcompte(true);
			ordrePaiementClientService.save(orPayClt);
			
			listdetailfinal=TraitementFacture(selectedFacture);
			detailOrdrePaiementClientService.saveList(listdetailfinal);
			
			orPayClt=new OrdrePaiementClient();
			orPayCltStatic=orPayClt;
			if(orPayClt.getDateCreation()==null)
				   orPayClt.setDateCreation(new Date());
			updateCompteur("OrdrePaiementClient");
			getOrdrePayClientId();
			RequestContext context = RequestContext.getCurrentInstance();
			context.update("form-unit:code");
			
			selectedFacture.clear();
			setSumFactSelect(new BigDecimal(0));
			context.update("form-unit:sumFactSelect");
			
			//listfacture=CreerListeFactureAPayee(listfacture);
			listfacture.clear();
			context.update("form-unit:detailTable");
			
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ordre payment client Enregistré!", null));
		}
		else
		{
			RequestContext context = RequestContext.getCurrentInstance();
			if(orPayClt.getCclient()==null|| orPayClt.getCclient()=="")
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sélectionner un client!", null));
			if(orPayClt.getDateCreation()==null)
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sélectionner une date!", null));
			if(orPayClt.getMtReglement()==null)
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mt Réglement ne doit pas etre null!", null));
			if(orPayClt.getMtVentiler()==null)
				FacesContext.getCurrentInstance().addMessage
				(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mt Ventiler ne doit pas etre null!", null));
		}
	}
	
	public List<FactureClient> CreerListeFactureAPayee(List<FactureClient> listfact)
	{
		int resCompare;
		List<FactureClient> listeFactClient=new ArrayList<FactureClient>() ;
		 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 //String dmy = dmyFormat.format(bonAchat.getDateCreation());
		 
		for(FactureClient f : listfact)
		{
			
			List<DetailOrdrePaiementClient> listeDetailPayfacture=new ArrayList<DetailOrdrePaiementClient>() ;
			DetailOrdrePaiementClient detailOrdPay=new DetailOrdrePaiementClient();
			FactureClient factClientlocal=new FactureClient();
			factClientlocal.setObservation("Facture");
			listeDetailPayfacture=detailOrdrePaiementClientService.findListFactureDetail(f.getCfactureClient());
			if(listeDetailPayfacture.size()>0)
			{
				detailOrdPay=listeDetailPayfacture.get(0);
				resCompare=detailOrdPay.getMtNonSoldee().compareTo(new BigDecimal(0));
				if(resCompare==1)
				{
					factClientlocal.setCfactureClient(detailOrdPay.getCfactureClient());
					factClientlocal.setDateCreation(f.getDateCreation());
					factClientlocal.setNetApayer(f.getNetApayer());
					factClientlocal.setMtSoldee(detailOrdPay.getMtSoldee());
					factClientlocal.setMtNonSoldee(detailOrdPay.getMtNonSoldee());
					listeFactClient.add(factClientlocal);
				}
			}
			else
			{
				factClientlocal=f;
				if(factClientlocal.getMtSoldee()==null)
					factClientlocal.setMtSoldee(new BigDecimal(0));
				if(factClientlocal.getMtNonSoldee()==null)
					factClientlocal.setMtNonSoldee(f.getNetApayer());
				factClientlocal.setObservation("Facture");
				listeFactClient.add(factClientlocal);
			}
			
			//listeDetail.add(detailOrdPay);
		}
		List<OrdrePaiementClient>listeAccompte=ordrePaiementClientService.findListOfOrdrePayClient(orPayClt.getCclient());
		if(listeAccompte.size()>0)
		{
			for(OrdrePaiementClient opc : listeAccompte)
			{
				FactureClient factClientlocal=new FactureClient();
				factClientlocal.setObservation("Accompte");
				factClientlocal.setCfactureClient(opc.getCordrePaiementClient());
				factClientlocal.setDateCreation(opc.getDateCreation());
				factClientlocal.setNetApayer(opc.getMtVentiler());
				factClientlocal.setMtSoldee(new BigDecimal(0));
				factClientlocal.setMtNonSoldee(new BigDecimal(0));
				listeFactClient.add(factClientlocal);
			}
		}
		
		List<AvoirClient> listAvoir=avoirClientService.findListOfAvoirClient(orPayClt.getCclient());
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
		}
		return listeFactClient;
	}
	
	public List<DetailOrdrePaiementClient> TraitementFacture(List<FactureClient> listfact)
	{
		int resCompare;BigDecimal mtReglementTotal;
		List<DetailOrdrePaiementClient> listdetailOrdPay=new ArrayList<DetailOrdrePaiementClient>() ;
		BigDecimal somAccompte=new BigDecimal(0);
		BigDecimal somFact=new BigDecimal(0);
		for(FactureClient f : listfact)
		{
			if(f.getObservation()=="Accompte" || f.getObservation()=="Avoir")// Récupérer le montant des Accomptes Sélectionnés.
				somAccompte=somAccompte.add(f.getNetApayer());
			else  // Récupérer le montant des Factures Sélectionnées.
				somFact=somFact.add(f.getMtNonSoldee());
		}
		
		if(orPayClt.getMtEscompte()!=null)
			mtReglementTotal=orPayClt.getMtReglement().add(orPayClt.getMtEscompte());
		else
			mtReglementTotal=orPayClt.getMtReglement();
		
		mtReglementTotal=mtReglementTotal.add(somAccompte);
		for(FactureClient f : listfact)
		{
			DetailOrdrePaiementClient detailOrdPay=new DetailOrdrePaiementClient();
			if(listdetailOrdPay.size()==0)
				detailOrdPay.setNligne(1);
			else
				detailOrdPay.setNligne(listdetailOrdPay.size()+1);
			detailOrdPay.setCordrePaiementClient(orPayClt.getCordrePaiementClient());
			detailOrdPay.setCfactureClient(f.getCfactureClient());
			
			if(f.getMtSoldee()==null)
			   detailOrdPay.setMtSoldee(BigDecimal.ZERO);
			else
			   detailOrdPay.setMtSoldee(f.getMtSoldee());
			
			if(f.getMtNonSoldee()==null)
			   detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
			else
			   detailOrdPay.setMtNonSoldee(f.getMtNonSoldee());
			
			if(f.getObservation()=="Facture")
			{
				orPayClt.setMtVentiler(orPayClt.getMtVentiler().setScale(3, BigDecimal.ROUND_UP));
				if(orPayClt.getMtVentiler().equals(BigDecimal.ZERO)||orPayClt.getMtVentiler().equals(new BigDecimal("0.000")) )
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
					else if(resCompare==-1)// Si le montant réglement est supèrieur à la somme des facts sélectionnées: Génération Accompte
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
				detailOrdPay.setCordrePaiementClientAcompte(f.getCfactureClient());
				OrdrePaiementClient opc=ordrePaiementClientService.findOPCById(f.getCfactureClient());
				opc.setGenererAcompte(false);
				ordrePaiementClientService.save(opc);
			}
			else if(f.getObservation()=="Avoir")
			{
				detailOrdPay.setMtSoldee(f.getNetApayer());
				detailOrdPay.setMtRetiree(f.getMtNonSoldee());
				detailOrdPay.setMtNonSoldee(BigDecimal.ZERO);
				detailOrdPay.setCavoirClient(f.getCfactureClient());
				AvoirClient avc=avoirClientService.findAVCById(f.getCfactureClient());
				avc.setSoldee("oui");
				avoirClientService.save(avc);
			}
			listdetailOrdPay.add(detailOrdPay);
		}
		return listdetailOrdPay;
	}
	
	public void supprimerOPC(OrdrePaiementClient opc)
	{
		List<DetailOrdrePaiementClient> listeDetailOPC= detailOrdrePaiementClientService.findDetailOfOrdrePayClient(opc.getCordrePaiementClient());
		if(listeDetailOPC.size()>0)
		for(DetailOrdrePaiementClient detailopc:listeDetailOPC)
		{
			if(detailopc.getCordrePaiementClientAcompte()!=null)
			{
				OrdrePaiementClient opc1=ordrePaiementClientService.findOPCById(detailopc.getCfactureClient());
				opc1.setGenererAcompte(true);		
				ordrePaiementClientService.save(opc1);
			}
			else if(detailopc.getCavoirClient()!=null)
			{
				AvoirClient avc=avoirClientService.findAVCById(detailopc.getCfactureClient());
				avc.setSoldee(null);
				avoirClientService.save(avc);
			}
			detailOrdrePaiementClientService.remove(detailopc);
		}
		ordrePaiementClientService.remove(opc);
		listeOPC.remove(opc);
		listeOPCstc=listeOPC;
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
		orPayClt=new OrdrePaiementClient();
	}
}
