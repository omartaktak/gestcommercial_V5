package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import tn.taktak.GestCommerciale_V1.entity.SessionCaisse;
import tn.taktak.GestCommerciale_V1.service.SessionCaisseService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class SessionCaisseController implements Serializable {

	@ManagedProperty("#{sessionCaisseService}")
	private SessionCaisseService sessionCaisseService;
    private List<SessionCaisse> listeSessionCaisse;
	private SessionCaisse sessionCaisse = new SessionCaisse();
	
	@PostConstruct
	public void loadSessionCaisse() {
		listeSessionCaisse= sessionCaisseService.findAll();
		//DevisClient devis = new DevisClient();
	}
	
	public void save() {
		sessionCaisseService.save(sessionCaisse);
		sessionCaisse=new SessionCaisse();
		listeSessionCaisse= sessionCaisseService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La session de caisse est Enregistrée!", null));
	}
	
	public void remove(SessionCaisse session) {
		sessionCaisseService.remove(session);
		listeSessionCaisse= sessionCaisseService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La session de caisse est Supprimé!", null));
	}
	
	public void clear()
	{
		sessionCaisse=new SessionCaisse();
	}
	
	
}
