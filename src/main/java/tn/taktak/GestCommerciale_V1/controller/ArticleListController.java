package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.primefaces.context.RequestContext;

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.service.ArticleService;


@ManagedBean
@Getter
@Setter
@ViewScoped
@RequestScoped
@SessionScoped
public class ArticleListController implements Serializable {

	@ManagedProperty("#{articleService}")
	private ArticleService articleService;
	
	//@ManagedProperty("#{tabMenuManagedBean}")
	//private TabMenuManagedBean tabMenuManagedBean;
	private List<Article> articles;
	private Article article = new Article();
	public static List<Article> listarticles=null;
	
	public static String id=null;
	public static Integer index=null;
	@PostConstruct
	public void loadArticle() {
				
		if(listarticles!=null)
			articles=listarticles;
		else
			articles = articleService.findAll();
		
		
//		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		String uri=request.getRequestURI();
//		StringBuffer url=request.getRequestURL();
//		url.append( "?i=2");
//		try{                     FacesContext.getCurrentInstance().getExternalContext().redirect(url.toString());
//		}catch (Exception ex){
//		    ex.printStackTrace();
//		}
//		String nul=null;

		
//		FacesContext fc = FacesContext.getCurrentInstance();
//		try
//		{
//			index =Integer.parseInt(getCountryParam(fc));
//		}
//		catch (Exception e)
//		{
//			index=-1;
//		}
//		if(index>=0)
//		{
//			tabMenuManagedBean.setIndex(index);
//
//		}
	}
	
	//get value from "f:param"
		public String getCountryParam(FacesContext fc){
			
			Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
			return params.get("i");
			
		}
	
	public List<String> listIds()
	{
		List<String> list=new ArrayList<String>() ;
			
		
		for (Article art : articles)
		{
			list.add(art.getCarticle());
		}
		return list;
	}
	
	public void selectedArticle(Article art) 
	{
		setArticle(art);
		id=art.getCarticle();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('articleDialog').show();");
	
		//context.update("formArticle:artForm");
	}
	
	
	
	public void save() {
//		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		String unite=(String)request.getParameter("form-unit:unite");
//		
//		article.setCunite(unite);
		boolean champs_vide=false;
		if(article.getCarticle().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getDesArticle().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La désignation ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getCtva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le régime tva ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getCunite().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'unité  ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getPrixAchat().equals(BigDecimal.ZERO))
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le prix d'achat ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getPrixVente().equals(BigDecimal.ZERO))
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le prix de vente ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(champs_vide==false)
		{
			articleService.save(article);
			article=new Article();
			articles = articleService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Article Enregistré!", null));
		}
		//String ss=redirect();
	}
	
	public void redirectArticle() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();											  
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Fichearticle.xhtml?i=2"));
        url+="?i=2";
        try {
        	extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        
	}
	
	public void selectUnite(String id_Unite)
	{
		//article.setCunite(id_Unite);
		redirectArticle();
		
		RequestContext context = RequestContext.getCurrentInstance();
		//context.execute("PF('unitDialog').hide();");
		context.update(":form-unit:unite");
		
	}
	public void update()
	{
		boolean champs_vide=false;
		article.setCarticle(id);
		if(article.getCarticle().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getDesArticle().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La désignation ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getCtva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le régime tva ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getCunite().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'unité  ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getPrixAchat().equals(BigDecimal.ZERO))
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le prix d'achat ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(article.getPrixVente().equals(BigDecimal.ZERO))
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le prix de vente ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(champs_vide==false)
		{
			articleService.save(article);
			article=new Article();
			articles = articleService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Article Mis à jour!", null));
		}
	}
	
	public void remove(Article article) {
		articleService.remove(article);
		articles = articleService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Article Supprimé!", null));
	}
	
	public void clear()
	{
		article=new Article();
	}
	
	public String redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/ListeArticle.xhtml?i=2"));
        try {
        	extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null;
	}
	
	public void filterArticle()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formArticle:rechercheArticle");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			articles=articleService.filterArticle(recherche);
			listarticles=articles;
			context.update("formArticle:articleTable");
		}
		else
		{
			articles=articleService.findAll();
			listarticles=articles;
		}
	}
	
	//public static Article findArticleById(String id)
	public  Article findArticleById(String id)
	{
		//ArticleService articleService1=new ArticleService();
		Article art= articleService.findArticleById(id);
		return art;
	}
}