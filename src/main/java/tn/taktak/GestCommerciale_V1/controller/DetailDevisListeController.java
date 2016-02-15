package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
//import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent; 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;

import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.service.ArticleService;
import tn.taktak.GestCommerciale_V1.service.DetailDevisClientService;
import tn.taktak.GestCommerciale_V1.service.DevisClientService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped

public class DetailDevisListeController implements Serializable {

	@ManagedProperty(value="#{clientListController}")
	private ClientListController clientListController;
	
	@ManagedProperty(value="#{tvaListController}")
	private TvaListController tvaListController;
	
	@ManagedProperty("#{detailDevisClientService}")
	private DetailDevisClientService detailDevisClientService;
	
	@ManagedProperty("#{devisListeController}")
	private DevisListeController devisListeController;
		
	/*@ManagedProperty("#{articleListController}")
	private ArticleListController articleListController;*/
	
	//String idClient=null;
	@ManagedProperty("#{articleService}")
    private ArticleService articleService;
	
	@ManagedProperty("#{devisClientService}")
	private DevisClientService devisClientService;
	
    private List<DetailDevisClient> listedetaildevis;
    //public static List<DetailDevisClient> listedetaildevisStatic=null;
    public static List<DetailDevisClient> listedetailarticles=null;
	private DetailDevisClient detailDevis = new DetailDevisClient();
	DevisClient devis=null;
	DevisClient devisInit=null;
////	private DetailDevisClient selectedArticle = new DetailDevisClient();
	private DataTable datatableDetail;
	
	
	@PostConstruct
	public void loadDevisClient() {
		
		//listedetaildevis = detailDevisClientService.findAll();
		if(listedetailarticles!=null)
			listedetaildevis=listedetailarticles;
		else
		listedetaildevis=new ArrayList<DetailDevisClient>(); 
	}
	
	public void save() {
		detailDevisClientService.save(detailDevis);
		detailDevis=new DetailDevisClient();
		listedetaildevis = detailDevisClientService.findAll();
//		FacesContext.getCurrentInstance().addMessage
//		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " Devis Enregistré!", null));
	}
	
	public void nouveauDevis()
	{
		//listedetailarticles.clear();
		listedetaildevis.clear();
		//devisListeController.setDevis(new DevisClient());
		//devis=devisListeController.getDevis();
		devisListeController.setDevis(new DevisClient());
		//devisListeController.updateCompteur("DevisClient");
		devisListeController.getbonAchatId();
		devisListeController.getDevis().setDateCreation(new Date());
		//devisListeController.setDevis(devis);
		RequestContext context = RequestContext.getCurrentInstance();
		//context.update("formPrincipal");
		context.update("formPrincipal:cdevisClient");
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
		context.update("formPrincipal:clt_RS");
		context.update("formPrincipal:clt_Add");
	}
	
	//public void saveListe()throws JRException, IOException
	public void saveListe()
	{
		devis=getDevisActuel();
//		Client client=clientListController.getClient();
//		devis.setCclient(client.getCclient());
//		devis.setDesAdresseClient(client.getDesAdresse());
//		devis.setCodePostalClient(client.getCodePostal());
		getClientOfDevis(clientListController.findClientById(devis.getCclient()));
		
		devis.setMtTotalTtc(devis.getMtTotalTtc().setScale(3, BigDecimal.ROUND_UP));
		devis.setMtTotalTva(devis.getMtTotalTva().setScale(3, BigDecimal.ROUND_UP));
		devis.setNetApayer(devis.getNetApayer().setScale(3, BigDecimal.ROUND_UP));
		
		if(listedetailarticles!=null)
			listedetaildevis=listedetailarticles;
		if(devisListeController.modif==true)
		{
			List<DetailDevisClient>list=findDetailOfDevis(devis.getCdevisClient());
			for(int i=0;i<list.size();i++)
			{
				remove(list.get(i));
			}
		}
		
		devisInit=devis;
		devisListeController.save();
		
		/*if(listedetailarticles!=null)
			listedetaildevis=listedetailarticles;
		
		if(devisListeController.modif==true)
		{
			List<DetailDevisClient>list=findDetailOfDevis(dc.getCdevisClient());
			for(int i=0;i<list.size();i++)
			{
				remove(list.get(i));
			}
		}*/
		
		detailDevisClientService.saveList(listedetaildevis);
		
		
		/*listedetaildevisStatic.clear();
		for(DetailDevisClient ddc :listedetaildevis)
		{
			listedetaildevisStatic.add(ddc);
		}*/
		detailDevis=new DetailDevisClient();
		
		
		//listedetailarticles.clear();
		/*listedetaildevis.clear();
		devisListeController.setDevis(new DevisClient());
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal");
		*/
		
		try {
			PDF();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			//nouveauDevis();
		}
		
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Detail Devis Enregistré!", null));
		//nouveauDevis();
	}
	
	public void remove(DetailDevisClient detaildevis) {
		detailDevisClientService.remove(detaildevis);
	//	listedetaildevis = detailDevisClientService.findAll();
//		FacesContext.getCurrentInstance().addMessage
//			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Devis Supprimé!", null));
	}
	
	public void removeLisDetail(DevisClient dc)
	{
		List<DetailDevisClient>list=findDetailOfDevis(dc.getCdevisClient());
		for(int i=0;i<list.size();i++)
		{
			remove(list.get(i));
		}
		devisListeController.remove(dc);
	}
	//delete article from table detailarticle and from listedetaildevis
	public void deleteArticle(DetailDevisClient selected)
	{
		listedetaildevis.remove(selected);
		
		devis=getDevisActuel();
		if(devis.getMtTotalVente()==null) devis.setMtTotalVente(BigDecimal.ZERO);
		devis.setMtTotalVente(devis.getMtTotalVente().subtract(selected.getMtTotalPrixVente()));
		if(devis.getMtTotalHt()==null) devis.setMtTotalHt(BigDecimal.ZERO);
		devis.setMtTotalHt(devis.getMtTotalHt().subtract(selected.getMtTotalHt()));
		if(devis.getMtTotalTva()==null) devis.setMtTotalTva(BigDecimal.ZERO);
		devis.setMtTotalTva(devis.getMtTotalTva().subtract(selected.getMtTotalTva()));
		if(devis.getMtTotalRemises()==null) devis.setMtTotalRemises(BigDecimal.ZERO);
		devis.setMtTotalRemises(devis.getMtTotalRemises().subtract(selected.getMtTotalRemise()));
		if(devis.getMtTotalFodec()==null) devis.setMtTotalFodec(BigDecimal.ZERO);
		devis.setMtTotalFodec(devis.getMtTotalFodec().subtract(selected.getMtTotalFodec()));
		if(devis.getMtTotalTtc()==null) devis.setMtTotalTtc(BigDecimal.ZERO);
		devis.setMtTotalTtc(devis.getMtTotalTtc().subtract(selected.getMtTotalTtc()));
		if(devis.getNetApayer()==null) devis.setNetApayer(BigDecimal.ZERO);
		devis.setNetApayer(devis.getNetApayer().subtract(selected.getMtTotalTtc()));
		if(devis.getPoidsTotalNet()==null) devis.setPoidsTotalNet(BigDecimal.ZERO);
		devis.setPoidsTotalNet(devis.getPoidsTotalNet().subtract(selected.getPoidsTotalNet()));
		if(devis.getPoidsTotalBrut()==null) devis.setPoidsTotalBrut(BigDecimal.ZERO);
		devis.setPoidsTotalBrut(devis.getPoidsTotalBrut().subtract(selected.getPoidsTotalBrut()));
		
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:pnl_totaux");
	}	

	public void getClientOfDevis(Client client)
	{
		devis=getDevisActuel();
		//Client client=clientListController.getClient();
		devis.setCclient(client.getCclient());
		devis.setDesAdresseClient(client.getDesAdresse());
		devis.setCodePostalClient(client.getCodePostal());
		devis.setVilleClient(client.getVille());
		devis.setPaysClient(client.getPays());
		
	}
	
	public void selectedDevis(DevisClient art) 
	{
		getClientOfDevis(clientListController.findClientById(art.getCclient()));
		setDevis(art);
		devisListeController.setDevis(art);
		
		devisListeController.modif=true;
		devisListeController.devisstc=art;
		List<DetailDevisClient> listeDetail=findDetailOfDevis(devis.getCdevisClient());
		listedetailarticles=listeDetail;
		setListedetaildevis(listeDetail);
		
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.getApplication().getNavigationHandler().handleNavigation(context, null, "/Fichedevisclient.xhtml");
	}
	
	public void redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Fichedevisclient.xhtml"));
        try {
        	extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        }
	}
	
	public List<DetailDevisClient> findDetailOfDevis (String t)
	{
		return detailDevisClientService.findDetailOfDevis(t);
	}
	
	public void clear()
	{
		detailDevis=new DetailDevisClient();
	}
	
	public DevisClient getDevisActuel()
	{
		return devisListeController.getDevis();
	}
	//*Mettre à jour la liste d'article du detail d'un devis client.
	
	
	public void TraitementArticle()
	{
		Article art=articleService.findArticleById(detailDevis.getCarticle());
		int result=art.getQteStock().compareTo(detailDevis.getQuantiteConsommer());
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
		//Article art=articleService.findArticleById(detailDevis.getCarticle());
		Article art=articleService.findArticleById(detailDevis.getCarticle());
		Tva tva_article=tvaListController.findTvaById(art.getCtva());
		devis=getDevisActuel();
			
		detailDevis.setDesArticle(art.getDesArticle());
		detailDevis.setPrixUniteVente(art.getPrixVente());
		if(detailDevis.getTauxRemise().equals(BigDecimal.ZERO))
		{	detailDevis.setMtUniteRemise(BigDecimal.ZERO);
			detailDevis.setPrixUniteHt(art.getPrixVente());
		}
		else
		{
			detailDevis.setMtUniteRemise((detailDevis.getPrixUniteVente().multiply(detailDevis.getTauxRemise())).divide(bd));
			detailDevis.setPrixUniteHt(detailDevis.getPrixUniteVente().subtract(detailDevis.getMtUniteRemise()));
		}
		BigDecimal baseTva=detailDevis.getPrixUniteHt();
		if(devis.isFodec())
		{
			detailDevis.setMtUniteFodec((detailDevis.getPrixUniteHt()).divide(bd));
			baseTva=detailDevis.getPrixUniteHt().add(detailDevis.getMtUniteFodec());
			detailDevis.setMtTotalFodec(detailDevis.getMtUniteFodec().multiply(detailDevis.getQuantiteConsommer()));
			
		}
		else
		{
			detailDevis.setMtUniteFodec(BigDecimal.ZERO);
			detailDevis.setMtTotalFodec(BigDecimal.ZERO);
			
		}
		if(art.getPoidsNet()==null)
		{
			detailDevis.setPoidsUniteNet(BigDecimal.ZERO);
			detailDevis.setPoidsTotalNet(BigDecimal.ZERO);
		}
		else
		{	
			detailDevis.setPoidsUniteNet(art.getPoidsNet());
			detailDevis.setPoidsTotalNet(detailDevis.getPoidsUniteNet().multiply(detailDevis.getQuantiteConsommer()));
		}
		if(art.getPoidsBrut()==null)
		{
			detailDevis.setPoidsUniteBrut(BigDecimal.ZERO);
			detailDevis.setPoidsTotalBrut(BigDecimal.ZERO);
		}
		else
		{
			detailDevis.setPoidsUniteBrut(art.getPoidsBrut());
			detailDevis.setPoidsTotalBrut(detailDevis.getPoidsUniteBrut().multiply(detailDevis.getQuantiteConsommer()));
		}
		detailDevis.setCtva(art.getCtva());
		detailDevis.setTauxTva(tva_article.getTauxTva());
		detailDevis.setMtTotalHt(detailDevis.getPrixUniteHt().multiply(detailDevis.getQuantiteConsommer()));
		detailDevis.setCuniteConsommer(art.getCunite());
		detailDevis.setPrixRevientUnite(art.getPrixAchat());
		detailDevis.setMtUniteTva((baseTva.multiply(detailDevis.getTauxTva())).divide(bd));
		detailDevis.setPrixUniteTtc(baseTva.add(detailDevis.getMtUniteTva()));
		detailDevis.setMtTotalPrixVente(detailDevis.getPrixUniteVente().multiply(detailDevis.getQuantiteConsommer()));
		detailDevis.setMtTotalRemise(detailDevis.getMtUniteRemise().multiply(detailDevis.getQuantiteConsommer()));
		detailDevis.setMtTotalTva(detailDevis.getMtUniteTva().multiply(detailDevis.getQuantiteConsommer()));
		detailDevis.setMtTotalTtc(detailDevis.getPrixUniteTtc().multiply(detailDevis.getQuantiteConsommer()));
		
		if(devis.getMtTotalVente()==null) devis.setMtTotalVente(BigDecimal.ZERO);
		devis.setMtTotalVente(devis.getMtTotalVente().add(detailDevis.getMtTotalPrixVente()));
		if(devis.getMtTotalHt()==null) devis.setMtTotalHt(BigDecimal.ZERO);
		devis.setMtTotalHt(devis.getMtTotalHt().add(detailDevis.getMtTotalHt()));
		if(devis.getMtTotalTva()==null) devis.setMtTotalTva(BigDecimal.ZERO);
		devis.setMtTotalTva(devis.getMtTotalTva().add(detailDevis.getMtTotalTva()));
		if(devis.getMtTotalRemises()==null) devis.setMtTotalRemises(BigDecimal.ZERO);
		devis.setMtTotalRemises(devis.getMtTotalRemises().add(detailDevis.getMtTotalRemise()));
		if(devis.getMtTotalFodec()==null) devis.setMtTotalFodec(BigDecimal.ZERO);
		devis.setMtTotalFodec(devis.getMtTotalFodec().add(detailDevis.getMtTotalFodec()));
		if(devis.getMtTotalTtc()==null) devis.setMtTotalTtc(BigDecimal.ZERO);
		devis.setMtTotalTtc(devis.getMtTotalTtc().add(detailDevis.getMtTotalTtc()));
		if(devis.getNetApayer()==null) devis.setNetApayer(BigDecimal.ZERO);
		devis.setNetApayer(devis.getNetApayer().add(detailDevis.getMtTotalTtc()));
		if(devis.getPoidsTotalNet()==null) devis.setPoidsTotalNet(BigDecimal.ZERO);
		devis.setPoidsTotalNet(devis.getPoidsTotalNet().add(detailDevis.getPoidsTotalNet()));
		if(devis.getPoidsTotalBrut()==null) devis.setPoidsTotalBrut(BigDecimal.ZERO);
		devis.setPoidsTotalBrut(devis.getPoidsTotalBrut().add(detailDevis.getPoidsTotalBrut()));
		
		if(listedetailarticles!=null)
			listedetaildevis=listedetailarticles;
		
		if(listedetaildevis.size()==0)
			detailDevis.setNligne(1);
		else
		{
			DetailDevisClient ddc=new DetailDevisClient();
			for( int i=0;i<listedetaildevis.size();i++)
			{
				ddc=listedetaildevis.get(i);
			}
			detailDevis.setNligne(ddc.getNligne()+1);
		}
		
		detailDevis.setCdevisClient(devis.getCdevisClient());
		if(detailDevis.getMtTotalMarge()==null)
			detailDevis.setMtTotalMarge(BigDecimal.ZERO);
		if(detailDevis.getMtUniteMarge()==null)
			detailDevis.setMtUniteMarge(BigDecimal.ZERO);
		if(detailDevis.getQuantiteNonTransferer()==null)
			detailDevis.setQuantiteNonTransferer(BigDecimal.ZERO);
		if(detailDevis.getQuantiteTransferer()==null)
			detailDevis.setQuantiteTransferer(BigDecimal.ZERO);
		if(detailDevis.getTauxConsommation()==null)
			detailDevis.setTauxConsommation(BigDecimal.ZERO);
		if(detailDevis.getTauxMarge()==null)
			detailDevis.setTauxMarge(BigDecimal.ZERO);
		
		listedetaildevis.add(detailDevis);
		listedetailarticles=listedetaildevis;
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
	
	}
	
	public String retournChiffreToLettre(BigDecimal bd)
	{
		NumberFormat formatter = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT); 
   	 	String resultatChiffre = formatter.format(bd);
   	 	return resultatChiffre;
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
    
	/*public void PDF() {  
        init();  
        HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
        ServletOutputStream servletOutputStream=null;
		try {
			servletOutputStream = httpServletResponse.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
        try {
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //JasperExportManager.exportReportToPdfFile(jasperPrint, "report.pdf");
        FacesContext.getCurrentInstance().responseComplete();  
        
    }*/

    ///Methode Init principal
/*    JasperPrint jasperPrint;  
    public void init() {  
    	//listedetaildevis = detailDevisClientService.findAll();
    	String resultatChiffre;
    	String idClient=null;
    	if(listedetailarticles!=null)
    		listedetaildevis = listedetailarticles;
    	 idClient=devis.getCclient();
    	 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 String dmy = dmyFormat.format(devis.getDateCreation());
    	 
    	 String[] parts = devis.getNetApayer().toString().split("\\.");
    	 
    	 //String split[] = StringUtils.split(devis.getNetApayer().toString(),"-");
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
    	 parameters.put("totalHtBrut",devis.getMtTotalVente());
    	 parameters.put("totalHtNet",devis.getMtTotalHt());
    	 parameters.put("totalRemise",devis.getMtTotalRemises());
    	 parameters.put("totalFodec",devis.getMtTotalFodec());
    	 parameters.put("totalTva",devis.getMtTotalTva());
    	 parameters.put("totalTtc",devis.getMtTotalTtc());
    	 parameters.put("netapayer",devis.getNetApayer());
    	 parameters.put("resultatChiffre",resultatChiffre);
    	 
    	 Client client=clientListController.findClientById(idClient);
    	 parameters.put("raisocial",client.getRaisonSociale());
    	 parameters.put("adresse",client.getDesAdresse());
    	 parameters.put("tel",client.getTel());
    	 parameters.put("gsm",client.getGsm());
    	 parameters.put("fax",client.getFax());
    	 parameters.put("mf",client.getMatFiscale());
    	 parameters.put("rc",client.getNregistreCommerce());
    	 
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listedetaildevis);  
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/reportdcV1.jasper");
        try {
			jasperPrint=JasperFillManager.fillReport(reportPath, parameters,beanCollectionDataSource);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);  
    }*/

    
    JasperPrint jasperPrint;  
    public void init() throws JRException{  
    	//listedetaildevis = detailDevisClientService.findAll();
    	String resultatChiffre;
    	String idClient=null;
    	if(listedetailarticles!=null)
    		listedetaildevis = listedetailarticles;
    	 idClient=devisInit.getCclient();
    	 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 String dmy = dmyFormat.format(devisInit.getDateCreation());
    	 
    	 String[] parts = devisInit.getNetApayer().toString().split("\\.");
    	 
    	 //String split[] = StringUtils.split(devis.getNetApayer().toString(),"-");
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
    	 parameters.put("totalHtBrut",devisInit.getMtTotalVente());
    	 parameters.put("totalHtNet",devisInit.getMtTotalHt());
    	 parameters.put("totalRemise",devisInit.getMtTotalRemises());
    	 parameters.put("totalFodec",devisInit.getMtTotalFodec());
    	 parameters.put("totalTva",devisInit.getMtTotalTva());
    	 parameters.put("totalTtc",devisInit.getMtTotalTtc());
    	 parameters.put("netapayer",devisInit.getNetApayer());
    	 parameters.put("resultatChiffre",resultatChiffre);
    	 
    	 Client client=clientListController.findClientById(idClient);
    	 parameters.put("raisocial",client.getRaisonSociale());
    	 parameters.put("adresse",client.getDesAdresse());
    	 parameters.put("tel",client.getTel());
    	 parameters.put("gsm",client.getGsm());
    	 parameters.put("fax",client.getFax());
    	 parameters.put("mf",client.getMatFiscale());
    	 parameters.put("rc",client.getNregistreCommerce());
    	 
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listedetaildevis);  
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/reportdcV1.jasper");
        jasperPrint=JasperFillManager.fillReport(reportPath, parameters,beanCollectionDataSource);
      //jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);  
    }
        
}
