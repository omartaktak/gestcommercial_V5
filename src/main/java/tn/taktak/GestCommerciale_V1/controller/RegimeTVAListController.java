package tn.taktak.GestCommerciale_V1.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import tn.taktak.GestCommerciale_V1.entity.RegimeTva;

import tn.taktak.GestCommerciale_V1.service.RegimeTVAService;

@ManagedBean
@Getter
@Setter
@ViewScoped
public class RegimeTVAListController implements Serializable {

	@ManagedProperty("#{regimeTVAService}")
	private RegimeTVAService regimeTVAService;
	
	private List<RegimeTva> regimeTvas;
	private RegimeTva regimeTva = new RegimeTva();
	
	@PostConstruct
	public void loadRegimeTVA() {
		regimeTvas = regimeTVAService.findAll();
	}
	
	public List<String> findDesRegimTva(){
		return regimeTVAService.findDesRegimTva();
	   
	}
	
	public void save() {
		regimeTVAService.save(regimeTva);
		regimeTva=new RegimeTva();
		regimeTvas = regimeTVAService.findAll();
		FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Regime TVA Enregistre!", null));
	}
	
	public void remove(RegimeTva regimeTva) {
		regimeTVAService.remove(regimeTva);
		regimeTvas = regimeTVAService.findAll();
		FacesContext.getCurrentInstance().addMessage
			(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Regime TVA Supprime!", null));
	}
	
	public void clear()
	{
		regimeTva=new RegimeTva();
	}
}
