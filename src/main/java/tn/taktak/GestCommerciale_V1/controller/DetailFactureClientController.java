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
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.DetailBonLivraison;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DetailFactureClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.FactureClient;
import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.service.ArticleService;
import tn.taktak.GestCommerciale_V1.service.DetailFactureClientService;
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
public class DetailFactureClientController implements Serializable {


	@ManagedProperty("#{detailFactureClientService}")
	private DetailFactureClientService detailFactureClientService;
	
	@ManagedProperty("#{articleListController}")
	private ArticleListController articleListController;
	
	@ManagedProperty(value="#{tvaListController}")
	private TvaListController tvaListController;
	
	@ManagedProperty("#{factureClientController}")
	private FactureClientController factureClientController;
		
	@ManagedProperty(value="#{clientListController}")
	private ClientListController clientListController;
	

	@ManagedProperty("#{articleService}")
    private ArticleService articleService;
	
	
	private List<DetailFactureClient> listefactureclient;
	private DetailFactureClient detailFactureClient=new DetailFactureClient();
	private FactureClient factureClient=null;
	public static List<DetailFactureClient> listedetailarticles=null;
	
	@PostConstruct
	public void loadFactureClient() {
	
		if(listedetailarticles!=null)
			listefactureclient=listedetailarticles;
		else
			listefactureclient=new ArrayList<DetailFactureClient>(); 
	}
	
	public FactureClient getFactureClientActuel()
	{
		return factureClientController.getFactureClient();
	}
	
	public void deleteArticle(DetailFactureClient selected)
	{
		
		listefactureclient.remove(selected);
		
		factureClient=getFactureClientActuel();
		if(factureClient.getMtTotalVente()==null) factureClient.setMtTotalVente(BigDecimal.ZERO);
		factureClient.setMtTotalVente(factureClient.getMtTotalVente().subtract(selected.getMtTotalPrixVente()));
		if(factureClient.getMtTotalHt()==null) factureClient.setMtTotalHt(BigDecimal.ZERO);
		factureClient.setMtTotalHt(factureClient.getMtTotalHt().subtract(selected.getMtTotalHt()));
		if(factureClient.getMtTotalTva()==null) factureClient.setMtTotalTva(BigDecimal.ZERO);
		factureClient.setMtTotalTva(factureClient.getMtTotalTva().subtract(selected.getMtTotalTva()));
		if(factureClient.getMtTotalRemises()==null) factureClient.setMtTotalRemises(BigDecimal.ZERO);
		factureClient.setMtTotalRemises(factureClient.getMtTotalRemises().subtract(selected.getMtTotalRemise()));
		if(factureClient.getMtTotalFodec()==null) factureClient.setMtTotalFodec(BigDecimal.ZERO);
		factureClient.setMtTotalFodec(factureClient.getMtTotalFodec().subtract(selected.getMtTotalFodec()));
		if(factureClient.getMtTotalTtc()==null) factureClient.setMtTotalTtc(BigDecimal.ZERO);
		factureClient.setMtTotalTtc(factureClient.getMtTotalTtc().subtract(selected.getMtTotalTtc()));
		if(factureClient.getNetApayer()==null) factureClient.setNetApayer(BigDecimal.ZERO);
		factureClient.setNetApayer(factureClient.getNetApayer().subtract(selected.getMtTotalTtc()));
		if(factureClient.getPoidsTotalNet()==null) factureClient.setPoidsTotalNet(BigDecimal.ZERO);
		factureClient.setPoidsTotalNet(factureClient.getPoidsTotalNet().subtract(selected.getPoidsTotalNet()));
		if(factureClient.getPoidsTotalBrut()==null) factureClient.setPoidsTotalBrut(BigDecimal.ZERO);
		factureClient.setPoidsTotalBrut(factureClient.getPoidsTotalBrut().subtract(selected.getPoidsTotalBrut()));
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:pnl_totaux");
	}
	
	public void remove(DetailFactureClient detaildevis) {
		detailFactureClientService.remove(detaildevis);

	}
	
	public void removeLisDetail(FactureClient dc)
	{
		List<DetailFactureClient>list=findDetailOfFacture(dc.getCfactureClient());
		for(int i=0;i<list.size();i++)
		{
			remove(list.get(i));
		}
		factureClientController.remove(dc);
	}
	
	public void selectedFacture(FactureClient art) 
	{
		getClientOfFacture(clientListController.findClientById(art.getCclient()));
		setFactureClient(art);
		factureClientController.setFactureClient(art);
		
		factureClientController.modif=true;
		factureClientController.facturestc=art;
		List<DetailFactureClient> listeDetail=findDetailOfFacture(factureClient.getCfactureClient());
		listedetailarticles=listeDetail;
		setListefactureclient(listeDetail);
		
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.getApplication().getNavigationHandler().handleNavigation(context, null, "/FactureClient.xhtml");
	}
	
	public List<DetailFactureClient> findDetailOfFacture (String t)
	{
		return detailFactureClientService.findDetailOfFacture(t);
	}
	
	public void getClientOfFacture(Client client)
	{
		factureClient=getFactureClientActuel();
		//Client client=clientListController.getClient();
		factureClient.setCclient(client.getCclient());
		factureClient.setDesAdresseClient(client.getDesAdresse());
		factureClient.setCodePostalClient(client.getCodePostal());
		factureClient.setVilleClient(client.getVille());
		factureClient.setPaysClient(client.getPays());
		
	}
	
	public void nouveauDevis()
	{
		//listedetailarticles.clear();
		listefactureclient.clear();
		//devisListeController.setDevis(new DevisClient());
		//devis=devisListeController.getDevis();
		factureClientController.setFactureClient(new FactureClient());
		//devisListeController.updateCompteur("DevisClient");
		factureClientController.getFactClientId();
		factureClientController.getFactureClient().setDateCreation(new Date());
		//devisListeController.setDevis(devis);
		RequestContext context = RequestContext.getCurrentInstance();
		//context.update("formPrincipal");
		context.update("formPrincipal:cdevisClient");
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
		context.update("formPrincipal:clt_RS");
		context.update("formPrincipal:clt_Add");
	}
	
	public void saveListe()throws JRException, IOException
	{
		factureClient=getFactureClientActuel();

		getClientOfFacture(clientListController.findClientById(factureClient.getCclient()));
		
		factureClient.setMtTotalTtc(factureClient.getMtTotalTtc().setScale(3, BigDecimal.ROUND_UP));
		factureClient.setMtTotalTva(factureClient.getMtTotalTva().setScale(3, BigDecimal.ROUND_UP));
		factureClient.setNetApayer(factureClient.getNetApayer().setScale(3, BigDecimal.ROUND_UP));
	
		if(listedetailarticles!=null)
			listefactureclient=listedetailarticles;
		
		if(factureClientController.modif==true)
		{
			List<DetailFactureClient>list=findDetailOfFacture(factureClient.getCfactureClient());
			for(int i=0;i<list.size();i++)
			{
				remove(list.get(i));
			}
		}
		
		factureClientController.save();
		detailFactureClientService.saveList(listefactureclient);
		
		Article article1=new Article();
		for(DetailFactureClient dbl : listefactureclient)
		{
			article1=articleService.findArticleById(dbl.getCarticle());
			article1.setQteStock(article1.getQteStock().subtract(dbl.getQuantiteConsommer()));
			articleService.save(article1);
		}
		
		detailFactureClient=new DetailFactureClient();
		PDF();
		listedetailarticles.clear();	
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Les details du bon de caisse sont Enregistrés!", null));
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
    		listefactureclient = listedetailarticles;
    	 idClient=factureClient.getCclient();
    	 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 String dmy = dmyFormat.format(factureClient.getDateCreation());
    	 
    	 String[] parts = factureClient.getNetApayer().toString().split("\\.");
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
    	 parameters.put("totalHtBrut",factureClient.getMtTotalVente());
    	 parameters.put("totalHtNet",factureClient.getMtTotalHt());
    	 parameters.put("totalRemise",factureClient.getMtTotalRemises());
    	 parameters.put("totalFodec",factureClient.getMtTotalFodec());
    	 parameters.put("totalTva",factureClient.getMtTotalTva());
    	 parameters.put("totalTtc",factureClient.getMtTotalTtc());
    	 parameters.put("netapayer",factureClient.getNetApayer());
    	 parameters.put("resultatChiffre",resultatChiffre);
    	 
    	 Client client=clientListController.findClientById(idClient);
    	 parameters.put("raisocial",client.getRaisonSociale());
    	 parameters.put("adresse",client.getDesAdresse());
    	 parameters.put("tel",client.getTel());
    	 parameters.put("gsm",client.getGsm());
    	 parameters.put("fax",client.getFax());
    	 parameters.put("mf",client.getMatFiscale());
    	 parameters.put("rc",client.getNregistreCommerce());
    	 
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listefactureclient);  
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/reportfc_V1.jasper");
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
   		Article art=articleListController.findArticleById(detailFactureClient.getCarticle());
   		int result=art.getQteStock().compareTo(detailFactureClient.getQuantiteConsommer());
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
   		Article art=articleListController.findArticleById(detailFactureClient.getCarticle());
   		Tva tva_article=tvaListController.findTvaById(art.getCtva());
   		factureClient=getFactureClientActuel();
   		
   		detailFactureClient.setDesArticle(art.getDesArticle());
   		detailFactureClient.setPrixUniteVente(art.getPrixVente());
   		if(detailFactureClient.getTauxRemise().equals(BigDecimal.ZERO))
   		{	detailFactureClient.setMtUniteRemise(BigDecimal.ZERO);
   		detailFactureClient.setPrixUniteHt(art.getPrixVente());
   		}
   		else
   		{
   			detailFactureClient.setMtUniteRemise((detailFactureClient.getPrixUniteVente().multiply(detailFactureClient.getTauxRemise())).divide(bd));
   			detailFactureClient.setPrixUniteHt(detailFactureClient.getPrixUniteVente().subtract(detailFactureClient.getMtUniteRemise()));
   		}
   		BigDecimal baseTva=detailFactureClient.getPrixUniteHt();
   		if(factureClient.isFodec())
   		{
   			detailFactureClient.setMtUniteFodec((detailFactureClient.getPrixUniteHt()).divide(bd));
   			baseTva=detailFactureClient.getPrixUniteHt().add(detailFactureClient.getMtUniteFodec());
   			detailFactureClient.setMtTotalFodec(detailFactureClient.getMtUniteFodec().multiply(detailFactureClient.getQuantiteConsommer()));
   			
   		}
   		else
   		{
   			detailFactureClient.setMtUniteFodec(BigDecimal.ZERO);
   			detailFactureClient.setMtTotalFodec(BigDecimal.ZERO);
   			
   		}
   		if(art.getPoidsNet()==null)
   		{
   			detailFactureClient.setPoidsUniteNet(BigDecimal.ZERO);
   			detailFactureClient.setPoidsTotalNet(BigDecimal.ZERO);
   		}
   		else
   		{	
   			detailFactureClient.setPoidsUniteNet(art.getPoidsNet());
   			detailFactureClient.setPoidsTotalNet(detailFactureClient.getPoidsUniteNet().multiply(detailFactureClient.getQuantiteConsommer()));
   		}
   		if(art.getPoidsBrut()==null)
   		{
   			detailFactureClient.setPoidsUniteBrut(BigDecimal.ZERO);
   			detailFactureClient.setPoidsTotalBrut(BigDecimal.ZERO);
   		}
   		else
   		{
   			detailFactureClient.setPoidsUniteBrut(art.getPoidsBrut());
   			detailFactureClient.setPoidsTotalBrut(detailFactureClient.getPoidsUniteBrut().multiply(detailFactureClient.getQuantiteConsommer()));
   		}
   		
   		detailFactureClient.setCtva(art.getCtva());
   		detailFactureClient.setTauxTva(tva_article.getTauxTva());
   		detailFactureClient.setMtTotalHt(detailFactureClient.getPrixUniteHt().multiply(detailFactureClient.getQuantiteConsommer()));
   		detailFactureClient.setCunite(art.getCunite());
   		detailFactureClient.setPrixRevientUnite(art.getPrixAchat());
   		detailFactureClient.setMtUniteTva((baseTva.multiply(detailFactureClient.getTauxTva())).divide(bd));
   		detailFactureClient.setPrixUniteTtc(baseTva.add(detailFactureClient.getMtUniteTva()));
   		detailFactureClient.setMtTotalPrixVente(detailFactureClient.getPrixUniteVente().multiply(detailFactureClient.getQuantiteConsommer()));
   		detailFactureClient.setMtTotalRemise(detailFactureClient.getMtUniteRemise().multiply(detailFactureClient.getQuantiteConsommer()));
   		detailFactureClient.setMtTotalTva(detailFactureClient.getMtUniteTva().multiply(detailFactureClient.getQuantiteConsommer()));
   		detailFactureClient.setMtTotalTtc(detailFactureClient.getPrixUniteTtc().multiply(detailFactureClient.getQuantiteConsommer()));
   		
   		if(factureClient.getMtTotalVente()==null) factureClient.setMtTotalVente(BigDecimal.ZERO);
   		factureClient.setMtTotalVente(factureClient.getMtTotalVente().add(detailFactureClient.getMtTotalPrixVente()));
   		if(factureClient.getMtTotalHt()==null) factureClient.setMtTotalHt(BigDecimal.ZERO);
   		factureClient.setMtTotalHt(factureClient.getMtTotalHt().add(detailFactureClient.getMtTotalHt()));
   		if(factureClient.getMtTotalTva()==null) factureClient.setMtTotalTva(BigDecimal.ZERO);
   		factureClient.setMtTotalTva(factureClient.getMtTotalTva().add(detailFactureClient.getMtTotalTva()));
   		if(factureClient.getMtTotalRemises()==null) factureClient.setMtTotalRemises(BigDecimal.ZERO);
   		factureClient.setMtTotalRemises(factureClient.getMtTotalRemises().add(detailFactureClient.getMtTotalRemise()));
   		if(factureClient.getMtTotalFodec()==null) factureClient.setMtTotalFodec(BigDecimal.ZERO);
   		factureClient.setMtTotalFodec(factureClient.getMtTotalFodec().add(detailFactureClient.getMtTotalFodec()));
   		if(factureClient.getMtTotalTtc()==null) factureClient.setMtTotalTtc(BigDecimal.ZERO);
   		factureClient.setMtTotalTtc(factureClient.getMtTotalTtc().add(detailFactureClient.getMtTotalTtc()));
   		if(factureClient.getNetApayer()==null) factureClient.setNetApayer(BigDecimal.ZERO);
   		factureClient.setNetApayer(factureClient.getNetApayer().add(detailFactureClient.getMtTotalTtc()));
   		if(factureClient.getPoidsTotalNet()==null) factureClient.setPoidsTotalNet(BigDecimal.ZERO);
   		factureClient.setPoidsTotalNet(factureClient.getPoidsTotalNet().add(detailFactureClient.getPoidsTotalNet()));
   		if(factureClient.getPoidsTotalBrut()==null) factureClient.setPoidsTotalBrut(BigDecimal.ZERO);
   		factureClient.setPoidsTotalBrut(factureClient.getPoidsTotalBrut().add(detailFactureClient.getPoidsTotalBrut()));
   		
   		if(listedetailarticles!=null)
   			listefactureclient=listedetailarticles;
   		
   		if(listefactureclient.size()==0)
   			detailFactureClient.setNligne(1);
		else
		{
			DetailFactureClient ddc=new DetailFactureClient();
			for( int i=0;i<listefactureclient.size();i++)
			{
				ddc=listefactureclient.get(i);
			}
			detailFactureClient.setNligne(ddc.getNligne()+1);
		}
   		/*if(listefactureclient==null)
   			detailFactureClient.setNligne(1);
   		else
   			detailFactureClient.setNligne(listefactureclient.size()+1);*/
   		
   		
   		detailFactureClient.setCfactureClient(factureClient.getCfactureClient());
   		if(detailFactureClient.getMtTotalMarge()==null)
   			detailFactureClient.setMtTotalMarge(BigDecimal.ZERO);
   		if(detailFactureClient.getMtUniteMarge()==null)
   			detailFactureClient.setMtUniteMarge(BigDecimal.ZERO);
   		if(detailFactureClient.getQuantiteNonTransferer()==null)
   			detailFactureClient.setQuantiteNonTransferer(BigDecimal.ZERO);
   		if(detailFactureClient.getQuantiteTransferer()==null)
   			detailFactureClient.setQuantiteTransferer(BigDecimal.ZERO);
   		if(detailFactureClient.getTauxConsommation()==null)
   			detailFactureClient.setTauxConsommation(BigDecimal.ZERO);
   		if(detailFactureClient.getTauxMarge()==null)
   			detailFactureClient.setTauxMarge(BigDecimal.ZERO);
   		
   		
   		listefactureclient.add(detailFactureClient);
   		listedetailarticles=listefactureclient;
   		RequestContext context = RequestContext.getCurrentInstance();
   		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
   		//context.update("listeArticle:detailTable");
   	}
    
    
}
