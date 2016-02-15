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
import tn.taktak.GestCommerciale_V1.entity.BonLivraison;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.DetailBonLivraison;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.service.ArticleService;
import tn.taktak.GestCommerciale_V1.service.DetailBonLivraisonService;
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

public class DetailBonLivController implements Serializable {

	@ManagedProperty("#{detailBonLivraisonService}")
	private DetailBonLivraisonService detailBonLivraisonService;
	
	@ManagedProperty("#{articleListController}")
	private ArticleListController articleListController;
	
	@ManagedProperty(value="#{tvaListController}")
	private TvaListController tvaListController;
	
	@ManagedProperty("#{bonLivraisonController}")
	private BonLivraisonController bonLivraisonController;
		
	@ManagedProperty(value="#{clientListController}")
	private ClientListController clientListController;
	
	@ManagedProperty("#{articleService}")
    private ArticleService articleService;
	
	private List<DetailBonLivraison> listebonliv;
	private DetailBonLivraison detailBonLiv=new DetailBonLivraison();
	private BonLivraison bonLiv=null;
	public static List<DetailBonLivraison> listedetailarticles=null;
	
	@PostConstruct
	public void loadBonLiv() {
	
		if(listedetailarticles!=null)
			listebonliv=listedetailarticles;
		else
			listebonliv=new ArrayList<DetailBonLivraison>(); 
	}
	
	public BonLivraison getBonLivActuel()
	{
		return bonLivraisonController.getBonLivraison();
	}
	
	public void removeLisDetail(BonLivraison dc)
	{
		List<DetailBonLivraison>list=findDetailOfBonLiv(dc.getCbonLivraison());
		for(int i=0;i<list.size();i++)
		{
			remove(list.get(i));
		}
		bonLivraisonController.remove(dc);
	}
	
	public void remove(DetailBonLivraison detailbl) {
		detailBonLivraisonService.remove(detailbl);
	}
	
	public void deleteArticle(DetailBonLivraison selected)
	{
		
		listebonliv.remove(selected);
		
		bonLiv=getBonLivActuel();
		if(bonLiv.getMtTotalVente()==null) bonLiv.setMtTotalVente(BigDecimal.ZERO);
		bonLiv.setMtTotalVente(bonLiv.getMtTotalVente().subtract(selected.getMtTotalPrixVente()));
		if(bonLiv.getMtTotalHt()==null) bonLiv.setMtTotalHt(BigDecimal.ZERO);
		bonLiv.setMtTotalHt(bonLiv.getMtTotalHt().subtract(selected.getMtTotalHt()));
		if(bonLiv.getMtTotalTva()==null) bonLiv.setMtTotalTva(BigDecimal.ZERO);
		bonLiv.setMtTotalTva(bonLiv.getMtTotalTva().subtract(selected.getMtTotalTva()));
		if(bonLiv.getMtTotalRemises()==null) bonLiv.setMtTotalRemises(BigDecimal.ZERO);
		bonLiv.setMtTotalRemises(bonLiv.getMtTotalRemises().subtract(selected.getMtTotalRemise()));
		if(bonLiv.getMtTotalFodec()==null) bonLiv.setMtTotalFodec(BigDecimal.ZERO);
		bonLiv.setMtTotalFodec(bonLiv.getMtTotalFodec().subtract(selected.getMtTotalFodec()));
		if(bonLiv.getMtTotalTtc()==null) bonLiv.setMtTotalTtc(BigDecimal.ZERO);
		bonLiv.setMtTotalTtc(bonLiv.getMtTotalTtc().subtract(selected.getMtTotalTtc()));
		if(bonLiv.getNetApayer()==null) bonLiv.setNetApayer(BigDecimal.ZERO);
		bonLiv.setNetApayer(bonLiv.getNetApayer().subtract(selected.getMtTotalTtc()));
		if(bonLiv.getPoidsTotalNet()==null) bonLiv.setPoidsTotalNet(BigDecimal.ZERO);
		bonLiv.setPoidsTotalNet(bonLiv.getPoidsTotalNet().subtract(selected.getPoidsTotalNet()));
		if(bonLiv.getPoidsTotalBrut()==null) bonLiv.setPoidsTotalBrut(BigDecimal.ZERO);
		bonLiv.setPoidsTotalBrut(bonLiv.getPoidsTotalBrut().subtract(selected.getPoidsTotalBrut()));
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:pnl_totaux");
	
		
	}
	
	public String retournChiffreToLettre(BigDecimal bd)
	{
		NumberFormat formatter = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT); 
   	 	String resultatChiffre = formatter.format(bd);
   	 	return resultatChiffre;
	}
	
	
	
	public void nouveauDevis()
	{
		//listedetailarticles.clear();
		listebonliv.clear();
		//devisListeController.setDevis(new DevisClient());
		//devis=devisListeController.getDevis();
		bonLivraisonController.setBonLivraison(new BonLivraison());
		//devisListeController.updateCompteur("DevisClient");
		bonLivraisonController.getbonLivId();
		bonLivraisonController.getBonLivraison().setDateCreation(new Date());
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
		bonLiv=getBonLivActuel();
		getClientOfBonLiv(clientListController.findClientById(bonLiv.getCclient()));
		bonLiv.setMtTotalTtc(bonLiv.getMtTotalTtc().setScale(3, BigDecimal.ROUND_UP));
		bonLiv.setMtTotalTva(bonLiv.getMtTotalTva().setScale(3, BigDecimal.ROUND_UP));
		bonLiv.setNetApayer(bonLiv.getNetApayer().setScale(3, BigDecimal.ROUND_UP));
		
		if(listedetailarticles!=null)
			listebonliv=listedetailarticles;
		
		if(bonLivraisonController.modif==true)
		{
			List<DetailBonLivraison>list=findDetailOfBonLiv(bonLiv.getCbonLivraison());
			for(int i=0;i<list.size();i++)
			{
				remove(list.get(i));
			}
		}
		
		bonLivraisonController.save();
		detailBonLivraisonService.saveList(listebonliv);
		
		Article article1=new Article();
		for(DetailBonLivraison dbl : listebonliv)
		{
			article1=articleService.findArticleById(dbl.getCarticle());
			article1.setQteStock(article1.getQteStock().subtract(dbl.getQuantiteConsommer()));
			articleService.save(article1);
		}
		
		detailBonLiv=new DetailBonLivraison();
		PDF();
		listedetailarticles.clear();	
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Les details du bon de livraison sont Enregistrés!", null));
	}
	
	public void getClientOfBonLiv(Client client)
	{
		bonLiv=getBonLivActuel();
		//Client client=clientListController.getClient();
		bonLiv.setCclient(client.getCclient());
		bonLiv.setDesAdresseClient(client.getDesAdresse());
		bonLiv.setCodePostalClient(client.getCodePostal());
		bonLiv.setVilleClient(client.getVille());
		bonLiv.setPaysClient(client.getPays());
		
	}
	
	public void selectedBonLiv(BonLivraison art) 
	{
		getClientOfBonLiv(clientListController.findClientById(art.getCclient()));
		setBonLiv(art);
		bonLivraisonController.setBonLivraison(art);
		
		bonLivraisonController.modif=true;
		bonLivraisonController.bonlivstc=art;
		List<DetailBonLivraison> listeDetail=findDetailOfBonLiv(bonLiv.getCbonLivraison());
		listedetailarticles=listeDetail;
		setListebonliv(listeDetail);
		
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.getApplication().getNavigationHandler().handleNavigation(context, null, "/Fichebondelivraison.xhtml");
	}
	
	public List<DetailBonLivraison> findDetailOfBonLiv (String t)
	{
		return detailBonLivraisonService.findDetailOfBonLiv(t);
	}
	
	JasperPrint jasperPrint;  
    public void init() throws JRException{  
    	//listedetaildevis = detailDevisClientService.findAll();
    	String resultatChiffre;
    	String idClient=null;
    	if(listedetailarticles!=null)
    		listebonliv = listedetailarticles;
    	 idClient=bonLiv.getCclient();
    	 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 String dmy = dmyFormat.format(bonLiv.getDateCreation());
    	 
    	 String[] parts = bonLiv.getNetApayer().toString().split("\\.");
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
    	 parameters.put("totalHtBrut",bonLiv.getMtTotalVente());
    	 parameters.put("totalHtNet",bonLiv.getMtTotalHt());
    	 parameters.put("totalRemise",bonLiv.getMtTotalRemises());
    	 parameters.put("totalFodec",bonLiv.getMtTotalFodec());
    	 parameters.put("totalTva",bonLiv.getMtTotalTva());
    	 parameters.put("totalTtc",bonLiv.getMtTotalTtc());
    	 parameters.put("netapayer",bonLiv.getNetApayer());
    	 parameters.put("resultatChiffre",resultatChiffre);
    	 
    	 Client client=clientListController.findClientById(idClient);
    	 parameters.put("raisocial",client.getRaisonSociale());
    	 parameters.put("adresse",client.getDesAdresse());
    	 parameters.put("tel",client.getTel());
    	 parameters.put("gsm",client.getGsm());
    	 parameters.put("fax",client.getFax());
    	 parameters.put("mf",client.getMatFiscale());
    	 parameters.put("rc",client.getNregistreCommerce());
    	 
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listebonliv);  
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/reportbl_V1.jasper");
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
		Article art=articleListController.findArticleById(detailBonLiv.getCarticle());
		int result=art.getQteStock().compareTo(detailBonLiv.getQuantiteConsommer());
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
		Article art=articleListController.findArticleById(detailBonLiv.getCarticle());
		Tva tva_article=tvaListController.findTvaById(art.getCtva());
		bonLiv=getBonLivActuel();
		
		detailBonLiv.setDesArticle(art.getDesArticle());
		detailBonLiv.setPrixUniteVente(art.getPrixVente());
		if(detailBonLiv.getTauxRemise().equals(BigDecimal.ZERO))
		{	detailBonLiv.setMtUniteRemise(BigDecimal.ZERO);
			detailBonLiv.setPrixUniteHt(art.getPrixVente());
		}
		else
		{
			detailBonLiv.setMtUniteRemise((detailBonLiv.getPrixUniteVente().multiply(detailBonLiv.getTauxRemise())).divide(bd));
			detailBonLiv.setPrixUniteHt(detailBonLiv.getPrixUniteVente().subtract(detailBonLiv.getMtUniteRemise()));
		}
		BigDecimal baseTva=detailBonLiv.getPrixUniteHt();
		if(bonLiv.isFodec())
		{
			detailBonLiv.setMtUniteFodec((detailBonLiv.getPrixUniteHt()).divide(bd));
			baseTva=detailBonLiv.getPrixUniteHt().add(detailBonLiv.getMtUniteFodec());
			detailBonLiv.setMtTotalFodec(detailBonLiv.getMtUniteFodec().multiply(detailBonLiv.getQuantiteConsommer()));
			
		}
		else
		{
			detailBonLiv.setMtUniteFodec(BigDecimal.ZERO);
			detailBonLiv.setMtTotalFodec(BigDecimal.ZERO);
			
		}
		if(art.getPoidsNet()==null)
		{
			detailBonLiv.setPoidsUniteNet(BigDecimal.ZERO);
			detailBonLiv.setPoidsTotalNet(BigDecimal.ZERO);
		}
		else
		{	
			detailBonLiv.setPoidsUniteNet(art.getPoidsNet());
			detailBonLiv.setPoidsTotalNet(detailBonLiv.getPoidsUniteNet().multiply(detailBonLiv.getQuantiteConsommer()));
		}
		if(art.getPoidsBrut()==null)
		{
			detailBonLiv.setPoidsUniteBrut(BigDecimal.ZERO);
			detailBonLiv.setPoidsTotalBrut(BigDecimal.ZERO);
		}
		else
		{
			detailBonLiv.setPoidsUniteBrut(art.getPoidsBrut());
			detailBonLiv.setPoidsTotalBrut(detailBonLiv.getPoidsUniteBrut().multiply(detailBonLiv.getQuantiteConsommer()));
		}
		
		detailBonLiv.setCtva(art.getCtva());
		detailBonLiv.setTauxTva(tva_article.getTauxTva());
		detailBonLiv.setMtTotalHt(detailBonLiv.getPrixUniteHt().multiply(detailBonLiv.getQuantiteConsommer()));
		detailBonLiv.setCunite(art.getCunite());
		detailBonLiv.setPrixRevientUnite(art.getPrixAchat());
		detailBonLiv.setMtUniteTva((baseTva.multiply(detailBonLiv.getTauxTva())).divide(bd));
		detailBonLiv.setPrixUniteTtc(baseTva.add(detailBonLiv.getMtUniteTva()));
		detailBonLiv.setMtTotalPrixVente(detailBonLiv.getPrixUniteVente().multiply(detailBonLiv.getQuantiteConsommer()));
		detailBonLiv.setMtTotalRemise(detailBonLiv.getMtUniteRemise().multiply(detailBonLiv.getQuantiteConsommer()));
		detailBonLiv.setMtTotalTva(detailBonLiv.getMtUniteTva().multiply(detailBonLiv.getQuantiteConsommer()));
		detailBonLiv.setMtTotalTtc(detailBonLiv.getPrixUniteTtc().multiply(detailBonLiv.getQuantiteConsommer()));
		
		if(bonLiv.getMtTotalVente()==null) bonLiv.setMtTotalVente(BigDecimal.ZERO);
		bonLiv.setMtTotalVente(bonLiv.getMtTotalVente().add(detailBonLiv.getMtTotalPrixVente()));
		if(bonLiv.getMtTotalHt()==null) bonLiv.setMtTotalHt(BigDecimal.ZERO);
		bonLiv.setMtTotalHt(bonLiv.getMtTotalHt().add(detailBonLiv.getMtTotalHt()));
		if(bonLiv.getMtTotalTva()==null) bonLiv.setMtTotalTva(BigDecimal.ZERO);
		bonLiv.setMtTotalTva(bonLiv.getMtTotalTva().add(detailBonLiv.getMtTotalTva()));
		if(bonLiv.getMtTotalRemises()==null) bonLiv.setMtTotalRemises(BigDecimal.ZERO);
		bonLiv.setMtTotalRemises(bonLiv.getMtTotalRemises().add(detailBonLiv.getMtTotalRemise()));
		if(bonLiv.getMtTotalFodec()==null) bonLiv.setMtTotalFodec(BigDecimal.ZERO);
		bonLiv.setMtTotalFodec(bonLiv.getMtTotalFodec().add(detailBonLiv.getMtTotalFodec()));
		if(bonLiv.getMtTotalTtc()==null) bonLiv.setMtTotalTtc(BigDecimal.ZERO);
		bonLiv.setMtTotalTtc(bonLiv.getMtTotalTtc().add(detailBonLiv.getMtTotalTtc()));
		if(bonLiv.getNetApayer()==null) bonLiv.setNetApayer(BigDecimal.ZERO);
		bonLiv.setNetApayer(bonLiv.getNetApayer().add(detailBonLiv.getMtTotalTtc()));
		if(bonLiv.getPoidsTotalNet()==null) bonLiv.setPoidsTotalNet(BigDecimal.ZERO);
		bonLiv.setPoidsTotalNet(bonLiv.getPoidsTotalNet().add(detailBonLiv.getPoidsTotalNet()));
		if(bonLiv.getPoidsTotalBrut()==null) bonLiv.setPoidsTotalBrut(BigDecimal.ZERO);
		bonLiv.setPoidsTotalBrut(bonLiv.getPoidsTotalBrut().add(detailBonLiv.getPoidsTotalBrut()));
		
		if(listedetailarticles!=null)
			listebonliv=listedetailarticles;
		
		if(listebonliv.size()==0)
			detailBonLiv.setNligne(1);
		else
		{
			DetailBonLivraison ddc=new DetailBonLivraison();
			for( int i=0;i<listebonliv.size();i++)
			{
				ddc=listebonliv.get(i);
			}
			detailBonLiv.setNligne(ddc.getNligne()+1);
			
		}
		
		/*if(listebonliv==null)
			detailBonLiv.setNligne(1);
		else
			detailBonLiv.setNligne(listebonliv.size()+1);*/
		
		
		detailBonLiv.setCbonLivraison(bonLiv.getCbonLivraison());
		if(detailBonLiv.getMtTotalMarge()==null)
			detailBonLiv.setMtTotalMarge(BigDecimal.ZERO);
		if(detailBonLiv.getMtUniteMarge()==null)
			detailBonLiv.setMtUniteMarge(BigDecimal.ZERO);
		if(detailBonLiv.getQuantiteNonTransferer()==null)
			detailBonLiv.setQuantiteNonTransferer(BigDecimal.ZERO);
		if(detailBonLiv.getQuantiteTransferer()==null)
			detailBonLiv.setQuantiteTransferer(BigDecimal.ZERO);
		if(detailBonLiv.getTauxConsommation()==null)
			detailBonLiv.setTauxConsommation(BigDecimal.ZERO);
		if(detailBonLiv.getTauxMarge()==null)
			detailBonLiv.setTauxMarge(BigDecimal.ZERO);
		
		
		listebonliv.add(detailBonLiv);
		listedetailarticles=listebonliv;
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
	}
}
