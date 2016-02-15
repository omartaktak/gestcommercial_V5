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
import tn.taktak.GestCommerciale_V1.entity.BonAchat;
import tn.taktak.GestCommerciale_V1.entity.DetailBonAchat;
import tn.taktak.GestCommerciale_V1.entity.DetailBonLivraison;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DevisClient;
import tn.taktak.GestCommerciale_V1.entity.Fournisseur;
import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.service.ArticleService;
import tn.taktak.GestCommerciale_V1.service.DetailBonAchatService;
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

public class DetailBonAchatController implements Serializable {

	@ManagedProperty("#{detailBonAchatService}")
	private DetailBonAchatService detailBonAchatService;
	
	@ManagedProperty("#{articleListController}")
	private ArticleListController articleListController;
	
	@ManagedProperty(value="#{tvaListController}")
	private TvaListController tvaListController;
	
	@ManagedProperty("#{bonAchatController}")
	private BonAchatController bonAchatController;
		
//	@ManagedProperty(value="#{clientListController}")
//	private ClientListController clientListController;
//	
	@ManagedProperty(value="#{fournisseurListController}")
	private FournisseurListController  fournisseurListController;
	
	@ManagedProperty("#{articleService}")
    private ArticleService articleService;
	
	private List<DetailBonAchat> listebonachat;
	private DetailBonAchat detailBonAchat=new DetailBonAchat();
	private BonAchat bonAchat=null;
	public static List<DetailBonAchat> listedetailarticles=null;
	
	@PostConstruct
	public void loadBonAchat() {
	
		if(listedetailarticles!=null)
			listebonachat=listedetailarticles;
		else
			listebonachat=new ArrayList<DetailBonAchat>(); 
	}
	
	public BonAchat getBonAchatActuel()
	{
		return bonAchatController.getBonAchat();
	}
	
	public void nouveauDevis()
	{
		//listedetailarticles.clear();
		listebonachat.clear();
		//devisListeController.setDevis(new DevisClient());
		//devis=devisListeController.getDevis();
		bonAchatController.setBonAchat(new BonAchat());
		//devisListeController.updateCompteur("DevisClient");
		bonAchatController.getbonAchatId();
		bonAchatController.getBonAchat().setDateCreation(new Date());
		//devisListeController.setDevis(devis);
		RequestContext context = RequestContext.getCurrentInstance();
		//context.update("formPrincipal");
		context.update("formPrincipal:cdevisClient");
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
		context.update("formPrincipal:clt_RS");
		context.update("formPrincipal:clt_Add");
	}
	
	
	public void deleteArticle(DetailBonAchat selected)
	{
		
		listebonachat.remove(selected);
		
		bonAchat=getBonAchatActuel();
		if(bonAchat.getMtTotalAchat()==null) bonAchat.setMtTotalAchat(BigDecimal.ZERO);
		bonAchat.setMtTotalAchat(bonAchat.getMtTotalAchat().subtract(selected.getMtTotalPrixAchat()));
		if(bonAchat.getMtTotalHt()==null) bonAchat.setMtTotalHt(BigDecimal.ZERO);
		bonAchat.setMtTotalHt(bonAchat.getMtTotalHt().subtract(selected.getMtTotalHt()));
		if(bonAchat.getMtTotalTva()==null) bonAchat.setMtTotalTva(BigDecimal.ZERO);
		bonAchat.setMtTotalTva(bonAchat.getMtTotalTva().subtract(selected.getMtTotalTva()));
		if(bonAchat.getMtTotalRemises()==null) bonAchat.setMtTotalRemises(BigDecimal.ZERO);
		bonAchat.setMtTotalRemises(bonAchat.getMtTotalRemises().subtract(selected.getMtTotalRemise()));
		if(bonAchat.getMtTotalFodec()==null) bonAchat.setMtTotalFodec(BigDecimal.ZERO);
		bonAchat.setMtTotalFodec(bonAchat.getMtTotalFodec().subtract(selected.getMtTotalFodec()));
		if(bonAchat.getMtTotalTtc()==null) bonAchat.setMtTotalTtc(BigDecimal.ZERO);
		bonAchat.setMtTotalTtc(bonAchat.getMtTotalTtc().subtract(selected.getMtTotalTtc()));
		if(bonAchat.getNetApayer()==null) bonAchat.setNetApayer(BigDecimal.ZERO);
		bonAchat.setNetApayer(bonAchat.getNetApayer().subtract(selected.getMtTotalTtc()));
		if(bonAchat.getPoidsTotalNet()==null) bonAchat.setPoidsTotalNet(BigDecimal.ZERO);
		bonAchat.setPoidsTotalNet(bonAchat.getPoidsTotalNet().subtract(selected.getPoidsTotalNet()));
		if(bonAchat.getPoidsTotalBrut()==null) bonAchat.setPoidsTotalBrut(BigDecimal.ZERO);
		bonAchat.setPoidsTotalBrut(bonAchat.getPoidsTotalBrut().subtract(selected.getPoidsTotalBrut()));
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:pnl_totaux");
		
	}
	
	public void getFournisseurOfBA(Fournisseur fourn)
	{
		bonAchat=getBonAchatActuel();
		//Client client=clientListController.getClient();
		bonAchat.setCfournisseur(fourn.getCfournisseur());
		bonAchat.setDesAdresseFournisseur(fourn.getDesAdresse());
		bonAchat.setCodePostalFournisseur(fourn.getCodePostal());
		bonAchat.setVilleFournisseur(fourn.getVille());
		bonAchat.setPaysFournisseur(fourn.getPays());
		
	}
	
	public void selectedBonAchat(BonAchat art)
	{
		getFournisseurOfBA(fournisseurListController.findFournisseurById(art.getCfournisseur()));
		setBonAchat(art);
		bonAchatController.setBonAchat(art);
		
		bonAchatController.modif=true;
		bonAchatController.bonachatstc=art;
		List<DetailBonAchat> listeDetail=findDetailOfBA(bonAchat.getCbonAchat());
		listedetailarticles=listeDetail;
		setListebonachat(listeDetail);
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getApplication().getNavigationHandler().handleNavigation(context, null, "/Fichebonachat.xhtml");
	}
	
	public List<DetailBonAchat> findDetailOfBA(String t)
	{
		return detailBonAchatService.findDetailOfBA(t);
	}
	
	public void removeLisDetail(BonAchat dc)
	{
		List<DetailBonAchat>list=findDetailOfBA(dc.getCbonAchat());
		for(int i=0;i<list.size();i++)
		{
			remove(list.get(i));
		}
		bonAchatController.remove(dc);
	}
	
	public void remove(DetailBonAchat detailba) {
		detailBonAchatService.remove(detailba);
	}
	
	public void saveListe()throws JRException, IOException
	{
		bonAchat=getBonAchatActuel();

		getFournisseurOfBA(fournisseurListController.findFournisseurById(bonAchat.getCfournisseur()));
		
		bonAchat.setMtTotalTtc(bonAchat.getMtTotalTtc().setScale(3, BigDecimal.ROUND_UP));
		bonAchat.setMtTotalTva(bonAchat.getMtTotalTva().setScale(3, BigDecimal.ROUND_UP));
		bonAchat.setNetApayer(bonAchat.getNetApayer().setScale(3, BigDecimal.ROUND_UP));
		
		if(bonAchatController.modif==true)
		{
			List<DetailBonAchat>list=findDetailOfBA(bonAchat.getCbonAchat());
			for(int i=0;i<list.size();i++)
			{
				remove(list.get(i));
			}
		}
		
		bonAchatController.save();
		
		if(listedetailarticles!=null)
			listebonachat=listedetailarticles;
		detailBonAchatService.saveList(listebonachat);
		
		Article article1=new Article();
		for(DetailBonAchat dbl : listebonachat)
		{
			article1=articleService.findArticleById(dbl.getCarticle());
			article1.setQteStock(article1.getQteStock().add(dbl.getQuantiteConsommer()));
			articleService.save(article1);
		}
		
		detailBonAchat=new DetailBonAchat();
		PDF();
		listedetailarticles.clear();	
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Les details du bon de achat sont Enregistrés!", null));
	}
	
	public String retournChiffreToLettre(BigDecimal bd)
	{
		NumberFormat formatter = new RuleBasedNumberFormat(Locale.FRANCE, RuleBasedNumberFormat.SPELLOUT); 
   	 	String resultatChiffre = formatter.format(bd);
   	 	return resultatChiffre;
	}
	
	JasperPrint jasperPrint;  
    public void init() throws JRException{  
    	
    	String resultatChiffre;
    	String idFourn=null;
    	if(listedetailarticles!=null)
    		listebonachat = listedetailarticles;
    	idFourn=bonAchat.getCfournisseur();
    	 SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    	 String dmy = dmyFormat.format(bonAchat.getDateCreation());
    	 
    	 String[] parts = bonAchat.getNetApayer().toString().split("\\.");
    	 String part1 = parts[0];
    	 String part2 = parts[1];
    	 BigDecimal bdc1=new BigDecimal(part1);
    	 part1=retournChiffreToLettre(bdc1);
    	 BigDecimal bdc2=new BigDecimal(part2);
    	 part2=retournChiffreToLettre(bdc2);		 
    	 resultatChiffre=part1+ " dinars "+part2+" millimes ";
    	 
    	 Map parameters = new HashMap();
    	 parameters.put("idFourn", idFourn);
    	 parameters.put("dateCreation",dmy);
    	 parameters.put("totalHtBrut",bonAchat.getMtTotalAchat());
    	 parameters.put("totalHtNet",bonAchat.getMtTotalHt());
    	 parameters.put("totalRemise",bonAchat.getMtTotalRemises());
    	 parameters.put("totalFodec",bonAchat.getMtTotalFodec());
    	 parameters.put("totalTva",bonAchat.getMtTotalTva());
    	 parameters.put("totalTtc",bonAchat.getMtTotalTtc());
    	 parameters.put("netapayer",bonAchat.getNetApayer());
    	 parameters.put("resultatChiffre",resultatChiffre);
    	 
    	 Fournisseur fourn=fournisseurListController.findFournisseurById(idFourn);
    	 parameters.put("raisocial",fourn.getRaisonSociale());
    	 parameters.put("adresse",fourn.getDesAdresse());
    	 parameters.put("tel",fourn.getTel());
    	 parameters.put("gsm",fourn.getGsm());
    	 parameters.put("fax",fourn.getFax());
    	 parameters.put("mf",fourn.getMatFiscale());
    	 parameters.put("rc",fourn.getNregistreCommerce());
    	 
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listebonachat);  
        String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/reportba.jasper");
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
		Article art=articleListController.findArticleById(detailBonAchat.getCarticle());
		int result=art.getQteStock().compareTo(detailBonAchat.getQuantiteConsommer());
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
		Article art=articleListController.findArticleById(detailBonAchat.getCarticle());
		Tva tva_article=tvaListController.findTvaById(art.getCtva());
		bonAchat=getBonAchatActuel();
		
		detailBonAchat.setDesArticle(art.getDesArticle());
		detailBonAchat.setPrixUniteAchat(art.getPrixAchat());
		if(detailBonAchat.getTauxRemise().equals(BigDecimal.ZERO))
		{	detailBonAchat.setMtUniteRemise(BigDecimal.ZERO);
			detailBonAchat.setPrixUniteHt(art.getPrixVente());
		}
		else
		{
			detailBonAchat.setMtUniteRemise((detailBonAchat.getPrixUniteAchat().multiply(detailBonAchat.getTauxRemise())).divide(bd));
			detailBonAchat.setPrixUniteHt(detailBonAchat.getPrixUniteAchat().subtract(detailBonAchat.getMtUniteRemise()));
		}
		BigDecimal baseTva=detailBonAchat.getPrixUniteHt();
		if(bonAchat.isFodec())
		{
			detailBonAchat.setMtUniteFodec((detailBonAchat.getPrixUniteHt()).divide(bd));
			baseTva=detailBonAchat.getPrixUniteHt().add(detailBonAchat.getMtUniteFodec());
			detailBonAchat.setMtTotalFodec(detailBonAchat.getMtUniteFodec().multiply(detailBonAchat.getQuantiteConsommer()));
			
		}
		else
		{
			detailBonAchat.setMtUniteFodec(BigDecimal.ZERO);
			detailBonAchat.setMtTotalFodec(BigDecimal.ZERO);
			
		}
		if(art.getPoidsNet()==null)
		{
			detailBonAchat.setPoidsUniteNet(BigDecimal.ZERO);
			detailBonAchat.setPoidsTotalNet(BigDecimal.ZERO);
		}
		else
		{	
			detailBonAchat.setPoidsUniteNet(art.getPoidsNet());
			detailBonAchat.setPoidsTotalNet(detailBonAchat.getPoidsUniteNet().multiply(detailBonAchat.getQuantiteConsommer()));
		}
		if(art.getPoidsBrut()==null)
		{
			detailBonAchat.setPoidsUniteBrut(BigDecimal.ZERO);
			detailBonAchat.setPoidsTotalBrut(BigDecimal.ZERO);
		}
		else
		{
			detailBonAchat.setPoidsUniteBrut(art.getPoidsBrut());
			detailBonAchat.setPoidsTotalBrut(detailBonAchat.getPoidsUniteBrut().multiply(detailBonAchat.getQuantiteConsommer()));
		}
		
		detailBonAchat.setCtva(art.getCtva());
		detailBonAchat.setTauxTva(tva_article.getTauxTva());
		detailBonAchat.setMtTotalHt(detailBonAchat.getPrixUniteHt().multiply(detailBonAchat.getQuantiteConsommer()));
		detailBonAchat.setCunite(art.getCunite());
		
		//detailBonAchat.setPrixRevientUnite(art.getPrixAchat());
		detailBonAchat.setPrixUniteHt(art.getPrixAchat());
		detailBonAchat.setMtUniteTva((baseTva.multiply(detailBonAchat.getTauxTva())).divide(bd));
		detailBonAchat.setPrixUniteTtc(baseTva.add(detailBonAchat.getMtUniteTva()));
		detailBonAchat.setMtTotalPrixAchat(detailBonAchat.getPrixUniteAchat().multiply(detailBonAchat.getQuantiteConsommer()));
		detailBonAchat.setMtTotalRemise(detailBonAchat.getMtUniteRemise().multiply(detailBonAchat.getQuantiteConsommer()));
		detailBonAchat.setMtTotalTva(detailBonAchat.getMtUniteTva().multiply(detailBonAchat.getQuantiteConsommer()));
		detailBonAchat.setMtTotalTtc(detailBonAchat.getPrixUniteTtc().multiply(detailBonAchat.getQuantiteConsommer()));
		
		if(bonAchat.getMtTotalAchat()==null) bonAchat.setMtTotalAchat(BigDecimal.ZERO);
		bonAchat.setMtTotalAchat(bonAchat.getMtTotalAchat().add(detailBonAchat.getMtTotalPrixAchat()));
		if(bonAchat.getMtTotalHt()==null) bonAchat.setMtTotalHt(BigDecimal.ZERO);
		bonAchat.setMtTotalHt(bonAchat.getMtTotalHt().add(detailBonAchat.getMtTotalHt()));
		if(bonAchat.getMtTotalTva()==null) bonAchat.setMtTotalTva(BigDecimal.ZERO);
		bonAchat.setMtTotalTva(bonAchat.getMtTotalTva().add(detailBonAchat.getMtTotalTva()));
		if(bonAchat.getMtTotalRemises()==null) bonAchat.setMtTotalRemises(BigDecimal.ZERO);
		bonAchat.setMtTotalRemises(bonAchat.getMtTotalRemises().add(detailBonAchat.getMtTotalRemise()));
		if(bonAchat.getMtTotalFodec()==null) bonAchat.setMtTotalFodec(BigDecimal.ZERO);
		bonAchat.setMtTotalFodec(bonAchat.getMtTotalFodec().add(detailBonAchat.getMtTotalFodec()));
		if(bonAchat.getMtTotalTtc()==null) bonAchat.setMtTotalTtc(BigDecimal.ZERO);
		bonAchat.setMtTotalTtc(bonAchat.getMtTotalTtc().add(detailBonAchat.getMtTotalTtc()));
		if(bonAchat.getNetApayer()==null) bonAchat.setNetApayer(BigDecimal.ZERO);
		bonAchat.setNetApayer(bonAchat.getNetApayer().add(detailBonAchat.getMtTotalTtc()));
		if(bonAchat.getPoidsTotalNet()==null) bonAchat.setPoidsTotalNet(BigDecimal.ZERO);
		bonAchat.setPoidsTotalNet(bonAchat.getPoidsTotalNet().add(detailBonAchat.getPoidsTotalNet()));
		if(bonAchat.getPoidsTotalBrut()==null) bonAchat.setPoidsTotalBrut(BigDecimal.ZERO);
		bonAchat.setPoidsTotalBrut(bonAchat.getPoidsTotalBrut().add(detailBonAchat.getPoidsTotalBrut()));
		
		if(listedetailarticles!=null)
			listebonachat=listedetailarticles;
		
		if(listebonachat.size()==0)
			detailBonAchat.setNligne(1);
		else
		{
			DetailBonAchat ddc=new DetailBonAchat();
			for( int i=0;i<listebonachat.size();i++)
			{
				ddc=listebonachat.get(i);
			}
			detailBonAchat.setNligne(ddc.getNligne()+1);
		}
		
		
		/*if(listebonachat==null)
			detailBonAchat.setNligne(1);
		else
			detailBonAchat.setNligne(listebonachat.size()+1);*/
		
		
		detailBonAchat.setCbonAchat(bonAchat.getCbonAchat());
	//	if(detailBonAchat.getMtTotalMarge()==null)
	//		detailBonAchat.setMtTotalMarge(BigDecimal.ZERO);
	//	if(detailBonAchat.getMtUniteMarge()==null)
	//		detailBonAchat.setMtUniteMarge(BigDecimal.ZERO);
		if(detailBonAchat.getQuantiteConsommer()==null)
			detailBonAchat.setQuantiteConsommer(BigDecimal.ZERO);
//		if(detailBonAchat.getQuantiteTransferer()==null)
//			detailBonAchat.setQuantiteTransferer(BigDecimal.ZERO);
//		if(detailBonAchat.getTauxConsommation()==null)
//			detailBonAchat.setTauxConsommation(BigDecimal.ZERO);
//		if(detailBonAchat.getTauxMarge()==null)
//			detailBonAchat.setTauxMarge(BigDecimal.ZERO);
		
		
		listebonachat.add(detailBonAchat);
		listedetailarticles=listebonachat;
		RequestContext context = RequestContext.getCurrentInstance();
		context.update("formPrincipal:detailTable");
		context.update("formPrincipal:pnl_totaux");
	}
	
}
