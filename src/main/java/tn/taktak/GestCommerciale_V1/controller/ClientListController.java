package tn.taktak.GestCommerciale_V1.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import lombok.Getter;
import lombok.Setter;
import tn.taktak.GestCommerciale_V1.entity.Article;
import tn.taktak.GestCommerciale_V1.entity.Client;
import tn.taktak.GestCommerciale_V1.entity.Fournisseur;
import tn.taktak.GestCommerciale_V1.service.ClientService;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class ClientListController implements Serializable {

	@ManagedProperty("#{clientService}")
	private ClientService clientService;
    private List<Client> clients;
	private Client client = new Client();
	public static String id=null;
	
	@PostConstruct
	public void loadClient() {
		clients = clientService.findAll();
	}
	
	public List<Client> findAll()
	{
		return clientService.findAll();
	}
	
	public void selectedClient(Client c) 
	{
		setClient(c);
		id=c.getCclient();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('clientDialog').show();");
	}
	public void save() {
		
		boolean champs_vide=false;
		if(client.getCclient().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(client.getCregimeTva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le régime tva ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(client.getRaisonSociale().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le Raison social ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(champs_vide==false)
		{
			clientService.save(client);
			client=new Client();
			clients = clientService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Client Enregistré!", null));
		}
	}
	
	public void update()
	{
		boolean champs_vide=false;
		client.setCclient(id);
		if(client.getCclient().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le code ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(client.getCregimeTva().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le régime tva ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(client.getRaisonSociale().isEmpty())
		{
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le Raison social ne peut pas etre null!", null));
			champs_vide=true;
		}
		if(champs_vide==false)
		{
			clientService.save(client);
			client=new Client();
			clients = clientService.findAll();
			FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fournisseur mis à jour!", null));
		}
	}
	
	public void remove(Client client) {
		clientService.remove(client);
		clients = clientService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Client Supprimé!", null));
	}
	
	public void clear()
	{
		client=new Client();
	}
	
	public String redirect() {

        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/ListeClient.xhtml"));
        try {

            extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);

        }
        return null;
 
    }
	
	public void filterClient()
	{
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String recherche=(String)request.getParameter("formClient:rechercheClient");
		if(recherche!=null && recherche.isEmpty()!=true && recherche!=" ")
		{
			RequestContext context = RequestContext.getCurrentInstance();
			clients=clientService.filterClient(recherche);
			context.update("formClient:clientTable");
		}
		else
		{
			clients=clientService.findAll();
		}
	}
	
	public  Client findClientById(String id)
	{
		Client art= clientService.findClientById(id);
		return art;
	}
	
}
