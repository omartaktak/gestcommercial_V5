package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.Tva;
import tn.taktak.GestCommerciale_V1.service.TvaService;


@Getter
@Setter
@ViewScoped
//@RequestScoped
@ManagedBean
public class TvaListController implements Serializable {

	@ManagedProperty("#{tvaService}")
	private TvaService tvaService;
	
	private List<Tva> tvas;
	private Tva tva = new Tva();
	
	public static List<Tva> listtva=null;
	public static String id=null;
	
	@PostConstruct
	public void loadTVA() {
		
		if(listtva!=null)
			tvas=listtva;
		else
		tvas = tvaService.findAll();
	}
	
	public List<String> listIds()
	{
		List<String> list=new ArrayList<String>() ;
			
		
		for (Tva tva : tvas)
		{
			list.add(tva.getCtva());
		}
		return list;
	}
	
	public List<BigDecimal> findTauxTva(){
		return tvaService.findTauxTva();
	   
	}
	
	public void save() {
		tvaService.save(tva);
		tva=new Tva();
		tvas = tvaService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TVA Enregistre!", null));
	}
	
	public void remove(Tva tva) {
		tvaService.remove(tva);
		tvas = tvaService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "TVA Supprime!", null));
	}
	
	public void clear()
	{
		tva=new Tva();
	}
	
	public  Tva findTvaById(String id)
	{
		Tva tva1= tvaService.findTvaById(id);
		return tva1;
	}
	
	public static Tva findTvaById2(String id)
	{
		TvaService tvaService1=new TvaService();
				Tva tva_art= tvaService1.findTvaById(id);
				return tva_art;
	}
	
	public String redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/ListeTva.xhtml"));
        try {
        	extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null;
	}
	
	public void filterTva()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formArticle:rechercheArticle");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			tvas=tvaService.filterTva(recherche);
			listtva=tvas;
			context.update("formArticle:articleTable");
		}
		else
		{
			tvas=tvaService.findAll();
			listtva=tvas;
		}
	}
	
	public void selectedTva(Tva art) 
	{
		setTva(art);
		id=art.getCtva();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('articleDialog').show();");
	}
	
	public void update()
	{
		boolean champs_vide=false;
		tva.setCtva(id);
		if(tva.getCtva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(tva.getTauxTva().equals(BigDecimal.ZERO))
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le taux ne peut pas etre null!", null));
			champs_vide=true;
		}
		
		if(champs_vide==false)
		{
			tvaService.save(tva);
			tva=new Tva();
			tvas = tvaService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tva Mis à jour!", null));
		}
	}
	
	
}
