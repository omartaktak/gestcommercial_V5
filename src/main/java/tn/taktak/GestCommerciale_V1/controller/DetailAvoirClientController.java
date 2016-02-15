package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;

import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.AvoirClient;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.DetailAvoirClient;
import tn.taktak.GestCommerciale_V1.entity.DetailBonAchat;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.Fournisseur;
import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.service.ArticleService;
import tn.taktak.GestCommerciale_V1.service.DetailAvoirClientService;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
public class DetailAvoirClientController implements Serializable {


	@ManagedProperty("#{detailAvoirClientService}")
	private DetailAvoirClientService detailAvoirClientService;
	
	@ManagedProperty("#{articleListController}")
	private ArticleListController articleListController;
	
	@ManagedProperty(value="#{tvaListController}")
	private TvaListController tvaListController;
	
	@ManagedProperty("#{avoirClientController}")
	private AvoirClientController avoirClientController;
		
	@ManagedProperty(value="#{clientListController}")
	private ClientListController clientListController;
	
	@ManagedProperty("#{articleService}")
    private ArticleService articleService;
	
	private List<DetailAvoirClient> listeavoirclient;
	private DetailAvoirClient detailAvoirClient=new DetailAvoirClient();
	private AvoirClient avoirClient=null;
	public static List<DetailAvoirClient> listedetailarticles=null;
	
	@PostConstruct
	public void loadDetailAvoirClient() {
	
		if(listedetailarticles!=null)
			listeavoirclient=listedetailarticles;
		else
			listeavoirclient=new ArrayList<DetailAvoirClient>(); 
	}
	
	public AvoirClient getAvoirClientActuel()
	{
		return avoirClientController.getAvoirClient();
	}
	
	public void deleteArticle(DetailAvoirClient selected)
	{
		
		listeavoirclient.remove(selected);
		
		avoirClient=getAvoirClientActuel();
		if(avoirClient.getMtTotalVente()==null) avoirClient.setMtTotalVente(BigDecimal.ZERO);
		avoirClient.setMtTotalVente(avoirClient.getMtTotalVente().subtract(selected.getMtTotalPrixVente()));
		if(avoirClient.getMtTotalHt()==null) avoirClient.setMtTotalHt(BigDecimal.ZERO);
		avoirClient.setMtTotalHt(avoirClient.getMtTotalHt().subtract(selected.getMtTotalHt()));
		if(avoirClient.getMtTotalTva()==null) avoirClient.setMtTotalTva(BigDecimal.ZERO);
		avoirClient.setMtTotalTva(avoirClient.getMtTotalTva().subtract(selected.getMtTotalTva()));
		if(avoirClient.getMtTotalRemises()==null) avoirClient.setMtTotalRemises(BigDecimal.ZERO);
		avoirClient.setMtTotalRemises(avoirClient.getMtTotalRemises().subtract(selected.getMtTotalRemise()));
		if(avoirClient.getMtTotalFodec()==null) avoirClient.setMtTotalFodec(BigDecimal.ZERO);
		avoirClient.setMtTotalFodec(avoirClient.getMtTotalFodec().subtract(selected.getMtTotalFodec()));
		if(avoirClient.getMtTotalTtc()==null) avoirClient.setMtTotalTtc(BigDecimal.ZERO);
		avoirClient.setMtTotalTtc(avoirClient.getMtTotalTtc().subtract(selected.getMtTotalTtc()));
		if(avoirClient.getNetApayer()==null) avoirClient.setNetApayer(BigDecimal.ZERO);
		avoirClient.setNetApayer(avoirClient.getNetApayer().subtract(selected.getMtTotalTtc()));
		if(avoirClient.getPoidsTotalNet()==null) avoirClient.setPoidsTotalNet(BigDecimal.ZERO);
		avoirClient.setPoidsTotalNet(avoirClient.getPoidsTotalNet().subtract(selected.getPoidsTotalNet()));
		if(avoirClient.getPoidsTotalBrut()==null) avoirClient.setPoidsTotalBrut(BigDecimal.ZERO);
		avoirClient.setPoidsTotalBrut(avoirClient.getPoidsTotalBrut().subtract(selected.getPoidsTotalBrut()));
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:pnl_totaux");
	}
	
	public void getClientOfAC(Client client)
	{
		avoirClient=getAvoirClientActuel();
		//Client client=clientListController.getClient();
		avoirClient.setCclient(client.getCclient());
		avoirClient.setDesAdresseClient(client.getDesAdresse());
		avoirClient.setCodePostalClient(client.getCodePostal());
		avoirClient.setVilleClient(client.getVille());
		avoirClient.setPaysClient(client.getPays());
		
	}
	
	public void selectedAvoirClient(AvoirClient art)
	{
		getClientOfAC(clientListController.findClientById(art.getCclient()));
		setAvoirClient(art);
		avoirClientController.setAvoirClient(art);
		
		avoirClientController.modif=true;
		avoirClientController.avoircltstc=art;
		List<DetailAvoirClient> listeDetail=findDetailOfAvClt(avoirClient.getCavoirClient());
		listedetailarticles=listeDetail;
		setListeavoirclient(listeDetail);
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getApplication().getNavigationHandler().handleNavigation(context, null, "/FicheAvoirClient_V1.xhtml");
	}
	
	public void nouveauDevis()
	{
		
		listeavoirclient.clear();
		avoirClientController.setAvoirClient(new AvoirClient());
		avoirClientController.getbonAvoirClientId();
		avoirClientController.getAvoirClient().setDateCreation(new Date());
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:cdevisClient");
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
		context.update("formPrincipal:clt_RS");
		context.update("formPrincipal:clt_Add");
	}
	
	public void remove(DetailAvoirClient detailAv) {
		detailAvoirClientService.remove(detailAv);
	}
	
	public void removeLisDetail(AvoirClient dc)
	{
		List<DetailAvoirClient>list=findDetailOfAvClt(dc.getCavoirClient());
		for(int i=0;i<list.size();i++)
		{
			remove(list.get(i));
		}
		avoirClientController.remove(dc);
	}
	
	public void saveListe()throws JRException, IOException
	{
		avoirClient=getAvoirClientActuel();
		avoirClient.setMtTotalTtc(avoirClient.getMtTotalTtc().setScale(3, BigDecimal.ROUND_UP));
		avoirClient.setMtTotalTva(avoirClient.getMtTotalTva().setScale(3, BigDecimal.ROUND_UP));
		avoirClient.setNetApayer(avoirClient.getNetApayer().setScale(3, BigDecimal.ROUND_UP));
		
		
		
		if(listedetailarticles!=null)
			listeavoirclient=listedetailarticles;
		if(avoirClientController.modif==true)
		{
			List<DetailAvoirClient>list=findDetailOfAvClt(avoirClient.getCavoirClient());
			for(int i=0;i<list.size();i++)
			{
				remove(list.get(i));
			}
		}
		
		avoirClientController.save();
		detailAvoirClientService.saveList(listeavoirclient);
		
		Article article1=new Article();
		for(DetailAvoirClient dbl : listeavoirclient)
		{
			article1=articleService.findArticleById(dbl.getCarticle());
			article1.setQteStock(article1.getQteStock().add(dbl.getQuantiteConsommer()));
			articleService.save(article1);
		}
		
		
		detailAvoirClient=new DetailAvoirClient();
		PDF();
		listedetailarticles.clear();	
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Les details du bon de caisse sont Enregistrés!", null));
	}
	
	public List<DetailAvoirClient> findDetailOfAvClt (String t)
	{
		return detailAvoirClientService.findDetailOfAvClt(t);
	}
	
	public String retournChiffreToLettre(BigDecimal bd)
	{
		NumberFormat formatter = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT); 
   	 	String resultatChiffre = formatter.format(bd);
   	 	return resultatChiffre;
	}
	
	JasperPrint jasperPrint;  
    public void init() throws JRException{  
    	//listedetaildevis = detailDevisClientService.findAll();
    	String resultatChiffre;
    	String idClient=null;
    	if(listedetailarticles!=null)
    		listeavoirclient = listedetailarticles;
    	 idClient=avoirClient.getCclient();
    	 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 String dmy = dmyFormat.format(avoirClient.getDateCreation());
    	 
    	 
    	 String[] parts = avoirClient.getNetApayer().toString().split("\\.");
    	 String part1 = parts[0];
    	 String part2 = parts[1];
    	 BigDecimal bdc1=new BigDecimal(part1);
    	 part1=retournChiffreToLettre(bdc1);
    	 BigDecimal bdc2=new BigDecimal(part2);
    	 part2=retournChiffreToLettre(bdc2);		 
    	 resultatChiffre=part1+ " dinars "+part2+" millimes ";
    	 
    	 Map parameters = new HashMap();
    	 parameters.put("idClient", idClient);
    	 parameters.put("dateCreation",dmy);
    	 parameters.put("totalHtBrut",avoirClient.getMtTotalVente());
    	 parameters.put("totalHtNet",avoirClient.getMtTotalHt());
    	 parameters.put("totalRemise",avoirClient.getMtTotalRemises());
    	 parameters.put("totalFodec",avoirClient.getMtTotalFodec());
    	 parameters.put("totalTva",avoirClient.getMtTotalTva());
    	 parameters.put("totalTtc",avoirClient.getMtTotalTtc());
    	 parameters.put("netapayer",avoirClient.getNetApayer());
    	 parameters.put("resultatChiffre",resultatChiffre);
    	 
    	 Client client=clientListController.findClientById(idClient);
    	 parameters.put("raisocial",client.getRaisonSociale());
    	 parameters.put("adresse",client.getDesAdresse());
    	 parameters.put("tel",client.getTel());
    	 parameters.put("gsm",client.getGsm());
    	 parameters.put("fax",client.getFax());
    	 parameters.put("mf",client.getMatFiscale());
    	 parameters.put("rc",client.getNregistreCommerce());
    	 
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listeavoirclient);  
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/reportac_V1.jasper");
        jasperPrint=JasperFillManager.fillReport(reportPath, parameters,beanCollectionDataSource);
      //jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);  
    } 
    
    public void PDF() throws JRException, IOException{  
        init();  
        HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
        ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
        //JasperExportManager.exportReportToPdfFile(jasperPrint, "report.pdf");
        FacesContext.getCurrentInstance().responseComplete();  
    }
	
    public void TraitementArticle()
	{
		Article art=articleListController.findArticleById(detailAvoirClient.getCarticle());
		int result=art.getQteStock().compareTo(detailAvoirClient.getQuantiteConsommer());
		if(result==1||result==0)
		{
			updateDetailListe();
		}
		else
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('infoQtStock').show();");
		}
	}
    
    public  void updateDetailListe()
   	{
   		BigDecimal bd = new BigDecimal(100);
   		Article art=articleListController.findArticleById(detailAvoirClient.getCarticle());
   		Tva tva_article=tvaListController.findTvaById(art.getCtva());
   		avoirClient=getAvoirClientActuel();
   		
   		detailAvoirClient.setDesArticle(art.getDesArticle());
   		detailAvoirClient.setPrixUniteVente(art.getPrixVente());
   		if(detailAvoirClient.getTauxRemise().equals(BigDecimal.ZERO))
   		{	detailAvoirClient.setMtUniteRemise(BigDecimal.ZERO);
   		detailAvoirClient.setPrixUniteHt(art.getPrixVente());
   		}
   		else
   		{
   			detailAvoirClient.setMtUniteRemise((detailAvoirClient.getPrixUniteVente().multiply(detailAvoirClient.getTauxRemise())).divide(bd));
   			detailAvoirClient.setPrixUniteHt(detailAvoirClient.getPrixUniteVente().subtract(detailAvoirClient.getMtUniteRemise()));
   		}
   		BigDecimal baseTva=detailAvoirClient.getPrixUniteHt();
   		if(avoirClient.isFodec())
   		{
   			detailAvoirClient.setMtUniteFodec((detailAvoirClient.getPrixUniteHt()).divide(bd));
   			baseTva=detailAvoirClient.getPrixUniteHt().add(detailAvoirClient.getMtUniteFodec());
   			detailAvoirClient.setMtTotalFodec(detailAvoirClient.getMtUniteFodec().multiply(detailAvoirClient.getQuantiteConsommer()));
   			
   		}
   		else
   		{
   			detailAvoirClient.setMtUniteFodec(BigDecimal.ZERO);
   			detailAvoirClient.setMtTotalFodec(BigDecimal.ZERO);
   			
   		}
   		if(art.getPoidsNet()==null)
   		{
   			detailAvoirClient.setPoidsUniteNet(BigDecimal.ZERO);
   			detailAvoirClient.setPoidsTotalNet(BigDecimal.ZERO);
   		}
   		else
   		{	
   			detailAvoirClient.setPoidsUniteNet(art.getPoidsNet());
   			detailAvoirClient.setPoidsTotalNet(detailAvoirClient.getPoidsUniteNet().multiply(detailAvoirClient.getQuantiteConsommer()));
   		}
   		if(art.getPoidsBrut()==null)
   		{
   			detailAvoirClient.setPoidsUniteBrut(BigDecimal.ZERO);
   			detailAvoirClient.setPoidsTotalBrut(BigDecimal.ZERO);
   		}
   		else
   		{
   			detailAvoirClient.setPoidsUniteBrut(art.getPoidsBrut());
   			detailAvoirClient.setPoidsTotalBrut(detailAvoirClient.getPoidsUniteBrut().multiply(detailAvoirClient.getQuantiteConsommer()));
   		}
   		
   		detailAvoirClient.setCtva(art.getCtva());
   		detailAvoirClient.setTauxTva(tva_article.getTauxTva());
   		detailAvoirClient.setMtTotalHt(detailAvoirClient.getPrixUniteHt().multiply(detailAvoirClient.getQuantiteConsommer()));
   		detailAvoirClient.setCunite(art.getCunite());
   		detailAvoirClient.setPrixRevientUnite(art.getPrixAchat());
   		detailAvoirClient.setMtUniteTva((baseTva.multiply(detailAvoirClient.getTauxTva())).divide(bd));
   		detailAvoirClient.setPrixUniteTtc(baseTva.add(detailAvoirClient.getMtUniteTva()));
   		detailAvoirClient.setMtTotalPrixVente(detailAvoirClient.getPrixUniteVente().multiply(detailAvoirClient.getQuantiteConsommer()));
   		detailAvoirClient.setMtTotalRemise(detailAvoirClient.getMtUniteRemise().multiply(detailAvoirClient.getQuantiteConsommer()));
   		detailAvoirClient.setMtTotalTva(detailAvoirClient.getMtUniteTva().multiply(detailAvoirClient.getQuantiteConsommer()));
   		detailAvoirClient.setMtTotalTtc(detailAvoirClient.getPrixUniteTtc().multiply(detailAvoirClient.getQuantiteConsommer()));
   		
   		if(avoirClient.getMtTotalVente()==null) avoirClient.setMtTotalVente(BigDecimal.ZERO);
   		avoirClient.setMtTotalVente(avoirClient.getMtTotalVente().add(detailAvoirClient.getMtTotalPrixVente()));
   		if(avoirClient.getMtTotalHt()==null) avoirClient.setMtTotalHt(BigDecimal.ZERO);
   		avoirClient.setMtTotalHt(avoirClient.getMtTotalHt().add(detailAvoirClient.getMtTotalHt()));
   		if(avoirClient.getMtTotalTva()==null) avoirClient.setMtTotalTva(BigDecimal.ZERO);
   		avoirClient.setMtTotalTva(avoirClient.getMtTotalTva().add(detailAvoirClient.getMtTotalTva()));
   		if(avoirClient.getMtTotalRemises()==null) avoirClient.setMtTotalRemises(BigDecimal.ZERO);
   		avoirClient.setMtTotalRemises(avoirClient.getMtTotalRemises().add(detailAvoirClient.getMtTotalRemise()));
   		if(avoirClient.getMtTotalFodec()==null) avoirClient.setMtTotalFodec(BigDecimal.ZERO);
   		avoirClient.setMtTotalFodec(avoirClient.getMtTotalFodec().add(detailAvoirClient.getMtTotalFodec()));
   		if(avoirClient.getMtTotalTtc()==null) avoirClient.setMtTotalTtc(BigDecimal.ZERO);
   		avoirClient.setMtTotalTtc(avoirClient.getMtTotalTtc().add(detailAvoirClient.getMtTotalTtc()));
   		if(avoirClient.getNetApayer()==null) avoirClient.setNetApayer(BigDecimal.ZERO);
   		avoirClient.setNetApayer(avoirClient.getNetApayer().add(detailAvoirClient.getMtTotalTtc()));
   		if(avoirClient.getPoidsTotalNet()==null) avoirClient.setPoidsTotalNet(BigDecimal.ZERO);
   		avoirClient.setPoidsTotalNet(avoirClient.getPoidsTotalNet().add(detailAvoirClient.getPoidsTotalNet()));
   		if(avoirClient.getPoidsTotalBrut()==null) avoirClient.setPoidsTotalBrut(BigDecimal.ZERO);
   		avoirClient.setPoidsTotalBrut(avoirClient.getPoidsTotalBrut().add(detailAvoirClient.getPoidsTotalBrut()));
   		
   		if(listedetailarticles!=null)
   			listeavoirclient=listedetailarticles;
   		
   		if(listeavoirclient.size()==0)
   			detailAvoirClient.setNligne(1);
		else
		{
			DetailAvoirClient ddc=new DetailAvoirClient();
			for( int i=0;i<listeavoirclient.size();i++)
			{
				ddc=listeavoirclient.get(i);
			}
			detailAvoirClient.setNligne(ddc.getNligne()+1);
		}
   		/*if(listeavoirclient==null)
   			detailAvoirClient.setNligne(1);
   		else
   			detailAvoirClient.setNligne(listeavoirclient.size()+1);
   		*/
   		
   		detailAvoirClient.setCavoirClient(avoirClient.getCavoirClient());
   		if(detailAvoirClient.getMtTotalMarge()==null)
   			detailAvoirClient.setMtTotalMarge(BigDecimal.ZERO);
   		if(detailAvoirClient.getMtUniteMarge()==null)
   			detailAvoirClient.setMtUniteMarge(BigDecimal.ZERO);
//   		if(detailAvoirClient.getQuantiteNonTransferer()==null)
//   			detailAvoirClient.setQuantiteNonTransferer(BigDecimal.ZERO);
//   		if(detailAvoirClient.getQuantiteTransferer()==null)
//   			detailAvoirClient.setQuantiteTransferer(BigDecimal.ZERO);
   		if(detailAvoirClient.getTauxConsommation()==null)
   			detailAvoirClient.setTauxConsommation(BigDecimal.ZERO);
   		if(detailAvoirClient.getTauxMarge()==null)
   			detailAvoirClient.setTauxMarge(BigDecimal.ZERO);
   		
   		
   		listeavoirclient.add(detailAvoirClient);
   		listedetailarticles=listeavoirclient;
   		RequestContext context = RequestContext.getCurrentInstance();
   		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
   		//context.update("listeArticle:detailTable");
   	}
}
