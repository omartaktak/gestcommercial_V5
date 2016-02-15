package tn.taktak.GestCommerciale_V1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Fournisseur {

	@Id
	private String cfournisseur;
	private String cregimeTva;
	private String raisonSociale;
	private String matFiscale;
	private String nregistreCommerce;
	private String siteWeb;
	private String skype;
	private String email;
	private String tel;
	private String fax;
	private String gsm;
	private String desAdresse;
	private String pays;
	private String ville;
	private String region;
	private String codePostal;
	private String observation;

	public Fournisseur() {
	}

	public Fournisseur(String cfournisseur, String cregimeTva,
			String raisonSociale) {
		this.cfournisseur = cfournisseur;
		this.cregimeTva = cregimeTva;
		this.raisonSociale = raisonSociale;
	}

	public Fournisseur(String cfournisseur, String cregimeTva,
			String raisonSociale, String matFiscale, String nregistreCommerce,
			String siteWeb, String skype, String email, String tel, String fax,
			String gsm, String desAdresse, String pays, String ville,
			String region, String codePostal, String observation) {
		this.cfournisseur = cfournisseur;
		this.cregimeTva = cregimeTva;
		this.raisonSociale = raisonSociale;
		this.matFiscale = matFiscale;
		this.nregistreCommerce = nregistreCommerce;
		this.siteWeb = siteWeb;
		this.skype = skype;
		this.email = email;
		this.tel = tel;
		this.fax = fax;
		this.gsm = gsm;
		this.desAdresse = desAdresse;
		this.pays = pays;
		this.ville = ville;
		this.region = region;
		this.codePostal = codePostal;
		this.observation = observation;
	}
}
