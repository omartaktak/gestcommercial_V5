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
import tn.taktak.GestCommerciale_V1.entity.BonCaisse;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.DetailBonCaisse;
import tn.taktak.GestCommerciale_V1.entity.DetailBonLivraison;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DetailFactureClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.service.ArticleService;
import tn.taktak.GestCommerciale_V1.service.DetailBonCaisseService;
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
public class DetailBonCaisseController implements Serializable {

	@ManagedProperty("#{detailBonCaisseService}")
	private DetailBonCaisseService detailBonCaisseService;
	
	@ManagedProperty("#{articleListController}")
	private ArticleListController articleListController;
	
	@ManagedProperty(value="#{tvaListController}")
	private TvaListController tvaListController;
	
	@ManagedProperty("#{bonCaisseController}")
	private BonCaisseController bonCaisseController;
		
	@ManagedProperty(value="#{clientListController}")
	private ClientListController clientListController;

	@ManagedProperty("#{articleService}")
    private ArticleService articleService;
	
	private List<DetailBonCaisse> listeboncaisse;
	private DetailBonCaisse detailBonCaisse=new DetailBonCaisse();
	private BonCaisse bonCaisse=null;
	public static List<DetailBonCaisse> listedetailarticles=null;
	
	@PostConstruct
	public void loadBonCaisse() {
	
		if(listedetailarticles!=null)
			listeboncaisse=listedetailarticles;
		else
			listeboncaisse=new ArrayList<DetailBonCaisse>(); 
	}
	
	public BonCaisse getBonCaisseActuel()
	{
		return bonCaisseController.getBonCaisse();
	}
	
	public void remove(DetailBonCaisse bc) {
		detailBonCaisseService.remove(bc);
	}
	
	public void removeLisDetail(BonCaisse dc)
	{
		List<DetailBonCaisse>list=findDetailOfBC(dc.getCbonCaisse());
		for(int i=0;i<list.size();i++)
		{
			remove(list.get(i));
		}
		bonCaisseController.remove(dc);
	}
	
	public void nouveauDevis()
	{
		
		listeboncaisse.clear();
		bonCaisseController.setBonCaisse(new BonCaisse());
		
		bonCaisseController.getbonCaisseId();
		bonCaisseController.getBonCaisse().setDateCreation(new Date());
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		context.update("formPrincipal:cdevisClient");
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
		context.update("formPrincipal:clt_RS");
		context.update("formPrincipal:clt_Add");
	}
	
	public void deleteArticle(DetailBonCaisse selected)
	{
		
		listeboncaisse.remove(selected);
		
		bonCaisse=getBonCaisseActuel();
		if(bonCaisse.getMtTotalVente()==null) bonCaisse.setMtTotalVente(BigDecimal.ZERO);
		bonCaisse.setMtTotalVente(bonCaisse.getMtTotalVente().subtract(selected.getMtTotalPrixVente()));
		if(bonCaisse.getMtTotalHt()==null) bonCaisse.setMtTotalHt(BigDecimal.ZERO);
		bonCaisse.setMtTotalHt(bonCaisse.getMtTotalHt().subtract(selected.getMtTotalHt()));
		if(bonCaisse.getMtTotalTva()==null) bonCaisse.setMtTotalTva(BigDecimal.ZERO);
		bonCaisse.setMtTotalTva(bonCaisse.getMtTotalTva().subtract(selected.getMtTotalTva()));
		if(bonCaisse.getMtTotalRemises()==null) bonCaisse.setMtTotalRemises(BigDecimal.ZERO);
		bonCaisse.setMtTotalRemises(bonCaisse.getMtTotalRemises().subtract(selected.getMtTotalRemise()));
		if(bonCaisse.getMtTotalFodec()==null) bonCaisse.setMtTotalFodec(BigDecimal.ZERO);
		bonCaisse.setMtTotalFodec(bonCaisse.getMtTotalFodec().subtract(selected.getMtTotalFodec()));
		if(bonCaisse.getMtTotalTtc()==null) bonCaisse.setMtTotalTtc(BigDecimal.ZERO);
		bonCaisse.setMtTotalTtc(bonCaisse.getMtTotalTtc().subtract(selected.getMtTotalTtc()));
		if(bonCaisse.getNetApayer()==null) bonCaisse.setNetApayer(BigDecimal.ZERO);
		bonCaisse.setNetApayer(bonCaisse.getNetApayer().subtract(selected.getMtTotalTtc()));
		if(bonCaisse.getPoidsTotalNet()==null) bonCaisse.setPoidsTotalNet(BigDecimal.ZERO);
		bonCaisse.setPoidsTotalNet(bonCaisse.getPoidsTotalNet().subtract(selected.getPoidsTotalNet()));
		if(bonCaisse.getPoidsTotalBrut()==null) bonCaisse.setPoidsTotalBrut(BigDecimal.ZERO);
		bonCaisse.setPoidsTotalBrut(bonCaisse.getPoidsTotalBrut().subtract(selected.getPoidsTotalBrut()));
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:pnl_totaux");
	}
	
	public void selectedBonCaisse(BonCaisse art) 
	{
		getClientOfBC(clientListController.findClientById(art.getCclient()));
		setBonCaisse(art);
		bonCaisseController.setBonCaisse(art);
		
		bonCaisseController.modif=true;
		bonCaisseController.bonCaissestc=art;
		List<DetailBonCaisse> listeDetail=findDetailOfBC(bonCaisse.getCbonCaisse());
		listedetailarticles=listeDetail;
		setListeboncaisse(listeDetail);
		
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.getApplication().getNavigationHandler().handleNavigation(context, null, "/Ficheboncaisse.xhtml");
	}
	
	public List<DetailBonCaisse> findDetailOfBC(String t)
	{
		return detailBonCaisseService.findDetailOfBC(t);
	}
	
	public void getClientOfBC(Client client)
	{
		bonCaisse=getBonCaisseActuel();
		//Client client=clientListController.getClient();
		bonCaisse.setCclient(client.getCclient());
		bonCaisse.setDesAdresseClient(client.getDesAdresse());
		bonCaisse.setCodePostalClient(client.getCodePostal());
		bonCaisse.setVilleClient(client.getVille());
		bonCaisse.setPaysClient(client.getPays());
		
	}
	
	public void saveListe()throws JRException, IOException
	{
		bonCaisse=getBonCaisseActuel();
		getClientOfBC(clientListController.findClientById(bonCaisse.getCclient()));
		
		bonCaisse.setMtTotalTtc(bonCaisse.getMtTotalTtc().setScale(3, BigDecimal.ROUND_UP));
		bonCaisse.setMtTotalTva(bonCaisse.getMtTotalTva().setScale(3, BigDecimal.ROUND_UP));
		bonCaisse.setNetApayer(bonCaisse.getNetApayer().setScale(3, BigDecimal.ROUND_UP));
	
		if(listedetailarticles!=null)
			listeboncaisse=listedetailarticles;
		
		if(bonCaisseController.modif==true)
		{
			List<DetailBonCaisse>list=findDetailOfBC(bonCaisse.getCbonCaisse());
			for(int i=0;i<list.size();i++)
			{
				remove(list.get(i));
			}
		}
		
		bonCaisseController.save();
		detailBonCaisseService.saveList(listeboncaisse);
		
		Article article1=new Article();
		for(DetailBonCaisse dbl : listeboncaisse)
		{
			article1=articleService.findArticleById(dbl.getCarticle());
			article1.setQteStock(article1.getQteStock().subtract(dbl.getQuantiteConsommer()));
			articleService.save(article1);
		}
		
		detailBonCaisse=new DetailBonCaisse();
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
    		listeboncaisse = listedetailarticles;
    	 idClient=bonCaisse.getCclient();
    	 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 String dmy = dmyFormat.format(bonCaisse.getDateCreation());
    	 
    	 String[] parts = bonCaisse.getNetApayer().toString().split("\\.");
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
    	 parameters.put("totalHtBrut",bonCaisse.getMtTotalVente());
    	 parameters.put("totalHtNet",bonCaisse.getMtTotalHt());
    	 parameters.put("totalRemise",bonCaisse.getMtTotalRemises());
    	 parameters.put("totalFodec",bonCaisse.getMtTotalFodec());
    	 parameters.put("totalTva",bonCaisse.getMtTotalTva());
    	 parameters.put("totalTtc",bonCaisse.getMtTotalTtc());
    	 parameters.put("netapayer",bonCaisse.getNetApayer());
    	 parameters.put("resultatChiffre",resultatChiffre);
    	 
    	 Client client=clientListController.findClientById(idClient);
    	 parameters.put("raisocial",client.getRaisonSociale());
    	 parameters.put("adresse",client.getDesAdresse());
    	 parameters.put("tel",client.getTel());
    	 parameters.put("gsm",client.getGsm());
    	 parameters.put("fax",client.getFax());
    	 parameters.put("mf",client.getMatFiscale());
    	 parameters.put("rc",client.getNregistreCommerce());
    	 
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listeboncaisse);  
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/reportbc_V1.jasper");
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
   		Article art=articleListController.findArticleById(detailBonCaisse.getCarticle());
   		int result=art.getQteStock().compareTo(detailBonCaisse.getQuantiteConsommer());
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
		Article art=articleListController.findArticleById(detailBonCaisse.getCarticle());
		Tva tva_article=tvaListController.findTvaById(art.getCtva());
		bonCaisse=getBonCaisseActuel();
		
		detailBonCaisse.setDesArticle(art.getDesArticle());
		detailBonCaisse.setPrixUniteVente(art.getPrixVente());
		if(detailBonCaisse.getTauxRemise().equals(BigDecimal.ZERO))
		{	detailBonCaisse.setMtUniteRemise(BigDecimal.ZERO);
		detailBonCaisse.setPrixUniteHt(art.getPrixVente());
		}
		else
		{
			detailBonCaisse.setMtUniteRemise((detailBonCaisse.getPrixUniteVente().multiply(detailBonCaisse.getTauxRemise())).divide(bd));
			detailBonCaisse.setPrixUniteHt(detailBonCaisse.getPrixUniteVente().subtract(detailBonCaisse.getMtUniteRemise()));
		}
		BigDecimal baseTva=detailBonCaisse.getPrixUniteHt();
		if(bonCaisse.isFodec())
		{
			detailBonCaisse.setMtUniteFodec((detailBonCaisse.getPrixUniteHt()).divide(bd));
			baseTva=detailBonCaisse.getPrixUniteHt().add(detailBonCaisse.getMtUniteFodec());
			detailBonCaisse.setMtTotalFodec(detailBonCaisse.getMtUniteFodec().multiply(detailBonCaisse.getQuantiteConsommer()));
			
		}
		else
		{
			detailBonCaisse.setMtUniteFodec(BigDecimal.ZERO);
			detailBonCaisse.setMtTotalFodec(BigDecimal.ZERO);
			
		}
		if(art.getPoidsNet()==null)
		{
			detailBonCaisse.setPoidsUniteNet(BigDecimal.ZERO);
			detailBonCaisse.setPoidsTotalNet(BigDecimal.ZERO);
		}
		else
		{	
			detailBonCaisse.setPoidsUniteNet(art.getPoidsNet());
			detailBonCaisse.setPoidsTotalNet(detailBonCaisse.getPoidsUniteNet().multiply(detailBonCaisse.getQuantiteConsommer()));
		}
		if(art.getPoidsBrut()==null)
		{
			detailBonCaisse.setPoidsUniteBrut(BigDecimal.ZERO);
			detailBonCaisse.setPoidsTotalBrut(BigDecimal.ZERO);
		}
		else
		{
			detailBonCaisse.setPoidsUniteBrut(art.getPoidsBrut());
			detailBonCaisse.setPoidsTotalBrut(detailBonCaisse.getPoidsUniteBrut().multiply(detailBonCaisse.getQuantiteConsommer()));
		}
		
		detailBonCaisse.setCtva(art.getCtva());
		detailBonCaisse.setTauxTva(tva_article.getTauxTva());
		detailBonCaisse.setMtTotalHt(detailBonCaisse.getPrixUniteHt().multiply(detailBonCaisse.getQuantiteConsommer()));
		detailBonCaisse.setCunite(art.getCunite());
		detailBonCaisse.setPrixRevientUnite(art.getPrixAchat());
		detailBonCaisse.setMtUniteTva((baseTva.multiply(detailBonCaisse.getTauxTva())).divide(bd));
		detailBonCaisse.setPrixUniteTtc(baseTva.add(detailBonCaisse.getMtUniteTva()));
		detailBonCaisse.setMtTotalPrixVente(detailBonCaisse.getPrixUniteVente().multiply(detailBonCaisse.getQuantiteConsommer()));
		detailBonCaisse.setMtTotalRemise(detailBonCaisse.getMtUniteRemise().multiply(detailBonCaisse.getQuantiteConsommer()));
		detailBonCaisse.setMtTotalTva(detailBonCaisse.getMtUniteTva().multiply(detailBonCaisse.getQuantiteConsommer()));
		detailBonCaisse.setMtTotalTtc(detailBonCaisse.getPrixUniteTtc().multiply(detailBonCaisse.getQuantiteConsommer()));
		
		if(bonCaisse.getMtTotalVente()==null) bonCaisse.setMtTotalVente(BigDecimal.ZERO);
		bonCaisse.setMtTotalVente(bonCaisse.getMtTotalVente().add(detailBonCaisse.getMtTotalPrixVente()));
		if(bonCaisse.getMtTotalHt()==null) bonCaisse.setMtTotalHt(BigDecimal.ZERO);
		bonCaisse.setMtTotalHt(bonCaisse.getMtTotalHt().add(detailBonCaisse.getMtTotalHt()));
		if(bonCaisse.getMtTotalTva()==null) bonCaisse.setMtTotalTva(BigDecimal.ZERO);
		bonCaisse.setMtTotalTva(bonCaisse.getMtTotalTva().add(detailBonCaisse.getMtTotalTva()));
		if(bonCaisse.getMtTotalRemises()==null) bonCaisse.setMtTotalRemises(BigDecimal.ZERO);
		bonCaisse.setMtTotalRemises(bonCaisse.getMtTotalRemises().add(detailBonCaisse.getMtTotalRemise()));
		if(bonCaisse.getMtTotalFodec()==null) bonCaisse.setMtTotalFodec(BigDecimal.ZERO);
		bonCaisse.setMtTotalFodec(bonCaisse.getMtTotalFodec().add(detailBonCaisse.getMtTotalFodec()));
		if(bonCaisse.getMtTotalTtc()==null) bonCaisse.setMtTotalTtc(BigDecimal.ZERO);
		bonCaisse.setMtTotalTtc(bonCaisse.getMtTotalTtc().add(detailBonCaisse.getMtTotalTtc()));
		if(bonCaisse.getNetApayer()==null) bonCaisse.setNetApayer(BigDecimal.ZERO);
		bonCaisse.setNetApayer(bonCaisse.getNetApayer().add(detailBonCaisse.getMtTotalTtc()));
		if(bonCaisse.getPoidsTotalNet()==null) bonCaisse.setPoidsTotalNet(BigDecimal.ZERO);
		bonCaisse.setPoidsTotalNet(bonCaisse.getPoidsTotalNet().add(detailBonCaisse.getPoidsTotalNet()));
		if(bonCaisse.getPoidsTotalBrut()==null) bonCaisse.setPoidsTotalBrut(BigDecimal.ZERO);
		bonCaisse.setPoidsTotalBrut(bonCaisse.getPoidsTotalBrut().add(detailBonCaisse.getPoidsTotalBrut()));
		
		if(listedetailarticles!=null)
			listeboncaisse=listedetailarticles;
		
		if(listeboncaisse.size()==0)
			detailBonCaisse.setNligne(1);
		else
		{
			DetailBonCaisse ddc=new DetailBonCaisse();
			for( int i=0;i<listeboncaisse.size();i++)
			{
				ddc=listeboncaisse.get(i);
			}
			detailBonCaisse.setNligne(ddc.getNligne()+1);
		}
		
		/*if(listeboncaisse==null)
			detailBonCaisse.setNligne(1);
		else
			detailBonCaisse.setNligne(listeboncaisse.size()+1);*/
		
		
		detailBonCaisse.setCbonCaisse(bonCaisse.getCbonCaisse());
		if(detailBonCaisse.getMtTotalMarge()==null)
			detailBonCaisse.setMtTotalMarge(BigDecimal.ZERO);
		if(detailBonCaisse.getMtUniteMarge()==null)
			detailBonCaisse.setMtUniteMarge(BigDecimal.ZERO);
		if(detailBonCaisse.getQuantiteNonTransferer()==null)
			detailBonCaisse.setQuantiteNonTransferer(BigDecimal.ZERO);
		if(detailBonCaisse.getQuantiteTransferer()==null)
			detailBonCaisse.setQuantiteTransferer(BigDecimal.ZERO);
		if(detailBonCaisse.getTauxConsommation()==null)
			detailBonCaisse.setTauxConsommation(BigDecimal.ZERO);
		if(detailBonCaisse.getTauxMarge()==null)
			detailBonCaisse.setTauxMarge(BigDecimal.ZERO);
		
		
		listeboncaisse.add(detailBonCaisse);
		listedetailarticles=listeboncaisse;
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
		//context.update("listeArticle:detailTable");
	}
    
    
}
